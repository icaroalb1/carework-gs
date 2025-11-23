package com.carework.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CheckinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createCheckin_withApiKey_shouldReturnCreated() throws Exception {
        String json = "{\"userId\":\"11111111-1111-1111-1111-111111111111\",\"mood\":4,\"stress\":2,\"sleepQuality\":4}";

        mockMvc.perform(post("/api/checkins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-Key", "carework-secret")
                        .content(json))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}