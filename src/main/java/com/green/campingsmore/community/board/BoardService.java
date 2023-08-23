package com.green.campingsmore.community.board;

import com.green.campingsmore.community.board.model.*;
import com.green.campingsmore.community.comment.CommentMapper;
import com.green.campingsmore.community.comment.CommentService;
import com.green.campingsmore.community.comment.model.CommentPageDto;
import com.green.campingsmore.community.comment.model.CommentRes;
import com.green.campingsmore.community.comment.model.CommentVo;
import com.green.campingsmore.config.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import com.green.campingsmore.community.board.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.ceil;
import static java.lang.Math.decrementExact;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper mapper;
    private final CommentService commentService;
    private final AuthenticationFacade FACADE;
    private final int ROW = 15;
    private final int Page = 1;

    @Value("${file.dir}")
    private String fileDir;

//    public void test() {
//        log.info("service-test-iuser : {}", facade.getLoginUserPk());
//    }

    //    @Transactional(rollbackFor = Exception.class)
//    public Long postBoard(BoardInsDto dto, List<MultipartFile> pics) throws Exception {
//        BoardEntity entity = new BoardEntity();
//        entity.setIcategory(1L);
//        entity.setTitle("");
//        entity.setCtnt("");
//        entity.setIuser(1L);
//        mapper.insBoard(entity);
//
//        Long iboard = entity.getIboard();
//
//        entity.setIuser(dto.getIuser());
//        entity.setTitle(dto.getTitle());
//        entity.setCtnt(dto.getCtnt());
//        entity.setIcategory(dto.getIcategory());
//        mapper.updBoardMain(entity);
//
//        if (pics != null) {
//            String centerPath = String.format("boardPics/%d", entity.getIboard());
//            String targetPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), centerPath);
//            File file = new File(targetPath);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//            List<BoardPicEntity> list = new ArrayList<>();
//            for (int i = 0; i < pics.size(); i++) {
//                String originFile = pics.get(i).getOriginalFilename();
//                String saveName = FileUtils.makeRandomFileNm(originFile);
//                File fileTarget = new File(targetPath + "/" + saveName);
//                try {
//                    pics.get(i).transferTo(fileTarget);
//                } catch (IOException e) {
//                    throw new Exception("파일저장을 실패했습니다");
//                }
//                BoardPicEntity picEntity = new BoardPicEntity();
//                picEntity.setIboard(entity.getIboard());
//                picEntity.setPic("file:///D:/"+fileDir+centerPath+"/"+saveName);
//                list.add(picEntity);
//                mapper.insBoardPic(list);
//            }
//
//        }else {
//            FileUtils.delFolder(FileUtils.getAbsolutePath(fileDir));
//            mapper.delWriteBoard(entity);
//        }
//        return iboard;
//    }

    public Long postboard() {
        try {
            BoardEntity entity = new BoardEntity();

            entity.setIuser(FACADE.getLoginUserPk());
            entity.setIcategory(1L);
            entity.setTitle("");
            entity.setCtnt("");
            mapper.insBoard(entity);
            Long iboard = entity.getIboard();
            return iboard;
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public List<String> postPic(Long iboard, List<MultipartFile> pics) throws Exception {
        List<String> fileUrls = new ArrayList<>();
        if (pics != null) {
            BoardEntity entity = new BoardEntity();
            entity.setIboard(iboard);
            String centerPath = String.format("boardPics/%d", entity.getIboard());
            String targetPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), centerPath);
            File file = new File(targetPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            List<BoardPicEntity> picEntities = new ArrayList<>();
            for (MultipartFile pic : pics) {
                String originFile = pic.getOriginalFilename();
                String saveName = FileUtils.makeRandomFileNm(originFile);
                File fileTarget = new File(targetPath + "/" + saveName);
                try {
                    pic.transferTo(fileTarget);
                } catch (IOException e) {
                    throw new Exception("파일저장을 실패했습니다");
                }
                BoardPicEntity picEntity = new BoardPicEntity();
                picEntity.setIboard(entity.getIboard());
                picEntity.setPic(centerPath + "/" + saveName);
                picEntities.add(picEntity);

                fileUrls.add("img/"+centerPath + "/" + saveName);
            }
            mapper.insBoardPic(picEntities);
        }
        return fileUrls;
    }

    @Transactional(rollbackFor = Exception.class)
    public String postOnePic(Long iboard, MultipartFile pic) throws Exception {
        if (pic != null) {
            BoardEntity entity = new BoardEntity();
            entity.setIboard(iboard);
            String centerPath = String.format("boardPics/%d", entity.getIboard());
            String targetPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), centerPath);
            File file = new File(targetPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            String originFile = pic.getOriginalFilename();
            String saveName = FileUtils.makeRandomFileNm(originFile);
            File fileTarget = new File(targetPath + "/" + saveName);
            try {
                pic.transferTo(fileTarget);
            } catch (IOException e) {
                throw new Exception("파일저장을 실패했습니다");
            }

            BoardPicEntity picEntity = new BoardPicEntity();
            picEntity.setIboard(entity.getIboard());
            picEntity.setPic(centerPath + "/" + saveName);
            mapper.insBoardOnePic(picEntity);

            return "img/"+centerPath + "/" + saveName;
        }
        return null;
    }

    public Long updContent(BoardInsDto dto) {
        try {
            BoardEntity entity = new BoardEntity();
            entity.setIboard(dto.getIboard());
            entity.setIuser(FACADE.getLoginUserPk());
            entity.setTitle(dto.getTitle());
            entity.setCtnt(dto.getCtnt());
            entity.setIcategory(dto.getIcategory());
            return mapper.updBoardMain(entity);
        } catch (Exception e) {
            return null;
        }
    }
