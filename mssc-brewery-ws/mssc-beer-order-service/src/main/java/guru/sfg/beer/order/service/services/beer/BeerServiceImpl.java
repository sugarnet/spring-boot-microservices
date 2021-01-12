package guru.sfg.beer.order.service.services.beer;

import guru.sfg.beer.order.service.web.model.BeerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
@Component
public class BeerServiceImpl implements BeerService {

    public final String BEER_PATH_V1 = "/api/v1/beer";

    private final RestTemplate restTemplate;

    private String beerServiceHost;

    public BeerServiceImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public void setBeerServiceHost(String beerServiceHost) {
        this.beerServiceHost = beerServiceHost;
    }

    public String getBeerServiceHost() {
        return beerServiceHost;
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID uuid) {
        return Optional.of(restTemplate.getForObject(beerServiceHost + BEER_PATH_V1 + uuid.toString(), BeerDTO.class));

    }

    @Override
    public Optional<BeerDTO> getBeerByUpc(String upc) {
        return Optional.of(restTemplate.getForObject(beerServiceHost + BEER_PATH_V1 + "/beerUpc/" + upc, BeerDTO.class));
    }
}
