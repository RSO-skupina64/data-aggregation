package com.rso.microservice.repository;

import com.rso.microservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PingRepository extends JpaRepository<Product, Long> {

    @Query(value = "/* ping */ SELECT 1", nativeQuery = true)
    Integer ping();

}
