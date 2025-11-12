package com.mcargo.hubservice.infrastructure.jpa;

import com.mcargo.hubservice.domain.entity.Hub;
import com.mcargo.hubservice.domain.entity.HubProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface HubJpaRepository extends JpaRepository<Hub, UUID> {

    // 허브상품id로 허브 조회
    @Query("SELECT h FROM Hub h JOIN h.hubProducts hp " +
            "WHERE hp.id = :hubProductId")
    Optional<Hub> findByHubProductId(UUID hubProductId);

    //허브상품id로 허브상품
    @Query("SELECT hp FROM Hub h JOIN h.hubProducts hp " +
            "WHERE hp.id = :hubProductId")
    Optional<HubProduct> findHubProductByHubProductId(UUID hubProductId);

    //이름or주소로 허브 검색
    @Query("SELECT h FROM Hub h " +
            "WHERE (:name IS NULL OR h.name LIKE %:name%) " +
            "AND (:address IS NULL OR h.address LIKE %:address%)")
    Page<Hub> searchHubs(String name, String address, Pageable pageable);

    // 허브id로 해당 허브의 허브상품들 조회
    @Query("SELECT hp FROM Hub h JOIN h.hubProducts hp " +
            "WHERE h.id = :hubId")
    Page<HubProduct> searchHubProducts(UUID hubId, Pageable pageable);
}
