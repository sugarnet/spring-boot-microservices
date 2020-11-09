package com.dss.msscbeerservice.web.controller;

import com.dss.msscbeerservice.web.model.BeerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable UUID beerId) {
        // todo
        return new ResponseEntity<>(BeerDTO.builder().build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createBeer(@RequestBody @Validated BeerDTO beerDTO) {
        // todo
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<?> updateBeer(@PathVariable UUID beerId, @RequestBody @Validated BeerDTO beerDTO) {
        // todo
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
