package com.example.dogapi.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BreedRequest {

    private String breedName;

    private List<String> subBreedNameList;
}
