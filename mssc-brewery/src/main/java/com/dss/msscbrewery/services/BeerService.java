package com.dss.msscbrewery.services;

import com.dss.msscbrewery.web.domain.BeerDTO;

import java.util.UUID;

public interface BeerService {

    BeerDTO getBeerById(UUID beerId);
}
