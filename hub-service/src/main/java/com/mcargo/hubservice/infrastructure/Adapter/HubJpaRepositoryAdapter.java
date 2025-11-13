package com.mcargo.hubservice.infrastructure.Adapter;

import com.mcargo.hubservice.domain.entity.Hub;
import com.mcargo.hubservice.domain.entity.HubProduct;
import com.mcargo.hubservice.domain.exception.HubException;
import com.mcargo.hubservice.domain.repository.HubRepository;
import com.mcargo.hubservice.domain.response.HubResponseCode;
import com.mcargo.hubservice.infrastructure.jpa.HubJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HubJpaRepositoryAdapter implements HubRepository {

    private final HubJpaRepository hubJpaRepository;

    // 허브 저장
    @Override
    public Hub save(Hub newHub) {
        return hubJpaRepository.save(newHub);
    }

    // 아이디로 허브 조회
    @Override
    public Hub findById(UUID hubId) {
        return hubJpaRepository.findById(hubId).orElseThrow(() -> new HubException(HubResponseCode.HUB_NOT_FOUND));
    }

    // 전체 허브 조회(페이징)
    @Override
    public Page<Hub> findAll(Pageable pageable) {
        return hubJpaRepository.findAll(pageable);
    }

    // 모든 허브 반환
    @Override
    public List<Hub> findAll() {
        return hubJpaRepository.findAll();
    }

    // HubProductId로 허브상품
    @Override
    public Optional<HubProduct> findHubProductByHubProductId(UUID hubProductId) {
        return hubJpaRepository.findHubProductByHubProductId(hubProductId);
    }

    // 이름 혹은 주소로 허브 검색
    @Override
    public Page<Hub> searchHubs(String name, String address, Pageable pageable) {
        return hubJpaRepository.searchHubs(name, address, pageable);
    }

    // 소속허브로 허브상품 검색
    @Override
    public Page<HubProduct> searchHubProducts(UUID hubId, Pageable pageable) {
        return hubJpaRepository.searchHubProducts(hubId, pageable);
    }

}
