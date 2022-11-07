package com.example.dogapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ContextConfiguration(classes = {DogApiApplication.class})
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BreedDataCreateTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void create() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/rest/v1/breed/create")
                        .content(requestBody()).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    private String requestBody() {
        return "{\n" +
                "    \"breedName\": \"test breed\",\n" +
                "    \"subBreedNameList\": [\n" +
                "         \"test sub breed 1\",\n" +
                "         \"test sub breed 2\"\n" +
                "    ]\n" +
                "}";
    }
}
