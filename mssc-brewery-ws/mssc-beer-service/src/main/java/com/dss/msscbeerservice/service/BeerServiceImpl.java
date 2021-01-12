package com.dss.msscbeerservice.service;

import com.dss.msscbeerservice.domain.Beer;
import com.dss.msscbeerservice.repository.BeerRepository;
import com.dss.msscbeerservice.web.controller.NotFoundException;
import com.dss.msscbeerservice.web.mapper.BeerMapper;
import com.dss.msscbeerservice.web.model.BeerDTO;
import com.dss.msscbeerservice.web.model.BeerPagedList;
import com.dss.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false")
    @Override
    public BeerDTO getById(UUID beerId, Boolean showInventoryOnHand) {

        if (showInventoryOnHand) {
            return beerMapper.beerToBeerDTOWithInventory(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
        }

        return beerMapper.beerToBeerDTO(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
    }

    @Cacheable(cacheNames = "beerUpcCache", key = "#beerUpc")
    @Override
    public BeerDTO getByUpc(String beerUpc) {
        return beerMapper.beerToBeerDTO(beerRepository.findBeerByUpc(beerUpc).orElseThrow(NotFoundException::new));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        return beerMapper.beerToBeerDTO(beerRepository.save(beerMapper.beerDTOtoBeer(beerDTO)));
    }

    @Override
    public BeerDTO updateBeer(UUID beerId, BeerDTO beerDTO) {
        final Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDTO.getBeerName());
        beer.setBeerStyle(beerDTO.getBeerStyle().name());
        beer.setUpc(beerDTO.getUpc());
        beer.setPrice(beerDTO.getPrice());

        return beerMapper.beerToBeerDTO(beerRepository.save(beer));
    }

    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (showInventoryOnHand) {
            beerPagedList = new BeerPagedList(
                    beerPage.getContent().stream().map(data -> beerMapper.beerToBeerDTOWithInventory(data)).collect(Collectors.toList()),
                    PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()), beerPage.getTotalElements());

        } else {
            beerPagedList = new BeerPagedList(
                    beerPage.getContent().stream().map(data -> beerMapper.beerToBeerDTO(data)).collect(Collectors.toList()),
                    PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()), beerPage.getTotalElements());

        }

        return beerPagedList;
    }
}
