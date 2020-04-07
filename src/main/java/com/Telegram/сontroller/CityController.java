package com.Telegram.сontroller;

import com.Telegram.entity.City;
import com.Telegram.service.CityService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public String helloWorld(Model model){
        return "Hello World!";
    }

    @GetMapping("/cities")
    public List<City> getCities(){
        return cityService.findAllCities();
    }

    @GetMapping("/city")
    public City getCityByName(@RequestParam String name){
        return cityService.findByCityName(name);
    }

    @PostMapping("/addCity")
    public City addCity(@RequestBody City city){
        return cityService.saveCity(city);
    }

    @PutMapping("/editCity")
    public City editCityById(@RequestBody City city){
        return cityService.updateCity(city);
    }

    @DeleteMapping("/deleteCity")
    public void deleteCity(@RequestBody City city){
        cityService.deleteCity(city);
    }
}
