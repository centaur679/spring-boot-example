package com.livk.auth.server;

import com.nimbusds.jose.util.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>
 * AuthServerAppTest
 * </p>
 *
 * @author livk
 * @date 2022/9/21
 */
@SpringBootTest
@AutoConfigureMockMvc
class AuthServerAppTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testPassword() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("grant_type", "password");
        params.set("username", "livk");
        params.set("password", "123456");
        params.set("scope", "livk.read");
        mockMvc.perform(post("/oauth2/token")
                        .header("Authorization", "Basic " + Base64.encode("livk-client:secret"))
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .params(params))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("sub", "livk").exists())
                .andExpect(jsonPath("iss", "http://localhost:9000").exists())
                .andExpect(jsonPath("token_type", "Bearer").exists())
                .andExpect(jsonPath("client_id", "livk-client").exists());
    }

    @Test
    public void testSms() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("grant_type", "sms");
        params.set("mobile", "18664960000");
        params.set("code", "123456");
        params.set("scope", "livk.read");
        mockMvc.perform(post("/oauth2/token")
                        .header("Authorization", "Basic " + Base64.encode("livk-client:secret"))
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .params(params))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("sub", "livk").exists())
                .andExpect(jsonPath("iss", "http://localhost:9000").exists())
                .andExpect(jsonPath("token_type", "Bearer").exists())
                .andExpect(jsonPath("client_id", "livk-client").exists());
    }
}
