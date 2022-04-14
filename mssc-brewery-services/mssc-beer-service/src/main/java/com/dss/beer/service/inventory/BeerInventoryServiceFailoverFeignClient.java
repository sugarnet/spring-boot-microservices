package com.dss.beer.service.inventory;

import com.dss.beer.service.inventory.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "inventory-failover")
public interface BeerInventoryServiceFailoverFeignClient {
    @GetMapping("inventory-failover")
    ResponseEntity<List<BeerInventoryDto>> getOnHandInventory();
}
