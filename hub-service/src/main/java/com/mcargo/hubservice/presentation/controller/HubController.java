package com.mcargo.hubservice.presentation.controller;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.hubservice.domain.response.HubResponseCode;
import com.mcargo.hubservice.application.service.HubService;
import com.mcargo.hubservice.presentation.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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

    // 미완 : userId
    // 허브 삭제
    @DeleteMapping("/{hubId}")
    public ApiResponse<Void> deleteHub(
            @PathVariable("hubId") UUID hubId) {

        Long userId = 1L; //TODO userId 삭제자 기록 용도
        hubService.deleteHub(userId, hubId);
        return ApiResponse.of(HubResponseCode.HUB_OK);
    }

    // 미완 : 레디스
    // 허브 조회
    @GetMapping
    public ApiResponse<Page<GetHubResponse>> getHubAll(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "true") Boolean isDescending) {

        return ApiResponse.of(HubResponseCode.HUB_OK, hubService.getHubAll(size, sortBy, isDescending));
    }

    // 미완 : userId
    // 허브 단일 조회
    @GetMapping("/{hubId}")
    public ApiResponse<GetHubResponse> getHub(
            @PathVariable("hubId") UUID hubId) {

        return ApiResponse.of(HubResponseCode.HUB_OK, hubService.getHub(hubId));
    }

    // 미완
    // 허브 검색
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
    // 특정 허브의 모든 허브상품 조회
    @GetMapping("/{hubId}/products")
    public ApiResponse<Page<GetHubProductResponse>> getProductsByHubId(
            @PathVariable("hubId") UUID hubId,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "true") Boolean isDescending) {

        return ApiResponse.of(HubResponseCode.HUB_OK, hubService.getProductsFromHub(hubId, size, sortBy, isDescending));
    }

    // 허브상품 추가
    @PostMapping("/Products")
    public ApiResponse<Void> addHubProduct(@Valid @RequestBody addHubProductRequest request) {
        hubService.addHubProduct(request);
        return ApiResponse.of(HubResponseCode.HUB_PRODUCT_CREATED);
    }

    // 허브상품 수정 (상태, 재고)
    @PatchMapping("/Products/{hubProductId}")
    public ApiResponse<Void> updateHubProduct(
            @PathVariable("hubProductId") UUID hubProductId,
            @Valid @RequestBody UpdateHubProductRequest request) {

        hubService.updateHubProduct(hubProductId, request);
        return ApiResponse.of(HubResponseCode.HUB_OK);
    }

    // 허브상품 삭제
    @DeleteMapping("/products/{hubProductId}")
    public ApiResponse<Void> deleteHubProduct(
            @PathVariable("hubProductId") UUID hubProductId) {

        Long userId = 1L; //TODO 삭제자 기록용 나중에 수정
        hubService.deleteHubProduct(userId, hubProductId);
        return ApiResponse.of(HubResponseCode.HUB_OK);
    }

    // 허브상품 상세 조회
    @GetMapping("/products/{hubProductId}")
    public ApiResponse<GetHubProductDetailsResponse> getHubProductDetails(@PathVariable("hubProductId") UUID hubProductId) {
        return ApiResponse.of(HubResponseCode.HUB_OK, hubService.getHubProductDetails(hubProductId));
    }

    // 허브상품 검색 TODO 구현방법 찾아보고 구현
    //@GetMapping("/products/search")

    // 허브상품 주문 가능 여부 확인
    @PostMapping("/product/orderable")
    public ApiResponse<Void> getHubProductOrderable(@Valid @RequestBody GetHubProductOrderableRequest request) {
        hubService.getHubProductOrderable(request);
        return ApiResponse.of(HubResponseCode.HUB_PRODUCT_ORDERABLE);
    }

}
