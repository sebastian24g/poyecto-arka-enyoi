package com.arka_EP.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataCartRepository extends JpaRepository<CartEntity, Long> {}
