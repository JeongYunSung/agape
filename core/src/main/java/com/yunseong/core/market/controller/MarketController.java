package com.yunseong.core.market.controller;

import com.yunseong.core.common.PageMetadata;
import com.yunseong.core.market.CreateMarketRequest;
import com.yunseong.core.market.FoodVO;
import com.yunseong.core.market.MarketVO;
import com.yunseong.core.market.RestaurantVO;
import com.yunseong.core.market.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/markets", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MarketController {

    private final MarketService marketService;

    @GetMapping
    public ResponseEntity<?> findMarkets(@PageableDefault Pageable pageable) {
        Page<MarketVO> page = this.marketService.findMarkets(pageable);
        return ResponseEntity.ok(new PageMetadata<>(page.getSize(), page.getNumber(), page.getTotalPages(), page.getContent()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findMarket(@PathVariable long id) {
        return ResponseEntity.ok(this.marketService.findMarket(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMarket(@PathVariable long id, @RequestBody MarketVO marketVO) {
        this.marketService.updateMarket(id, marketVO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMarket(@PathVariable long id) {
        this.marketService.deleteMarket(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> openMarket(@RequestBody CreateMarketRequest request) {
        CreateMarketResponse response = this.marketService.openMarket(request);
        return ResponseEntity
                .created(URI.create("/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{id}/restaurant")
    public ResponseEntity<?> updateRestaurant(@PathVariable long id, @RequestBody RestaurantVO restaurantVO) {
        this.marketService.updateRestaurant(id, restaurantVO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/foods/{fid}")
    public ResponseEntity<?> deleteFood(@PathVariable long id, @PathVariable long fid) {
        this.marketService.deleteFood(id, fid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/foods/{fid}")
    public ResponseEntity<?> updateFood(@PathVariable long id, @PathVariable long fid, @RequestBody FoodVO foodVO) {
        this.marketService.updateFood(id, fid, foodVO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/foods")
    public ResponseEntity<?> addFood(@PathVariable long id, @RequestBody FoodVO foodVO) {
        this.marketService.addFood(id, foodVO);
        return ResponseEntity.created(URI.create("/" + id + "/foods")).build();
    }
}
