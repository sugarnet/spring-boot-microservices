package com.dss.msscbeerservice.web.controller;

import com.dss.msscbeerservice.bootstrap.BeerLoader;
import com.dss.msscbeerservice.web.model.BeerDTO;
import com.dss.msscbeerservice.web.model.BeerStyleEnum;
import com.dss.msscbeerservice.service.BeerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @Test
    void getBeerById() throws Exception {
        given(beerService.getById(any())).willReturn(getValidBeerDTO());
        mockMvc.perform(get("/api/v1/beer/".concat(UUID.randomUUID().toString())).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void createBeer() throws Exception {
        BeerDTO beerDTO = getValidBeerDTO();
        String beerDTOJson = objectMapper.writeValueAsString(beerDTO);

        given(beerService.saveNewBeer(any())).willReturn(getValidBeerDTO());

        mockMvc.perform(post("/api/v1/beer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDTOJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeer() throws Exception {
        BeerDTO beerDTO = getValidBeerDTO();
        String beerDTOJson = objectMapper.writeValueAsString(beerDTO);

        given(beerService.updateBeer(any(), any())).willReturn(getValidBeerDTO());

        mockMvc.perform(put("/api/v1/beer/".concat(UUID.randomUUID().toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDTOJson))
                .andExpect(status().isNoContent());
    }

    BeerDTO getValidBeerDTO() {
        return BeerDTO.builder().beerName("My Beer").beerStyle(BeerStyleEnum.IPA).price(new BigDecimal("2.00")).upc(BeerLoader.BEER_1_UPC).build();
    }


}