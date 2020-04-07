package com.Telegram.service;

import com.Telegram.entity.City;
import com.Telegram.repository.CityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City saveCity(City city) {
        City cityFromDB = cityRepository.findAllByName(city.getName());
        if (cityFromDB == null) {
            cityRepository.save(city);
            return city;
        }
        return cityFromDB;
    }

    public List<City> findAllCities() {
        return cityRepository.findAll();
    }

    public City findByCityName(String name) {
        String nameToDB = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        return cityRepository.findAllByName(nameToDB);
    }

    public void deleteCity(City city) {
        cityRepository.delete(city);
    }

    public City updateCity(City city) {
        City cityFromDB = cityRepository.findById(city.getId()).get();
        BeanUtils.copyProperties(city, cityFromDB, "id");
        return cityRepository.save(cityFromDB);
    }
}
