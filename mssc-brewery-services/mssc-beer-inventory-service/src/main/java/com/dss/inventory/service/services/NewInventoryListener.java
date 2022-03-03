package com.dss.inventory.service.services;

import com.dss.inventory.service.config.JmsConfig;
import com.dss.inventory.service.domain.BeerInventory;
import com.dss.brewery.model.events.NewInventoryEvent;
import com.dss.inventory.service.repositories.BeerInventoryRepository;
import com.dss.brewery.model.events.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewInventoryListener {
    private final BeerInventoryRepository beerInventoryRepository;

    @Transactional
    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listen(NewInventoryEvent inventoryEvent) {

        BeerDto beerDto = inventoryEvent.getBeerDto();

        BeerInventory beerInventory = BeerInventory.builder().beerId(beerDto.getId()).upc(beerDto.getUpc()).quantityOnHand(beerDto.getQuantityOnHand()).build();

        beerInventoryRepository.save(beerInventory);
    }
}
