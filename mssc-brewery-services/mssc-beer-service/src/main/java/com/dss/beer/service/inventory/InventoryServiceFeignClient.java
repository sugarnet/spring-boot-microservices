package com.dss.beer.service.inventory;

import com.dss.beer.config.FeignClientConfig;
import com.dss.beer.service.inventory.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "inventory-service", fallback = BeerInventoryServiceFailoverFeignClientImpl.class, configuration = FeignClientConfig.class)
public interface InventoryServiceFeignClient {
    @GetMapping(BeerInventoryServiceRestTemplateImpl.INVENTORY_PATH)
    ResponseEntity<List<BeerInventoryDto>> getOnHandInventory(@PathVariable UUID beerId);
}
