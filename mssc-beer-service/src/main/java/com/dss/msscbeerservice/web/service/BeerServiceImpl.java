package com.dss.msscbeerservice.web.service;

import com.dss.msscbeerservice.web.domain.Beer;
import com.dss.msscbeerservice.web.mapper.BeerMapper;
import com.dss.msscbeerservice.web.model.BeerDTO;
import com.dss.msscbeerservice.web.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDTO getById(UUID beerId) {
        return beerMapper.beerToBeerDTO(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
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
}
