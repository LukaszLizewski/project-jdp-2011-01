package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
  
    @Override
    List<ProductEntity> findAll();

}

