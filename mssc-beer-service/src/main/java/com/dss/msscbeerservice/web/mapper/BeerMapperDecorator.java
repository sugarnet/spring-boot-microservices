package com.dss.msscbeerservice.web.mapper;

import com.dss.msscbeerservice.domain.Beer;
import com.dss.msscbeerservice.web.model.BeerDTO;
import com.dss.msscbeerservice.service.inventory.BeerInventoryService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerMapperDecorator implements BeerMapper {
    private BeerInventoryService beerInventoryService;
    private BeerMapper beerMapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper beerMapper) {
        this.beerMapper = beerMapper;
    }

    @Override
    public BeerDTO beerToBeerDTO(Beer beer) {
        BeerDTO dto = beerMapper.beerToBeerDTO(beer);
        dto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
        return dto;
    }

    @Override
    public Beer beerDTOtoBeer(BeerDTO beerDTO) {
        return beerMapper.beerDTOtoBeer(beerDTO);
    }
}
