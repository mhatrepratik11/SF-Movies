package com.pratik.movies.service;

import com.google.maps.model.LatLng;
import com.pratik.movies.models.MoviesInfo;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class MovieService {

    private Logger LOG = Logger.getLogger(MovieService.class);

    @Autowired
    private RestTemplate restTemplate;

    LinkedHashMap<String, String> moviesInfo;

    List<MoviesInfo> moviesInfoList = new ArrayList<>();

    public List<MoviesInfo> getAllData() {
        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity("https://data.sfgov.org/resource/wwmu-gmzc.json", Object[].class);
        Object[] objects = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();

        LOG.info("The status code is : " + statusCode);

        if (statusCode == HttpStatus.OK) {
            for (Object object : objects) {
                moviesInfo = (LinkedHashMap<String, String>) object;
                getLatLng(moviesInfo.get("locations"));
            }
            LOG.info("The movie list size is : " + moviesInfoList.size());
        }
        return moviesInfoList;
    }

    public void getLatLng(String address) {
        LatLng latLng;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://maps.googleapis.com/maps/api/geocode/json?address=" + address +"&key=AIzaSyDHyQC-zcYFwwBZPcv_05uIJ133p5uhZxQ", String.class);
        String result = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();

        if (statusCode == HttpStatus.OK) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject geometryJsonObject = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                JSONObject longName = jsonObject.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(0);

                double lat = (double) geometryJsonObject.get("lat");
                double lng = (double) geometryJsonObject.get("lng");

                String cityName = longName.getString("long_name");

                latLng = new LatLng(lat, lng);

                moviesInfoList.add(new MoviesInfo(moviesInfo.get("actor_1"), moviesInfo.get("actor_2"),
                        moviesInfo.get("actor_3"), moviesInfo.get("director"), moviesInfo.get("distributor"),
                        moviesInfo.get("fun_facts"), moviesInfo.get("locations"), moviesInfo.get("production_company"),
                        moviesInfo.get("release_year"), moviesInfo.get("title"), moviesInfo.get("writer"),
                        latLng, lat, lng, cityName));

                LOG.info("The lat lng is : " + latLng.toString());
                LOG.info("The city name is : " + cityName);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
