package com.green.campingsmore.community.board;

import com.green.campingsmore.community.board.model.*;
import com.green.campingsmore.community.board.utils.FileUtils;
import com.green.campingsmore.community.comment.CommentService;
import com.green.campingsmore.community.comment.model.CommentPageDto;
import com.green.campingsmore.community.comment.model.CommentRes;
import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.config.security.model.MyUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import({BoardService.class, AuthenticationFacade.class, FileUtils.class})

class BoardServiceTest {
    @Value("${file.dir}")
    private String fileDir;

    @BeforeEach
    void setUp() {
        testPics = new ArrayList<>();
        testPics.add(new MockMultipartFile("pic1.jpg", new byte[0]));
        testPics.add(new MockMultipartFile("pic2.jpg", new byte[0]));
    }
    @MockBean
    private BoardMapper mapper;

    @Autowired
    private BoardService service;

    @MockBean
    private List<MultipartFile> testPics;

    @MockBean
    private AuthenticationFacade FACADE;
    @Autowired
    private CommentService commentService;

//        @BeforeEach
//    void beforeEach() {
//
//        UserDetails user = createUserDetails();
//
////            MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
//
//        SecurityContext context;
//            context = SecurityContextHolder.getContext();
//            context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
//
//    }
//
//        private UserDetails createUserDetails() {
//        List<String> roles = new ArrayList<>();
//        roles.add("ROLE_USER");
//
//        UserDetails userDetails = MyUserDetails.builder()
//                .iuser(10L)
//                .name("김철수")
//                .roles(roles)
//                .build();
//        return userDetails;
//    }

//    @Test 안되는거<<
//    void postboardTest() {
//        BoardEntity entity = new BoardEntity();
//        entity.setIuser(FACADE.getLoginUserPk());
//        given(mapper.insBoard(entity)).willReturn(1L);
//        Long result = service.postboard();
//
//        // Verify that the mapper's insBoard method was called with the correct entity
//        verify(mapper).insBoard(any());
//
//        // Check the result, assuming that the insBoard method returns 1L
//        // If the actual return value is different, adjust the assertion accordingly
//        assertEquals(null, result);
//    }







    @Test
    @Transactional
    void postPicTest() throws Exception {
        // Mocking
        given(mapper.insBoardPic(any())).willReturn(1L); // 또는 원하는 대로 모킹 설정

        // 실행
        List<String> fileUrls = service.postPic(1L, testPics);

        // 검증
        verify(mapper).insBoardPic(any()); // insBoardPic이 호출되었는지 확인

        // 기대 결과 검증
        // 만약 서비스에서 사용하는 실제 로직에 따라 fileUrls를 검증하는 로직이 있다면 추가하십시오.
        // 예를 들어, fileUrls의 사이즈나 URL 패턴을 확인할 수 있을 것입니다.
    }

    @Test
    public void postOnePic() throws Exception {
        Long iboard = 1L;
        MultipartFile pic = new MockMultipartFile("testPic.jpg", "testPic.jpg", "image/jpeg", "test image".getBytes());

        BoardEntity expectedBoardEntity = new BoardEntity();
        expectedBoardEntity.setIboard(iboard);

        given(mapper.insBoardOnePic(any())).willReturn(1L);

        String result = service.postOnePic(iboard, pic);


        assertNotNull(result);
        assertTrue(result.contains("file:///"));


        verify(mapper, times(1)).insBoardOnePic(any());
    }


    @Test
    void UpdContent() {
        BoardInsDto dto = new BoardInsDto();
        dto.setIboard(1L);
        dto.setTitle("테스트 제목");
        dto.setCtnt("테스트 내용");
        dto.setIcategory(2L);

        // 매퍼의 동작을 모의화
        given(mapper.updBoardMain(any())).willReturn(1L);

        Long result = service.updContent(dto);

        // eq()를 사용하여 올바른 인수와 일치시키는 메서드 호출 확인
        verify(mapper).updBoardMain(argThat(argument ->
                argument.getIboard().equals(dto.getIboard()) &&
                        argument.getIuser().equals(FACADE.getLoginUserPk()) &&
                        argument.getTitle().equals(dto.getTitle()) &&
                        argument.getCtnt().equals(dto.getCtnt()) &&
                        argument.getIcategory().equals(dto.getIcategory())
        ));

        assertEquals(1L, result);
    }

    @Test
    void DelWriteBoard() {
        // Given
        Long iboard = 1L;

        // Mock mapper
        given(mapper.delWriteBoard(iboard)).willReturn(1L);

        // When
        Long result = service.delWriteBoard(iboard);

        // Then
        verify(mapper).delPicBoard(iboard);
        verify(mapper).delWriteBoard(iboard);

        assertEquals(1L, result);
    }

    @Test
    void selMyBoard() {
        given(FACADE.getLoginUserPk()).willReturn(1L);

        BoardMyDto dto = new BoardMyDto();
        dto.setIuser(FACADE.getLoginUserPk());


        List<BoardMyVo> list = service.selMyBoard(dto);
        given(mapper.selMyBoard(any())).willReturn(list);

        assertEquals(0,list.size());
    }

