package com.dds.jackson.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class BeerDtoTest extends BaseTest {

    @Test
    void testSerializeDto() throws JsonProcessingException {
        BeerDto dto = getDto();
        String json = new ObjectMapper().writeValueAsString(dto);
        System.out.println(json);
    }

    @Test
    void testDeserializeDto() throws IOException {
        String json = "{\"id\":\"508b8151-c5e8-4119-aa40-d6b2365dbd41\",\"beerName\":\"BeerName\",\"beerStyle\":\"Ale\",\"upc\":12344324,\"price\":12.99,\"createdDate\":{\"offset\":{\"totalSeconds\":-10800,\"id\":\"-03:00\",\"rules\":{\"fixedOffset\":true,\"transitions\":[],\"transitionRules\":[]}},\"nano\":145294000,\"year\":2020,\"monthValue\":12,\"dayOfMonth\":14,\"hour\":7,\"minute\":43,\"second\":53,\"dayOfWeek\":\"MONDAY\",\"dayOfYear\":349,\"month\":\"DECEMBER\"},\"lastUpdatedDate\":{\"offset\":{\"totalSeconds\":-10800,\"id\":\"-03:00\",\"rules\":{\"fixedOffset\":true,\"transitions\":[],\"transitionRules\":[]}},\"nano\":145368000,\"year\":2020,\"monthValue\":12,\"dayOfMonth\":14,\"hour\":7,\"minute\":43,\"second\":53,\"dayOfWeek\":\"MONDAY\",\"dayOfYear\":349,\"month\":\"DECEMBER\"}}";
        BeerDto dto = new ObjectMapper().readValue(json, BeerDto.class);
        System.out.println(dto);
    }
}