//    public Long updBoard(List<MultipartFile> pic, BoardUpdDto dto) {
//        BoardEntity entity = new BoardEntity();
//        entity.setIboard(dto.getIboard());
//        entity.setTitle(dto.getTitle());
//        entity.setCtnt(dto.getCtnt());
//        entity.setIuser(dto.getIuser());
//        Long result = mapper.updBoard(entity);
//        BoardPicEntity boardPicEntity = new BoardPicEntity();
//        boardPicEntity.setIboardpic(dto.getIboard());
//        mapper.delPic(boardPicEntity);
//        if (pic != null) {
//            String centerPath = String.format("boardPics/%d", entity.getIboard());
//            String targetPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), centerPath);
//            File file = new File(targetPath);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//            List<BoardPicEntity> list = new ArrayList<>();
//
//            for (int i = 0; i < pic.size(); i++) {
//                String originFile = pic.get(i).getOriginalFilename();
//                String saveName = FileUtils.makeRandomFileNm(originFile);
//                File fileTarget = new File(targetPath + "/" + saveName);
//                try {
//                    pic.get(i).transferTo(fileTarget);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                BoardPicEntity picEntity = new BoardPicEntity();
//                picEntity.setIboard(entity.getIboard());
//                picEntity.setPic(saveName);
//                list.add(picEntity);
//                mapper.insBoardPic(list);
//            }
//        }

        //        return result; // 파일이 없을 경우 게시글 정보 업데이트 결과 리턴
