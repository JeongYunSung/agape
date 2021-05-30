package com.yunseong.gateway.market;

import com.yunseong.core.common.PageMetadata;
import com.yunseong.core.market.*;
import com.yunseong.core.member.CreateMemberResponse;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping(value = "/markets", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MarketController {

    private final WebClient webClient;

    @GetMapping
    public ResponseEntity<?> findMarkets(@PageableDefault Pageable pageable) {
        return this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/markets")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .queryParam("sort", pageable.getSort() == Sort.unsorted() ? "" : pageable.getSort().toString())
                        .build()
                )
                .retrieve()
                .toEntity(PageMetadata.class)
                .block();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findMarket(@PathVariable long id) {
        return this.webClient
                .get()
                .uri("/markets/" + id)
                .retrieve()
                .toEntity(FindMarketDetailResponse.class)
                .block();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMarket(@PathVariable long id, @RequestBody MarketVO marketVO) {
        return this.webClient
                .put()
                .uri("/markets/" + id)
                .body(BodyInserters.fromValue(marketVO))
                .retrieve()
                .toBodilessEntity().block();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMarket(@PathVariable long id) {
        return this.webClient
                .delete()
                .uri("/markets/" + id)
                .retrieve()
                .toBodilessEntity().block();
    }

    @PostMapping
    public ResponseEntity<?> openMarket(@RequestBody CreateMarketRequest request) {
        return this.webClient
                .post()
                .uri("/markets")
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .toEntity(CreateMarketResponse.class)
                .block();
    }

    @PutMapping("/{id}/restaurant")
    public ResponseEntity<?> updateRestaurant(@PathVariable long id, @RequestBody RestaurantVO restaurantVO) {
        return this.webClient
                .put()
                .uri("/markets/" + id + "/restaurant")
                .body(BodyInserters.fromValue(restaurantVO))
                .retrieve()
                .toBodilessEntity().block();
    }

    @DeleteMapping("/{id}/foods/{fid}")
    public ResponseEntity<?> deleteFood(@PathVariable long id, @PathVariable long fid) {
        return this.webClient
                .delete()
                .uri("/markets/" + id + "/foods/" + fid)
                .retrieve()
                .toBodilessEntity().block();
    }

    @PutMapping("/{id}/foods/{fid}")
    public ResponseEntity<?> updateFood(@PathVariable long id, @PathVariable long fid, @RequestBody FoodVO foodVO) {
        return this.webClient
                .put()
                .uri("/markets/" + id + "/foods/" + fid)
                .body(BodyInserters.fromValue(foodVO))
                .retrieve()
                .toBodilessEntity().block();
    }

    @PostMapping("/{id}/foods")
    public ResponseEntity<?> addFood(@PathVariable long id, @RequestBody FoodVO foodVO) {
        return this.webClient
                .post()
                .uri("/markets/" + id + "/foods")
                .body(BodyInserters.fromValue(foodVO))
                .retrieve()
                .toBodilessEntity().block();
    }
}
