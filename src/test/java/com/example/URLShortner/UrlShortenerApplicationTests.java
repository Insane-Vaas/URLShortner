package com.example.URLShortner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UrlShortenerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void runTest() throws Exception {


        String folderPath = "/testdata";
        File folder = new File(getClass().getResource(folderPath).toURI());
        File[] jsonFiles = folder.listFiles((dir, name) -> name.endsWith(".json"));

        if (jsonFiles == null || jsonFiles.length == 0) {
            throw new RuntimeException("No test JSON files found.");
        }

        for (File jsonFile : jsonFiles) {
            System.out.println("Running test with file: " + jsonFile.getName());

            JsonNode testJson = objectMapper.readTree(jsonFile);
            JsonNode requestBody = testJson.get("request");
            JsonNode expectedResponse = testJson.get("expectedResponse");


            MvcResult result = null;
            String call = testJson.get("method").textValue();
            result = switch (call) {
                case "POST" ->
                        mockMvc.perform(MockMvcRequestBuilders.post(testJson.get("url").textValue()).contentType(MediaType.APPLICATION_JSON).content(requestBody.toString()))
                                .andReturn();
                case "GET" ->
                        mockMvc.perform(MockMvcRequestBuilders.get(testJson.get("url").textValue()).contentType(MediaType.APPLICATION_JSON).content(requestBody.toString()))
                                .andReturn();
                case "PUT" ->
                        mockMvc.perform(MockMvcRequestBuilders.put(testJson.get("url").textValue()).contentType(MediaType.APPLICATION_JSON).content(requestBody.toString()))
                                .andReturn();
                case "DELETE" ->
                        mockMvc.perform(MockMvcRequestBuilders.delete(testJson.get("url").textValue()).contentType(MediaType.APPLICATION_JSON).content(requestBody.toString()))
                                .andReturn();
                default -> result;
            };

            if (result != null) {
                String actualResponse = result.getResponse().getContentAsString();
                JsonNode actualJson = objectMapper.readTree(actualResponse);

                assertEquals(expectedResponse, actualJson);

            }
        }

    }
}
