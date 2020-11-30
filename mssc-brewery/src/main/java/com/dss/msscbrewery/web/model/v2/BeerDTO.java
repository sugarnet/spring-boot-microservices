package com.dss.msscbrewery.web.model.v2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDTO {
    private UUID id;
    private String beerName;
    private BeerStyle beerStyle;
    private Long upc;

    private OffsetDateTime createdDate;
    private OffsetDateTime lastUpdatedDate;
}
