package com.mcargo.hubservice.presentation.controller;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.hubservice.domain.response.HubResponseCode;
import com.mcargo.hubservice.application.service.HubService;
import com.mcargo.hubservice.presentation.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hubs")
@RequiredArgsConstructor
public class HubController {

    private final HubService hubService;

    // ㅡㅡ허브 관련ㅡㅡ
    // 허브 생성
    @PostMapping
    public ApiResponse<Void> createHub(@Valid @RequestBody CreateHubRequest request) {
        hubService.createHub(request);
        return ApiResponse.of(HubResponseCode.HUB_CREATED);
    }

    // 허브 수정
    @PatchMapping("/{hubId}")
    public ApiResponse<Void> updateHub(
            @PathVariable("hubId") UUID hubId,
            @Valid @RequestBody UpdateHubRequest request) {

        hubService.updateHub(hubId, request);
        return ApiResponse.of(HubResponseCode.HUB_OK);
    }

    // 미완 : userId TODO
    // 허브 삭제
    @DeleteMapping("/{hubId}")
    public ApiResponse<Void> deleteHub(
            @PathVariable("hubId") UUID hubId) {

        Long userId = 1L; // 임시
        hubService.deleteHub(userId, hubId);
        return ApiResponse.of(HubResponseCode.HUB_OK);
    }

    // 미완 : 레디스 TODO
    // 전체 허브 조회
    @GetMapping
    public ApiResponse<Page<GetHubResponse>> getHubAll(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "true") Boolean isDescending) {

        return ApiResponse.of(HubResponseCode.HUB_OK, hubService.getHubAll(size, sortBy, isDescending));
    }

    // 미완 : 레디스 TODO
    // 허브 단일 조회
    @GetMapping("/{hubId}")
    public ApiResponse<GetHubResponse> getHub(
            @PathVariable("hubId") UUID hubId) {

        return ApiResponse.of(HubResponseCode.HUB_OK, hubService.getHub(hubId));
    }

    // 허브 검색. 이름, 주소로
    @GetMapping("/search")
    public ApiResponse<Page<GetHubResponse>> searchHubs(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "true") Boolean isDescending) {

        return ApiResponse.of(HubResponseCode.HUB_OK, hubService.searchHub(name, address, size, sortBy, isDescending));
    }

    // ㅡㅡ허브상품 관련ㅡㅡ
    // 허브상품 추가
    @PostMapping("/Products")
    public ApiResponse<Void> addHubProduct(@Valid @RequestBody AddHubProductRequest request) {
        hubService.addHubProduct(request);
        return ApiResponse.of(HubResponseCode.HUB_PRODUCT_CREATED);
    }

    // 허브상품 수정(상태, 재고)
    @PatchMapping("/Products/{hubProductId}")
    public ApiResponse<Void> updateHubProduct(
            @PathVariable("hubProductId") UUID hubProductId,
            @Valid @RequestBody UpdateHubProductRequest request) {

        hubService.updateHubProduct(hubProductId, request);
        return ApiResponse.of(HubResponseCode.HUB_OK);
    }

    // 미완 : userId TODO
    // 허브상품 삭제
    @DeleteMapping("/products/{hubProductId}")
    public ApiResponse<Void> deleteHubProduct(
            @PathVariable("hubProductId") UUID hubProductId) {

        Long userId = 1L; // 임시
        hubService.deleteHubProduct(userId, hubProductId);
        return ApiResponse.of(HubResponseCode.HUB_OK);
    }

    // 허브상품 상세 조회 -> 급해서 업체쪽 dto 그냥 가져옴
    @GetMapping("/products/{hubProductId}") // 응답 dto 아쉽
    public ApiResponse<ProductReadResponse> getHubProductDetails(@PathVariable("hubProductId") UUID hubProductId) {
        return ApiResponse.of(HubResponseCode.HUB_OK, hubService.getHubProductDetails(hubProductId));
    }

    // 허브상품 검색. 소속 허브로. 상품의 대한 정보는 없음(id값만)
    @GetMapping("/products/search")
    public ApiResponse<Page<GetHubProductResponse>> searchHubProducts(
            @RequestParam(required = false) UUID hubId,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "true") Boolean isDescending) {

        return ApiResponse.of(HubResponseCode.HUB_OK, hubService.searchHubProducts(hubId, size, sortBy, isDescending));
    }


    // 허브상품 주문 가능 여부 확인. 내부호출용도
    @PostMapping("/products/orderable")
    public List<GetHubProductOrderableResponse> getHubProductOrderable(
            @RequestBody List<GetHubProductOrderableRequest> request) {
        return hubService.getHubProductOrderable(request);
    }

}