//    }


    public Long delWriteBoard(Long iboard) {
        try {
            mapper.delPicBoard(iboard);
            String centerPath = String.format("boardPics/%d", iboard);
            FileUtils.delFolder(fileDir + centerPath);

            return mapper.delWriteBoard(iboard);
        } catch (Exception e) {
            return null;
        }
    }

    public List<BoardMyVo> selMyBoard(BoardMyDto dto) {
        log.info("유저 PK  : {}", FACADE.getLoginUserPk());
        return mapper.selMyBoard(dto);
    }

    public Long delBoard(BoardDelDto dto) {
        dto.setIuser(FACADE.getLoginUserPk());
        try {
            mapper.delBoardPic(dto.getIboard());

            String centerPath = String.format("boardPics/%d", dto.getIboard());
            FileUtils.delFolder(fileDir + centerPath);
            return mapper.delBoard(dto);
        } catch (Exception e) {
            // 예외 처리 로직
            e.printStackTrace(); // 예외 정보 출력
            // 예외 처리 후 반환할 값이나 로직을 작성
            return 0L; // 예시로 간단히 null 반환
        }
    }//게시글 삭제


    public BoardRes selBoardList(BoardPageDto dto) {
        int num = dto.getPage() - 1;
        dto.setStartIdx(num * dto.getRow());
        try {
            List<BoardListVo> list = mapper.selBoardList(dto);

            Long maxboard = mapper.maxBoard();
            int mp = (int) ceil((double) maxboard / dto.getRow());

            int isMore = mp > dto.getPage() ? 1 : 0;
            return BoardRes.builder().isMore(isMore)
                    .row(dto.getRow()).maxPage(mp).list(list).build();//페이징}
        } catch (Exception e) {
            // 예외 처리 로직
            e.printStackTrace(); // 예외 정보 출력
            // 예외 처리 후 반환할 값이나 로직을 작성
            return null; // 예시로 간단히 null 반환
        }
    }

    public BoardRes categoryBoardList(BoardPageDto dto) {
        int num = dto.getPage() - 1;
        dto.setStartIdx(num * dto.getRow());
        try {
            List<BoardListVo> list = mapper.categoryBoardList(dto);
            Long maxboard = mapper.maxBoard();
            int mp = (int) ceil((double) maxboard / dto.getRow());

            int isMore = mp > dto.getPage() ? 1 : 0;
            return BoardRes.builder().isMore(isMore)
                    .row(dto.getRow()).maxPage(mp).list(list).build();
        } catch (Exception e) {
            // 예외 처리 로직
            e.printStackTrace(); // 예외 정보 출력
            // 예외 처리 후 반환할 값이나 로직을 작성
            return null; // 예시로 간단히 null 반환
        }
        //카테고리별 리스트
    }

    public BoardSelRes selBoard(BoardSelPageDto dto) {

        int num = dto.getPage() - 1;
        dto.setStartIdx(num * dto.getRow());
        try {
            List<BoardSelVo> list = mapper.selBoard(dto);

            double maxpage = mapper.maxSelBoard(dto);
            int mp = (int) ceil(maxpage / dto.getRow());

            int isMore = mp > dto.getPage() ? 1 : 0;
            int page = mp - dto.getPage();
            return BoardSelRes.builder().isMore(isMore).title(dto.getTitle()).row(dto.getRow()).maxPage(mp).midPage(page).nowPage(dto.getPage()).list(list).build();
        } catch (Exception e) {
            // 예외 처리 로직
            e.printStackTrace(); // 예외 정보 출력
            // 예외 처리 후 반환할 값이나 로직을 작성
            return null; // 예시로 간단히 null 반환
        }
    }

    public BoardCmtDeVo deBoard(BoardDeDto dto) {
        int row = 15;
        int page = 1;
        try {
            mapper.viewBoard(dto);
            BoardDeVo boardDeVo = mapper.deBoard(dto);
            CommentPageDto dto1 = new CommentPageDto();
            dto1.setIboard(dto.getIboard());
            dto1.setPage(page);
            dto1.setRow(row);
            dto.setIboard(dto.getIboard());
            List<BoardPicVo> picList = mapper.picBoard(dto);
            if (picList == null) {
                picList = new ArrayList<>();
            }

            CommentRes commentRes = commentService.selComment(dto1);
            BoardCmtDeVo result = BoardCmtDeVo.builder().boardDeVo(boardDeVo).picList(picList).commentList(commentRes)
                    .build();
            return result;
        } catch (Exception e) {
            // 예외 처리 로직
            e.printStackTrace(); // 예외 정보 출력
            // 예외 처리 후 반환할 값이나 로직을 작성
            return null; // 예시로 간단히 null 반환
        }

    }

    //    public Long delOnePic(BoardPicDelDto dto) {
//        try {
//            long start = System.currentTimeMillis();
//            BoardEntity entity = new BoardEntity();
//            entity.setIboard(dto.getIboard());
//            entity.setIboardpic(dto.getIboardpic());
//            entity.setIuser(FACADE.getLoginUserPk());
//            String centerPath = String.format("boardPics/%d", dto.getIboard());
//            String targetPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), centerPath);
//            File file = new File(targetPath);
//            if (file.exists()) {
//                File[] deleteFolderList = file.listFiles();
//                String result = mapper.selPicName(dto.getIboardpic());
//                for (File deleteFile : deleteFolderList) {
//                    if (deleteFile.getName().equals(result)) {
//                        deleteFile.delete();
//                    }
//                }
//                File[] re = file.listFiles();
//                if (re != null && re.length == 0) {
//                    FileUtils.delFolder(fileDir + centerPath);
//                }
//            }
//            long end = System.currentTimeMillis();
//            log.info("groupConcat : {}", (end - start));
//            return mapper.delOnePic(entity);
//
//        } catch (Exception e) {
//            return 0L;
//        }
//    }
    public Long delOnePic(BoardPicDelDto dto) {
        try {
            BoardEntity entity = new BoardEntity();
            entity.setIboard(dto.getIboard());
            entity.setIboardpic(dto.getIboardpic());
            entity.setIuser(FACADE.getLoginUserPk());
            String centerPath = String.format("boardPics/%d", dto.getIboard());
            String targetPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), centerPath);
            File file = new File(targetPath);

            if (file.exists()) {
                File[] deleteFolderList = file.listFiles();
                String result = mapper.selPicName(dto.getIboardpic());
                String picName = result.substring(result.lastIndexOf('/') + 1);
                for (File deleteFile : deleteFolderList) {
                    if (deleteFile.getName().equals(picName)) {
                        deleteFile.delete();
                    }
                }

                File[] re = file.listFiles();
                if (re != null && re.length == 0) {
                    FileUtils.delFolder(fileDir + centerPath);
                }
            }
            return mapper.delOnePic(entity);
        } catch (Exception e) {
            return 0L;
        }
    }

    public Long insCategory(String name) {
        return mapper.insCategory(name);
    }
}