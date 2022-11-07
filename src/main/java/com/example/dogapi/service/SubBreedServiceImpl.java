package com.example.dogapi.service;

import com.example.dogapi.entity.Breed;
import com.example.dogapi.entity.SubBreed;
import com.example.dogapi.repository.BreedRepository;
import com.example.dogapi.repository.SubBreedRepository;
import com.example.dogapi.request.SubBreedRequest;
import com.example.dogapi.rest.RestClient;
import com.example.dogapi.util.ErrorResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class SubBreedServiceImpl implements SubBreedService {

    private final BreedRepository breedRepository;

    private final SubBreedRepository subBreedRepository;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(timeout = 2)
    public Object getListByBreed(String name) throws Exception {

        ResponseEntity<Object> result;

        try{
            final String GET_LIST_BY_BREED =
                    "https://dog.ceo/api/breed/" + name + "/list";
            result = (ResponseEntity<Object>) RestClient.consume(GET_LIST_BY_BREED);
        }catch(HttpClientErrorException e){
            e.printStackTrace();
            String error = ErrorResponseUtil.errorResponseClientRequest(
                    e.getResponseBodyAsString());
            throw new Exception(error);
        }
        return result.getBody();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getListImagesByBreedAndSubBreed(String breedName, String subBreedName) throws Exception {

        ResponseEntity<Object> result;
        try{
            final String GET_LIST_IMAGES_BY_BREED_AND_SUB_BREED =
                    "https://dog.ceo/api/breed/" + breedName + "/" + subBreedName + "/images";
            result = (ResponseEntity<Object>) RestClient.consume(
                    GET_LIST_IMAGES_BY_BREED_AND_SUB_BREED);
        }catch(HttpClientErrorException e){
            e.printStackTrace();
            String error = ErrorResponseUtil.errorResponseClientRequest(
                    e.getResponseBodyAsString());
            throw new Exception(error);
        }

        return result.getBody();
    }

    @Override
    public List<SubBreed> create(SubBreedRequest subBreedRequest) {

        List<SubBreed> subBreedList = new ArrayList<>();

        Breed breedExist = breedRepository.findByName(subBreedRequest.getBreedName());
        if (breedExist == null){
            throw new IllegalStateException(
                    "Breed with name " + subBreedRequest.getBreedName() + " does not exist");
        }
        boolean isSubBreedExist = subBreedRequest.getSubBreedNameList()
                .stream().allMatch(subBreedName ->
                        subBreedRepository.findByNameAndBreed(
                                subBreedName, subBreedRequest.getBreedName()) != null);
        if (isSubBreedExist){
            throw new IllegalStateException("Sub breed already exist");
        }

        subBreedRequest.getSubBreedNameList().forEach(subBreedName -> {
            SubBreed subBreed = new SubBreed();
            subBreed.setCreatedDate(new Date());
            subBreed.setCreatedBy("admin");
            subBreed.setName(subBreedName);
            subBreed.setBreed(breedExist);
            subBreed.setIsSync(false);
            subBreedList.add(subBreed);
        });
        subBreedRepository.saveAll(subBreedList);

        return subBreedList;
    }

    @Override
    public SubBreed update(String id, String description) {

        SubBreed subBreed = subBreedRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException(
                            "Sub breed with id " + id + " does not exist"
                    ));

        subBreed.setUpdatedDate(new Date());
        subBreed.setUpdatedBy("admin");
        subBreed.setDescription(description);
        subBreedRepository.save(subBreed);

        return subBreed;
    }

    @Override
    public Object delete(String id) {

        boolean exists = subBreedRepository.existsById(id);
        if (!exists)
            throw new IllegalStateException(
                    "Sub breed with id " + id + " does not exist");

        subBreedRepository.deleteById(id);

        return null;
    }
}
