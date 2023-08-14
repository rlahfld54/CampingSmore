package com.green.campingsmore.community.board;

import com.green.campingsmore.MockMvcConfig;
import com.green.campingsmore.community.comment.CommentController;
import com.green.campingsmore.community.comment.CommentService;
import com.green.campingsmore.config.security.AuthenticationFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@MockMvcConfig
@WebMvcTest(BoardController.class)// 내 컨트롤러
@AutoConfigureMockMvc(addFilters = false)
@Import({BoardController.class})

class BoardControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthenticationFacade FACADE;
    @MockBean
    private BoardService service;


//    public void testPostPic() throws Exception {
//        // Creating a sample multipart file
//        MockMultipartFile mockMultipartFile = new MockMultipartFile(
//                "file", "test.jpg", "image/jpeg", "test data".getBytes()
//        );
//
//        // Stubbing service method
//        given(service.postOnePic(anyLong(), any(MultipartFile.class))).withFailMessage("mockedImageUrl");
//
//        // Performing the POST request
//        mvc.perform(multipart("/onepice")
//                        .param("iboard", "1")
//                        .file(mockMultipartFile)
//                .andExpect(status().isOk())
//                .andExpect(content().string("mockedImageUrl")));
//
//        // Verifying interactions
//        verify(service).postOnePic(eq(1L), any(MultipartFile.class));
//    }
    @Test
    void uploadFiles() {
    }

    @Test
    void updContent() {
    }

    @Test
    void selMyBoard() {
    }

    @Test
    void delWriteBoard() {
    }

    @Test
    void delBoard() {
    }

    @Test
    void selBoardList() {
    }

    @Test
    void categoryBoardList() {
    }

    @Test
    void selBoard() {
    }

    @Test
    void deBoard() {
    }

    @Test
    void delOnePic() {
    }
}