package com.Telegram.repository;

import com.Telegram.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Override
    List<City> findAll();

    City findAllByName(String name);

    @Override
    City save(City city);

    @Override
    void delete(City city);

    @Override
    Optional<City> findById(Long id);
}
