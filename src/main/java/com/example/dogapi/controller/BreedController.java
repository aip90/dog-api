package com.example.dogapi.controller;

import com.example.dogapi.base.BaseController;
import com.example.dogapi.request.BreedRequest;
import com.example.dogapi.service.BreedService;
import com.example.dogapi.util.ErrorResponseUtil;
import com.example.dogapi.validator.BreedValidator;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "rest/v1/breed")
public class BreedController extends BaseController {

    private final BreedService breedService;

    private BreedValidator breedValidator;

    @GetMapping("/sync")
    @ApiOperation(value = "Sync list all breed with sub breed from dog api")
    public ResponseEntity<?> sync() throws Exception{

        return ok("Sync list all breed with sub breed", breedService.sync());
    }

    @GetMapping("/list-dog-api")
    @ApiOperation(value = "Get list all breed with sub breed from dog api")
    public ResponseEntity<?> getListDogApi() throws Exception{
        return ok("Get list dog api", breedService.getListDogApi());
    }

    @GetMapping("/list")
    @ApiOperation(value = "Get list all breed with sub breed")
    public ResponseEntity<?> getList() throws Exception{
        return ok("Get list all breed with sub breed", breedService.getList());
    }

    @PutMapping(path = "/get/{id}")
    @ApiOperation(value = "Get breed by id")
    public ResponseEntity<?> getById (
            @PathVariable("id") String id) {
        return ok("Get breed by id", breedService.getById(id));
    }

    @PostMapping(path = "/create")
    @ApiOperation(value = "Create breed data")
    public ResponseEntity<?> create(
            @RequestBody BreedRequest breedRequest, BindingResult bindingResult) {

        breedValidator.validate(breedRequest, bindingResult);
        if (bindingResult.hasErrors())
            return badRequest(ErrorResponseUtil.collectError(bindingResult));

        return ok("Create breed data", breedService.create(breedRequest));
    }

    @PutMapping(path = "/update/{id}")
    @ApiOperation(value = "Update description breed")
    public ResponseEntity<?> update (
            @PathVariable("id") String id,
            @RequestParam(required = false) String description) {
        return ok("Update description breed", breedService.update(id, description));
    }

    @DeleteMapping(path = "/delete/{id}")
    @ApiOperation(value = "Delete breed data")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        return ok("Delete breed data", breedService.delete(id));
    }

    @GetMapping("/list-breed-images")
    @ApiOperation(value = "Get list breed images")
    public ResponseEntity<?> getListBreedImages(
            @RequestParam(value = "breedName", required = true) String breedName)
            throws Exception{
        return ok("Get list breed images",
                breedService.getListBreedImages(breedName));
    }
}
