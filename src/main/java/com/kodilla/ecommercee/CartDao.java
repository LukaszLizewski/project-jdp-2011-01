package com.kodilla.ecommercee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface CartDao extends CrudRepository<Cart, Long> {

}