package com.livk.excel.mvc.controller;

import com.livk.autoconfigure.excel.annotation.ExcelReturn;
import com.livk.util.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>
 * InfoControllerTest
 * </p>
 *
 * @author livk
 * @date 2022/11/2
 */
@AutoConfigureMockMvc
@SpringBootTest
class InfoControllerTest {

    @Autowired
    MockMvc mockMvc;

    ClassPathResource resource = new ClassPathResource("outFile.xls");

    MockMultipartFile file = new MockMultipartFile("file", "outFile.xlsx", MediaType.MULTIPART_FORM_DATA_VALUE, resource.getInputStream());

    InfoControllerTest() throws IOException {
    }

    @Test
    void uploadList() throws Exception {
        mockMvc.perform(multipart(POST, "/uploadList")
                        .file(file))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void uploadAndDownload() throws Exception {
        mockMvc.perform(multipart(POST, "/uploadAndDownload")
                        .file(file))
                .andExpect(status().isOk())
                .andDo(result -> {
                    ByteArrayInputStream in = new ByteArrayInputStream(result.getResponse().getContentAsByteArray());
                    FileUtils.testDownload(in, "uploadAndDownloadMock" + ExcelReturn.Suffix.XLS.getName());
                });
        File outFile = new File("./uploadAndDownloadMock" + ExcelReturn.Suffix.XLS.getName());
        Assertions.assertTrue(outFile.exists());
        Assertions.assertTrue(outFile.delete());
    }
}
