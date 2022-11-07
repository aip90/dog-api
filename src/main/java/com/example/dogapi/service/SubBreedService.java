package com.example.dogapi.service;

import com.example.dogapi.entity.SubBreed;
import com.example.dogapi.request.SubBreedRequest;

import java.util.List;

public interface SubBreedService {

    Object getListByBreed(String name) throws Exception;

    Object getListImagesByBreedAndSubBreed(String breedName, String subBreedName) throws Exception;

    List<SubBreed> create (SubBreedRequest subBreedRequest);

    SubBreed update (String id, String description);

    Object delete(String id);
}
