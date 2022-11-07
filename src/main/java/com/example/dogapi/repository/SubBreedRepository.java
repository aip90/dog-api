package com.example.dogapi.repository;

import com.example.dogapi.entity.SubBreed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubBreedRepository extends JpaRepository<SubBreed, String> {

    @Query("SELECT sb FROM SubBreed sb WHERE sb.name = :name AND sb.breed.name = :breedName")
    SubBreed findByNameAndBreed(@Param("name") String name, @Param("breedName") String breedName);

    @Query("SELECT sb FROM SubBreed sb WHERE sb.breed.id = :breedId")
    List<SubBreed> findByBreed(String breedId);

    @Query("DELETE FROM SubBreed sb WHERE sb.breed.id = :breedId")
    @Modifying
    void deleteByBreed(String breedId);
}