    @Test
    public void testDelBoard_Success() {
        // Given
        BoardDelDto dto = new BoardDelDto();
        dto.setIboard(1L);

        given(FACADE.getLoginUserPk()).willReturn(1L);
        given(mapper.delBoard(eq(dto))).willReturn(1L); // 성공적으로 삭제되었다고 가정

        // When
        Long result = service.delBoard(dto);

        // Then
        assertEquals(1L, result);
        // 추가 검증 로직 작성 가능
    }

    @Test
    public void testDelBoard_Failure() {
        // Given
        BoardDelDto dto = new BoardDelDto();
        dto.setIboard(1L);

        given(FACADE.getLoginUserPk()).willReturn(1L);
        given(mapper.delBoard(eq(dto))).willReturn(0L); // 삭제 실패

        // When
        Long result = service.delBoard(dto);

        // Then
        assertEquals(0L, result);
        // 추가 검증 로직 작성 가능
    }
    @Test
    public void testSelBoardList() {
        // Given
        BoardPageDto dto = new BoardPageDto();
        dto.setPage(1);
        dto.setRow(10);

        List<BoardListVo> mockList = new ArrayList<>(); // 적절한 Mock 데이터 생성
        when(mapper.selBoardList(eq(dto))).thenReturn(mockList);

        when(mapper.maxBoard()).thenReturn(20L);

        // When
        BoardRes result = service.selBoardList(dto);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getIsMore());
        assertEquals(10, result.getRow());
        assertEquals(2, result.getMaxPage());
        assertEquals(mockList, result.getList());
    }

    @Test
    public void testCategoryBoardList() {
        // Given
        BoardPageDto dto = new BoardPageDto();
        dto.setPage(1);
        dto.setRow(10);

        List<BoardListVo> mockList = new ArrayList<>(); // 적절한 Mock 데이터 생성
        when(mapper.categoryBoardList(eq(dto))).thenReturn(mockList);

        when(mapper.maxBoard()).thenReturn(20L);

        // When
        BoardRes result = service.categoryBoardList(dto);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getIsMore());
        assertEquals(10, result.getRow());
        assertEquals(2, result.getMaxPage());
        assertEquals(mockList, result.getList());
    }

    @Test
    public void testSelBoard() {
        // Given
        BoardSelPageDto dto = new BoardSelPageDto();
        dto.setPage(1);
        dto.setRow(10);
        dto.setTitle("Test Title");

        List<BoardSelVo> mockList = new ArrayList<>(); // 적절한 Mock 데이터 생성
        when(mapper.selBoard(eq(dto))).thenReturn(mockList);

        when(mapper.maxSelBoard(eq(dto))).thenReturn(20L); // 예상 최대 페이지

        // When
        BoardSelRes result = service.selBoard(dto);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getIsMore());
        assertEquals("Test Title", result.getTitle());
        assertEquals(10, result.getRow());
        assertEquals(2, result.getMaxPage());
        assertEquals(1, result.getMidPage());
        assertEquals(1, result.getNowPage());
        assertEquals(mockList, result.getList());
    }

//    @Test
//    public void testDeBoard() {
//        // Given
//        BoardDeDto dto = new BoardDeDto();
//        dto.setIboard(1L);
//
//        BoardDeVo mockBoardDeVo = new BoardDeVo(1L, 1L, "Title", "Content", "Author", "Category", LocalDateTime.now(), 1L);
//        when(mapper.viewBoard(eq(dto))).thenReturn(mockBoardDeVo);
//        when(mapper.deBoard(eq(dto))).thenReturn(mockBoardDeVo);
//
//        List<BoardPicVo> mockPicList = new ArrayList<>();
//        when(mapper.picBoard(eq(dto))).thenReturn(mockPicList);
//
//        CommentPageDto mockCommentPageDto = new CommentPageDto();
//        CommentRes mockCommentRes = new CommentRes(1,2,3,4,);
//        when(commentService.selComment(eq(mockCommentPageDto))).thenReturn(mockCommentRes);
//
//        when(FACADE.getLoginUserPk()).thenReturn(1L);
//
//        // When
//        BoardCmtDeVo result = service.deBoard(dto);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(1L, result.getIuser());
//        assertEquals(mockBoardDeVo, result.getBoardDeVo());
//        assertEquals(mockPicList, result.getPicList());
//        assertEquals(mockCommentRes, result.getCommentList());
//    }

    @Test
    public void testDelOnePic() throws Exception {
        // Given
        BoardPicDelDto dto = new BoardPicDelDto();
        dto.setIboard(1L);
        dto.setIboardpic(1L);

        File mockFile = mock(File.class);
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.listFiles()).thenReturn(new File[] {mockFile});
        when(mockFile.getName()).thenReturn("test.jpg");

        when(mapper.selPicName(eq(dto.getIboardpic()))).thenReturn("test.jpg");
        when(mapper.delOnePic(eq(dto))).thenReturn(1L);

//
//        PowerMockito.mockStatic(FileUtils.class); // FileUtils 클래스를 모의
//        PowerMockito.when(FileUtils.getAbsolutePath(anyString())).thenReturn("absolutePath");
//        PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(mockFile);

        // When
        Long result = service.delOnePic(dto);

        // Then
        assertEquals(1L, result);
        verify(mockFile).delete();
        verify(mockFile).listFiles();
    }

    @Test
    void insCategory() {
    }
}