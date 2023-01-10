package com.rso.microservice.repository;

import com.rso.microservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    List<Product> findAll();

    @Query(value="SELECT * FROM Product p ORDER BY p.id LIMIT :offset, :limit", nativeQuery = true)
    List<Product> findAll(@Param("offset") Integer offset, @Param("limit") Integer limit);
}
