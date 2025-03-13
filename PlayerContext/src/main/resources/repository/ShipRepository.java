package com.ship.shipcontext.shipcontext.repository;


import com.ship.shipcontext.entity.ShipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShipRepository extends JpaRepository<ShipEntity, Long> {
    List<ShipEntity> findByPlayerId(Long playerId);
}
