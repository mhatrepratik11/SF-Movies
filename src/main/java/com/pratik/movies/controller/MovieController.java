package com.pratik.movies.controller;

import com.google.maps.model.LatLng;
import com.pratik.movies.models.MovieHealthCheck;
import com.pratik.movies.models.MovieResponse;
import com.pratik.movies.models.MoviesInfo;
import com.pratik.movies.service.MovieService;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {})
@RequestMapping("/movie")
public class MovieController {

    private Logger LOG = Logger.getLogger(MovieController.class);
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MovieService movieService;

    List<MoviesInfo> moviesInfoList = new ArrayList<>();

    @PostConstruct
    public void setupDatabase() {
        getMovieApiData();
    }

    @RequestMapping(value = { "/check" }, method = RequestMethod.GET, produces = "application/json")
    public MovieHealthCheck healthCheck(@RequestParam(value="name", defaultValue="World") String name) {

        return new MovieHealthCheck(counter.incrementAndGet(), "Movie : " + name);
    }

    @RequestMapping(value = { "/getMovieApiData" }, method = RequestMethod.GET)
    public void getMovieApiData() {
        moviesInfoList = movieService.getAllData();
    }

    @RequestMapping(value = {"/getAllMoviesList"}, method = RequestMethod.POST, produces = "application/json")
    public MovieResponse getAllMoviesList() {

        if (moviesInfoList == null || moviesInfoList.isEmpty()) {
            getMovieApiData();
        }

        return new MovieResponse(1, null, moviesInfoList);
    }

    @RequestMapping(value = {"/getMoviesFromSanFransisco"}, method = RequestMethod.POST, produces = "application/json")
    public MovieResponse getMoviesFromSanFransisco() {

        if (moviesInfoList == null || moviesInfoList.isEmpty()) {
            getMovieApiData();
        }

        List<MoviesInfo> sanFranciscoMovieList = moviesInfoList.stream().filter(sf -> Pattern.compile(Pattern.quote(sf.getCity()), Pattern.CASE_INSENSITIVE).matcher("Francisco").find()).collect(Collectors.toList());

        LOG.info("san size is : " + sanFranciscoMovieList.size());

        return new MovieResponse(1, null, sanFranciscoMovieList);
    }
}
