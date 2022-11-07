package com.example.dogapi.controller;

import com.example.dogapi.base.BaseController;
import com.example.dogapi.request.SubBreedRequest;
import com.example.dogapi.service.SubBreedService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "rest/v1/sub-breed")
public class SubBreedController extends BaseController {

    private final SubBreedService subBreedService;

    @GetMapping("/list-by-breed")
    @ApiOperation(value = "Get list all sub breed by breed")
    public ResponseEntity<?> getListByBreed(
            @RequestParam(value = "name", required = true)
            String name) throws Exception{
        return ok("Get list all sub breed by breed",
                subBreedService.getListByBreed(name));
    }

    @GetMapping("/list-images-by-breed-and-sub-breed")
    @ApiOperation(value = "Get list Images by breed and sub breed")
    public ResponseEntity<?> getListImagesByBreedAndSubBreed(
            @RequestParam(value = "breedName", required = true)
            String breedName,
            @RequestParam(value = "subBreedName", required = true)
            String subBreedName) throws Exception{
        return ok("Get list Images by breed and sub breed",
                subBreedService.getListImagesByBreedAndSubBreed(breedName, subBreedName));
    }

    @PostMapping(path = "/create")
    @ApiOperation(value = "Create sub breed data")
    public ResponseEntity<?> create(@RequestBody SubBreedRequest subBreedRequest) {
        return ok("Create sub breed data", subBreedService.create(subBreedRequest));
    }

    @PutMapping(path = "/update/{id}")
    @ApiOperation(value = "Update description sub breed")
    public ResponseEntity<?> update (
            @PathVariable("id") String id,
            @RequestParam(required = false) String description) {
        return ok("Update description sub breed", subBreedService.update(id, description));
    }

    @DeleteMapping(path = "/delete/{id}")
    @ApiOperation(value = "Delete sub breed data")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        return ok("Delete sub breed data", subBreedService.delete(id));
    }
}
