package com.example.dogapi.service;

import com.example.dogapi.entity.Breed;
import com.example.dogapi.entity.SubBreed;
import com.example.dogapi.repository.BreedRepository;
import com.example.dogapi.repository.SubBreedRepository;
import com.example.dogapi.request.BreedRequest;
import com.example.dogapi.rest.RestClient;
import com.example.dogapi.util.ErrorResponseUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BreedServiceImpl implements BreedService {

    private static final Logger logger = LoggerFactory.getLogger(BreedServiceImpl.class);

    private final BreedRepository breedRepository;

    private final SubBreedRepository subBreedRepository;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public List<Breed> sync() throws Exception {

        List<Breed> breedList = new ArrayList<>();
        List<SubBreed> subBreedList = new ArrayList<>();
        AtomicReference<Breed> breedExist = new AtomicReference<>(new Breed());

        try{
            final String GET_LIST_ALL_BREEDS =
                    "https://dog.ceo/api/breeds/list/all";
            ResponseEntity<LinkedHashMap> result =
                    (ResponseEntity<LinkedHashMap>)
                            RestClient.consume(GET_LIST_ALL_BREEDS);
            LinkedHashMap<String, ArrayList<String>> response =
                    (LinkedHashMap<String, ArrayList<String>>)
                            Objects.requireNonNull(result.getBody()).get("message");

            response.forEach((breedName, subBreedNameList) -> {
                breedExist.set(breedRepository.findByName(breedName));
                Breed breed = new Breed();
                if (breedExist.get() == null){
                    breed.setCreatedDate(new Date());
                    breed.setCreatedBy("system");
                    breed.setName(breedName);
                    breed.setIsSync(true);
                    breed.setSubBreedList(subBreedNameList);
                    breedList.add(breed);
                }

                subBreedNameList.forEach(subBreedName -> {
                    SubBreed subBreedExist =
                            subBreedRepository.findByNameAndBreed(
                                    subBreedName, breedExist.get() != null ?
                                            breedExist.get().getName() : null);
                    if (subBreedExist == null){
                        SubBreed subBreed = new SubBreed();
                        subBreed.setCreatedDate(new Date());
                        subBreed.setCreatedBy("system");
                        subBreed.setName(subBreedName);
                        subBreed.setBreed(breed);
                        subBreed.setIsSync(true);
                        subBreedList.add(subBreed);
                    }
                });
            });
            breedRepository.saveAll(breedList);
            subBreedRepository.saveAll(subBreedList);
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("Error found");
        }
        return breedList;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(timeout = 5)
    public Object getListDogApi() throws Exception {

        ResponseEntity<Object> result;

        try {
            final String GET_LIST_ALL_BREEDS =
                    "https://dog.ceo/api/breeds/list/all";
            result = (ResponseEntity<Object>) RestClient.consume(GET_LIST_ALL_BREEDS);
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("Error found");
        }
        return result.getBody();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public ArrayList<String> getListBreedImages(String breedName) throws Exception {

        ArrayList<String> breedImagesList = new ArrayList<>();

        try{
            final String GET_LIST_BREED_IMAGES = "https://dog.ceo/api/breed/" + breedName + "/images";
            ResponseEntity<LinkedHashMap> result = (ResponseEntity<LinkedHashMap>)
                    RestClient.consume(GET_LIST_BREED_IMAGES);
            ArrayList<String> response = (ArrayList<String>)
                    Objects.requireNonNull(result.getBody()).get("message");
            if (breedName.equals("shiba")){
                response.forEach(url -> {
                    String output = "";
                    Pattern pattern = Pattern.compile("shiba-(.*?).jpg|shiba-(.*?)i.jpg" , Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(url);
                    while (matcher.find()) {
                        output = matcher.group(1);
                    }
                    output = output.replaceAll("([a-z])", "");
                    int num = Integer.parseInt(output);
                    if(num % 2 != 0)
                        breedImagesList.add(url);
                });
            } else {
                breedImagesList.addAll(response);
            }
        }catch(HttpClientErrorException e){
            e.printStackTrace();
            String error =
                    ErrorResponseUtil.errorResponseClientRequest(
                            e.getResponseBodyAsString());
            throw new Exception(error);
        }

        return breedImagesList;
    }

    @Override
    public List<Breed> getList() throws Exception {

        List<Breed> breedList = new ArrayList<>();

        try {
            breedList = breedRepository.findAll();
            List<Breed> breedListNew = new ArrayList<>();
            for (Breed breed: breedList){
                List<SubBreed> subBreedList = subBreedRepository.findByBreed(breed.getId());
                if (breed.getName().equals("sheepdog") || breed.getName().equals("terrier")){
                    for (SubBreed subBreed: subBreedList){
                        Breed breedNew = new Breed();
                        breedNew.setName(breed.getName()+"-"+subBreed.getName());
                        breedNew.setSubBreedList(new ArrayList<>());
                        breedListNew.add(breedNew);
                    }
                    if (breed.getName().equals("terrier")){
                        ArrayList<String> breedImagesList = getListBreedImages(breed.getName());
                        breedListNew.stream().filter(breedData -> breedData.getName().contains("terrier"))
                                .forEach(breedNew -> {
                                    ArrayList<String> breedImages = breedImagesList.stream()
                                            .filter(s -> s.contains(breedNew.getName()))
                                            .collect(Collectors.toCollection(ArrayList::new));
                                    breedNew.setBreedImageList(breedImages);
                                });
                    }
                } else {
                    ArrayList<String> subBreedArrayList =  new ArrayList<>();
                    subBreedArrayList = subBreedList.stream().map(SubBreed::getName)
                            .collect(Collectors.toCollection(ArrayList::new));
                    breed.setSubBreedList(subBreedArrayList);
                }
            }

            breedList = breedList.stream().filter(
                            breed -> !breed.getName().equals("sheepdog") &&
                                    !breed.getName().equals("terrier"))
                    .collect(Collectors.toList());
            breedList.addAll(breedListNew);
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("Error found");
        }

        return breedList;
    }

    @Override
    @Cacheable(cacheNames = "breed", key = "#id")
    public Breed getById(String id) {

        logger.info("fetching breed from db");

        Breed breed = breedRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Breed with id " + id + " does not exist"
                ));

        ArrayList<String> subBreedArrayList =  new ArrayList<>();
        List<SubBreed> subBreedList = subBreedRepository.findByBreed(id);
        if (subBreedList.size() > 0){
            subBreedArrayList = subBreedList.stream().map(SubBreed::getName)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        breed.setSubBreedList(subBreedArrayList);

        return breed;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Breed create(BreedRequest breedRequest) {

        Breed breedExist = breedRepository.findByName(breedRequest.getBreedName());
        if (breedExist != null){
            throw new IllegalStateException(
                    "Breed with name " +
                            breedRequest.getBreedName() + " already exist");
        }

        if (breedRequest.getSubBreedNameList().size() > 0){
            boolean isSubBreedExist = breedRequest.getSubBreedNameList()
                    .stream().allMatch(subBreedName ->
                            subBreedRepository.findByNameAndBreed(
                                    subBreedName, breedRequest.getBreedName()) != null);
            if (isSubBreedExist)
                throw new IllegalStateException("Sub breed already exist");
        }

        Breed breed = new Breed();
        breed.setCreatedDate(new Date());
        breed.setCreatedBy("admin");
        breed.setName(breedRequest.getBreedName());
        breed.setIsSync(false);
        breed.setSubBreedList(new ArrayList<>(breedRequest.getSubBreedNameList()));
        breedRepository.save(breed);

        List<SubBreed> subBreedList = new ArrayList<>();
        breedRequest.getSubBreedNameList().forEach(subBreedName -> {
            SubBreed subBreed = new SubBreed();
            subBreed.setCreatedDate(new Date());
            subBreed.setCreatedBy("admin");
            subBreed.setName(subBreedName);
            subBreed.setBreed(breed);
            subBreed.setIsSync(false);
            subBreedList.add(subBreed);
        });
        subBreedRepository.saveAll(subBreedList);

        return breed;
    }

    @Override
    @CachePut(cacheNames = "breed", key = "#id")
    public Breed update(String id, String description) {

        Breed breed = breedRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                    "Breed with id " + id + " does not exist"
                ));

        breed.setUpdatedDate(new Date());
        breed.setUpdatedBy("admin");
        breed.setDescription(description);
        breedRepository.save(breed);

        logger.info("breed updated with new description");

        return breed;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    @CacheEvict(cacheNames = "breed", key = "#id")
    public Object delete(String id) {

        boolean exists = breedRepository.existsById(id);
        if (!exists)
            throw new IllegalStateException(
                    "Breed with id " + id + " does not exist");

        breedRepository.deleteById(id);
        subBreedRepository.deleteByBreed(id);

        return null;
    }
}
