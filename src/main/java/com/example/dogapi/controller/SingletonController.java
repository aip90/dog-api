package com.example.dogapi.controller;

import com.example.dogapi.singleton.Singleton;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@AllArgsConstructor
@RestController
@RequestMapping(path = "rest/v1/singleton")
public class SingletonController {

    @GetMapping
    @ApiOperation(value = "Singleton example")
    public ArrayList<String> example() throws Exception{

        ArrayList<String> result = new ArrayList<>();

        Singleton x = Singleton.getInstance();

        Singleton y = Singleton.getInstance();

        Singleton z = Singleton.getInstance();

        result.add(String.valueOf(x.hashCode()));
        result.add(String.valueOf(y.hashCode()));
        result.add(String.valueOf(z.hashCode()));

        return result;
    }
}
