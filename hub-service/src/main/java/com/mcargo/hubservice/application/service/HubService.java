package com.mcargo.hubservice.application.service;

import com.mcargo.hubservice.domain.exception.HubException;
import com.mcargo.hubservice.domain.response.HubResponseCode;
import com.mcargo.common.util.PageingUtils;
import com.mcargo.hubservice.domain.entity.Hub;
import com.mcargo.hubservice.domain.entity.HubProduct;
import com.mcargo.hubservice.domain.repository.HubRepository;
import com.mcargo.hubservice.presentation.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HubService {

    private final HubRepository hubRepository;

    // 허브 생성
    @Transactional
    public void createHub(CreateHubRequest dto) {

        Hub newHub = Hub.create(
                dto.name(),
                dto.address(),
                dto.latitude(),
                dto.longitude()
        );
        hubRepository.save(newHub);
    }


    // 허브 수정
    @Transactional
    public void updateHub(UUID hubId, UpdateHubRequest request) {
        Hub findHub = hubRepository.findById(hubId)
                .orElseThrow(() -> new HubException(HubResponseCode.HUB_NOT_FOUND));

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

    // 전체 허브 조회
    @Transactional(readOnly = true)
    public Page<GetHubResponse> getHubAll(int size, String sortBy, boolean isDescending) {
        Pageable pageable = PageingUtils.createPageable(size, sortBy, isDescending);
        Page<Hub> pageHubs = hubRepository.findAll(pageable);

        return pageHubs.map(
                hub -> new GetHubResponse(
                        hub.getName(),
                        hub.getAddress(),
                        hub.getLatitude(),
                        hub.getLongitude(),
                        hub.getLastDriverNumber()
                )
        );
    }

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

    // 미완
    // 허브 검색
    @Transactional(readOnly = true)
    public Page<GetHubResponse> searchHub(String name, String address, int size, String sortBy, Boolean isDescending) {
        Pageable pageable = PageingUtils.createPageable(size, sortBy, isDescending);


        return null;
    }

    // 특정 허브의 모든 허브상품 조회
    @Transactional(readOnly = true)
    public Page<GetHubProductResponse> getProductsFromHub(UUID hubId, int size, String sortBy, Boolean isDescending) {
        Pageable pageable = PageingUtils.createPageable(size, sortBy, isDescending);

        Page<HubProduct> findHubProducts = hubRepository.findProductsFromHub(hubId, pageable);
        return findHubProducts.map(hp -> new GetHubProductResponse(
                hubId, // TODO HubProduct에 필드 생성만 해놓으면 단방향으로 생성된 HubId가 제대로 들어갈까?
                hp.getProductId(),
                hp.getStatus(),
                hp.getStock()
        ));

    }

    // ㅡㅡ허브 상품 관련ㅡㅡ
    // 허브상품 추가
    @Transactional
    public void addHubProduct(@Valid addHubProductRequest request) {
        //TODO 요청의 업체상품의 아이디가 유효 한지 확인 클라이언트 요청 추가 예정

        Hub findHub = hubRepository.findById(request.hubId()).orElseThrow(
                () -> new HubException(HubResponseCode.HUB_NOT_FOUND));

        findHub.addHubProduct(
                request.productId(),
                request.stock()
        );
    }

    // 허브상품 수정 (상태, 재고)
    @Transactional
    public void updateHubProduct(UUID hubProductId, UpdateHubProductRequest request) {
        // 허브상품id로 허브 검색
        Hub findHub = hubRepository.findByHubProductId(hubProductId).orElseThrow(
                () -> new HubException(HubResponseCode.HUB_NOT_FOUND));

        findHub.updateHubProduct(hubProductId, request.hubProductStatus(), request.stock());
    }

    // 허브상품 삭제
    @Transactional
    public void deleteHubProduct(Long userId, UUID hubProductId) {
        // 허브상품id로 허브 검색
        Hub findHub = hubRepository.findByHubProductId(hubProductId).orElseThrow(
                () -> new HubException(HubResponseCode.HUB_NOT_FOUND));

        findHub.deleteHubProduct(userId, hubProductId);
    }

    // 허브상품 상세조회
    @Transactional(readOnly = true)
    public GetHubProductDetailsResponse getHubProductDetails(UUID hubProductId) {

        //TODO 상품 상세정보 http요청하고 받아서 dto 변환 후 반환
        //어떤 데이터 반환하는지 확인 후 작성
        return new GetHubProductDetailsResponse();

    }

    // 허브상품 검색 TODO 구현방법 찾아보고 구현
    // @Transactional(readOnly = true)
    // public

    // 허브상품 주문 가능 여부 확인
    @Transactional(readOnly = true)
    public void getHubProductOrderable(GetHubProductOrderableRequest request) {
        //TODO 주문 가능 여부 요청에 어떤 응답을 기대하는지 확인하고 작성
    }

    // 미완
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

    // 모든 허브id 반환
    protected List<UUID> getAllHubId() {
        return hubRepository.findAllHub().stream()
                .map(Hub::getId)
                .collect(Collectors.toList());
    }

    // 허브 주소 반환
    protected String getHubAddress(UUID hubId) {
        Hub findHub = hubRepository.findById(hubId).orElseThrow(
                () -> new HubException(HubResponseCode.HUB_NOT_FOUND));
        return findHub.getAddress();
    }
}