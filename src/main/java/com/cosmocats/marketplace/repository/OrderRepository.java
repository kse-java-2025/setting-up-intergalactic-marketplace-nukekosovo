package com.cosmocats.marketplace.repository;

import com.cosmocats.marketplace.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByBusinessKey(UUID businessKey);
}