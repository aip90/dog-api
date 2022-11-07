package com.example.dogapi.service;

import com.example.dogapi.entity.Breed;
import com.example.dogapi.request.BreedRequest;

import java.util.ArrayList;
import java.util.List;

public interface BreedService {
    List<Breed> sync() throws Exception;

    Object getListDogApi() throws Exception;

    ArrayList<String> getListBreedImages(String breedName) throws Exception;

    List<Breed> getList() throws Exception;

    Breed getById(String id);

    Breed create(BreedRequest breedRequest);

    Breed update(String id, String description);

    Object delete(String id);
}
