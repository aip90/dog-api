package com.example.dogapi.repository;

import com.example.dogapi.entity.Breed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BreedRepository extends JpaRepository<Breed, String> {

    @Query("SELECT b FROM Breed b WHERE b.name = :name")
    Breed findByName(@Param("name") String name);
}
