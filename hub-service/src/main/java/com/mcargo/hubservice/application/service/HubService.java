package com.mcargo.hubservice.application.service;

import com.mcargo.common.util.PageingUtils;
import com.mcargo.hubservice.application.port.CompanyPort;
import com.mcargo.hubservice.application.port.UserPort;
import com.mcargo.hubservice.domain.entity.Hub;
import com.mcargo.hubservice.domain.entity.HubProduct;
import com.mcargo.hubservice.domain.entity.HubProductStatus;
import com.mcargo.hubservice.domain.exception.HubException;
import com.mcargo.hubservice.domain.repository.HubRepository;
import com.mcargo.hubservice.domain.response.HubResponseCode;
import com.mcargo.hubservice.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubService {

    private final HubRepository hubRepository;
    private final CompanyPort companyPort;
    private final UserPort userPort;
    private final HubABDomainService abDomainService;

    //ㅡㅡ 허브 관련 ㅡㅡ
    // 허브 생성
    @Transactional
    public void createHub(CreateHubRequest req1, req2) {
        abDomainService.createHub(AResponse, BResponse);
        hubRepository.save(newHub);
    }

    // 허브 수정
    @Transactional
    public void updateHub(UUID hubId, UpdateHubRequest request) {
        Hub findHub = hubRepository.findById(hubId);

        findHub.update(
            request.name(),
            request.address(),
            request.latitude(),
            request.longitude(),
            request.lastDriverNumber()
        );

    }

    // 허브 삭제
    @Transactional
    public void deleteHub(Long userId, UUID hubId) {
        Hub findHub = hubRepository.findById(hubId).orElseThrow(
            () -> new HubException(HubResponseCode.HUB_NOT_FOUND));

        findHub.delete(userId); // soft delete
    }

    // TODO 레디스 캐싱
    // 전체 허브 조회
    @Transactional(readOnly = true)
    public Page<GetHubResponse> getHubAll(int size, String sortBy, boolean isDescending) {
        Pageable pageable = PageingUtils.createPageable(size, sortBy, isDescending);
        Page<Hub> pageHubs = hubRepository.findAll(pageable);

        return pageHubs.map(
            hub -> GetHubResponse.toResponse(it)
            )
        );
    }

    // TODO 레디스 캐싱
    // 허브 단일 조회
    @Transactional(readOnly = true)
    public GetHubResponse getHub(UUID hubId) {
        Hub findHub = hubRepository.findById(hubId).orElseThrow(
            () -> new HubException(HubResponseCode.HUB_NOT_FOUND));

        return new GetHubResponse(
            findHub.getName(),
            findHub.getAddress(),
            findHub.getLatitude(),
            findHub.getLongitude(),
            findHub.getLastDriverNumber()
        );
    }

    // 허브 검색. 이름, 주소로
    @Transactional(readOnly = true)
    public Page<GetHubResponse> searchHub(
        String name, String address, Pageable pageable
    ) {
        Pageable pageable = PageingUtils.createPageable(size, sortBy, isDescending);

        Page<Hub> findHubs = hubRepository.searchHubs(name, address, pageable);

        return findHubs.map(h -> new GetHubResponse(
            h.getName(),
            h.getAddress(),
            h.getLatitude(),
            h.getLongitude(),
            h.getLastDriverNumber()
        ));
    }

    // ㅡㅡ허브 상품 관련ㅡㅡ
    // 허브상품 추가
    @Transactional
    public void addHubProduct(AddHubProductRequest request) {
        Hub findHub = hubRepository.findById(request.hubId()).orElseThrow(
            () -> new HubException(HubResponseCode.HUB_NOT_FOUND));

        findHub.addHubProduct(
            request.productId(),
            request.stock()
        );
    }

    // 허브상품 수정(상태, 재고)
    @Transactional
    public void updateHubProduct(UUID hubProductId, UpdateHubProductRequest request) {
        HubProduct findHubProduct = hubRepository.findHubProductByHubProductId(hubProductId).orElseThrow(
            () -> new HubException(HubResponseCode.HUB_PRODUCT_NOT_FOUND));

        findHubProduct.update(request.hubProductStatus(), request.stock());
    }

    // 허브상품 삭제
    @Transactional
    public void deleteHubProduct(Long userId, UUID hubProductId) {
        HubProduct findHubProduct = hubRepository.findHubProductByHubProductId(hubProductId).orElseThrow(
            () -> new HubException(HubResponseCode.HUB_PRODUCT_NOT_FOUND));
        findHubProduct.delete(userId);
    }


    // 허브상품 상세조회
    @Transactional(readOnly = true)
    public GetHubProductDetailsResponse getHubProductDetails(UUID hubProductId) {
        HubProduct findHubProduct = hubRepository.findHubProductByHubProductId(hubProductId).orElseThrow(
            () -> new HubException(HubResponseCode.HUB_PRODUCT_NOT_FOUND));

        //TODO 상품 상세정보 api 구현되면 적용
        //companyClient

        return null;

    }

    // 허브상품 검색. 소속 허브로. 상품의 대한 정보는 없음(id값만)
    @Transactional(readOnly = true)
    public Page<GetHubProductResponse> searchHubProducts(UUID hubId, int size, String sortBy, Boolean isDescending) {
        Pageable pageable = PageingUtils.createPageable(size, sortBy, isDescending);

        Page<HubProduct> findHubProducts = hubRepository.searchHubProducts(hubId, pageable);
        return findHubProducts.map(hp -> new GetHubProductResponse(
            hubId,
            hp.getProductId(),
            hp.getStatus(),
            hp.getStock()
        ));
    }

    // 허브상품 주문 가능 여부 확인
    @Transactional(readOnly = true)
    public List<GetHubProductOrderableResponse> getHubProductOrderable(List<GetHubProductOrderableRequest> request) {
        return request.stream()
            .map(orderedItem -> {
                HubProduct findHubProduct = hubRepository.findHubProductByHubProductId(orderedItem.hubProductId()).orElseThrow(
                    () -> new HubException(HubResponseCode.HUB_PRODUCT_NOT_FOUND));

                return checkOrderable(findHubProduct, orderedItem.quantity());
            }).toList();
    }


    // ㅡㅡ protected, private ㅡㅡ
    // 업체 배송 담당자 배정. 허브 경로에서 사용
    @Transactional
    protected Integer assignDriver(UUID hubId) {
        Hub findHub = hubRepository.findById(hubId)
            .orElseThrow(() -> new HubException(HubResponseCode.HUB_NOT_FOUND));
        int lastDriverNumber = findHub.getLastDriverNumber();

        //TODO 요청으로 해당 허브 소속의 업체배송 가능한 배송번호리스트 필요
        List<Integer> availableDriverNumbers = new ArrayList<>();

        int nextDriverNumber = getNextDriverNumber(availableDriverNumbers, lastDriverNumber);
        findHub.updateDriverNumber(nextDriverNumber);

        return nextDriverNumber;
    }

    // 다음 업체 배송 담당자를 구하는 정책
    private Integer getNextDriverNumber(List<Integer> availableDriverNumbers, Integer lastDriverNumber) {
        return availableDriverNumbers.stream()
            .filter(num -> num > lastDriverNumber)
            .min(Integer::compareTo)
            .orElseGet(() -> availableDriverNumbers.stream()
                .min(Integer::compareTo)
                .orElseThrow(() -> new HubException(HubResponseCode.HUB_DRIVER_NOT_FOUND)));
    }

    // 주문 가능 여부 판단
    private GetHubProductOrderableResponse checkOrderable(HubProduct hubProduct, int requestedQuantity) {
        HubProduct.checkable(requestedQuantity)

        return new GetHubProductOrderableResponse(
            hubProduct.getId(),
            orderable, message
        );
    }

    // 허브 반환. 허브 경로에서 사용
    protected Hub getHubAsHub(UUID hubId) {
        return hubRepository.findById(hubId).orElseThrow(
            () -> new HubException(HubResponseCode.HUB_NOT_FOUND));

    }

    // 모든 허브 반환. 허브 경로에서 사용
    protected List<Hub> getAllHub() {
        return hubRepository.findAll();
    }
}
