package com.mcargo.hubservice.domain.repository;

import com.mcargo.hubservice.domain.entity.Hub;
import com.mcargo.hubservice.domain.entity.HubProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface HubRepository {

    Hub save(Hub newHub);

    Optional<Hub> findById(UUID hubId);

    Page<Hub> findAll(Pageable pageable);

    Optional<Hub> findByHubProductId(UUID hubProductId);

    Page<HubProduct> findProductsFromHub(UUID hubId, Pageable pageable);

    Collection<Hub> findAllHub();
}
