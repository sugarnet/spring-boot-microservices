package com.dss.msscbeerservice.service.inventory;

import java.util.UUID;

public interface BeerInventoryService {
    Integer getOnHandInventory(UUID beerId);
}
