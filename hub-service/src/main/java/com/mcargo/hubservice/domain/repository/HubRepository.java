package com.mcargo.hubservice.domain.repository;

import com.mcargo.hubservice.domain.entity.Hub;
import com.mcargo.hubservice.domain.entity.HubProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubRepository {

    Hub save(Hub newHub);

    Optional<Hub> findById(UUID hubId);

    Page<Hub> findAll(Pageable pageable);

    List<Hub> findAll();

    Optional<HubProduct> findHubProductByHubProductId(UUID hubProductId);

    Page<Hub> searchHubs(String name, String address, Pageable pageable);

    Page<HubProduct> searchHubProducts(UUID hubId, Pageable pageable);
}
