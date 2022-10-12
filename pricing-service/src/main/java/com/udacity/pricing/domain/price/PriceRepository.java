package com.udacity.pricing.domain.price;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PriceRepository extends CrudRepository<Price,Long> {
    Price findByVehicleId(Long vehicleId);
}
