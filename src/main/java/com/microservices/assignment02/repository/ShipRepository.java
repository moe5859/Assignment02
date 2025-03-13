package com.microservices.assignment02.repository;


import com.microservices.assignment02.entity.ShipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShipRepository extends JpaRepository<ShipEntity, Long> {
    List<ShipEntity> findByPlayerId(Long playerId);
}
