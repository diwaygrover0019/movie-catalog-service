package com.microservices.moviecatalogservice.controllers;

import com.microservices.moviecatalogservice.models.CatalogItem;
import com.microservices.moviecatalogservice.models.UserRating;
import com.microservices.moviecatalogservice.services.MovieInfoService;
import com.microservices.moviecatalogservice.services.RatingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    private RatingInfoService ratingInfoService;

    @Autowired
    private MovieInfoService movieInfoService;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        // response from ratings MS
        UserRating userRating = ratingInfoService.getUserRatings(userId);

        return userRating.getUserRating()
                .stream()
                .map(rating -> {
                    return movieInfoService.getMovieInfo(rating);
                })
                .collect(Collectors.toList())
                ;
    }
}
