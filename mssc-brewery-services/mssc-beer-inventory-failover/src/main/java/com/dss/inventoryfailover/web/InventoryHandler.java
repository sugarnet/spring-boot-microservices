package com.dss.inventoryfailover.web;

import com.dss.inventoryfailover.model.BeerInventoryDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class InventoryHandler {

    public Mono<ServerResponse> listInventory(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(Arrays.asList(BeerInventoryDto.builder()
                        .id(UUID.randomUUID())
                        .createdDate(OffsetDateTime.now())
                        .lastModifiedDate(OffsetDateTime.now())
                        .beerId(UUID.fromString("0000000-0000-0000-0000-000000000000"))
                        .quantityOnHand(999)
                        .build())), List.class);
    }
}
