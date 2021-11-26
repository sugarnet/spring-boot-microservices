package guru.springframework.msscjacksonexamples.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("snake")
@JsonTest
public class BeerDtoSnakeTest extends BaseTest {

    @Test
    void snakeTest() throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(getDto());
        System.out.println(json);
    }
}
