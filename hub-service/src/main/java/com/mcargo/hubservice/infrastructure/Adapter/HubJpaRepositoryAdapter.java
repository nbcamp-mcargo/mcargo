package com.mcargo.hubservice.infrastructure.Adapter;

import com.mcargo.hubservice.domain.entity.Hub;
import com.mcargo.hubservice.domain.entity.HubProduct;
import com.mcargo.hubservice.domain.repository.HubRepository;
import com.mcargo.hubservice.infrastructure.jpa.HubJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
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
    public Optional<Hub> findById(UUID hubId) {
        return hubJpaRepository.findById(hubId);
    }

    // 전체 허브 조회(페이징)
    @Override
    public Page<Hub> findAll(Pageable pageable) {
        return hubJpaRepository.findAll(pageable);
    }

    // 허브상품id로 허브 조회
    @Override
    public Optional<Hub> findByHubProductId(UUID hubProductId) {
        return hubJpaRepository.findByHubProductId(hubProductId);
    }

    // 해당 허브의 모든 허브상품 조회
    @Override
    public Page<HubProduct> findProductsFromHub(UUID hubId, Pageable pageable) {
        return hubJpaRepository.findProductsFromHub(hubId, pageable);
    }

    @Override
    public Collection<Hub> findAllHub() {
        return hubJpaRepository.findAll();
    }

}
