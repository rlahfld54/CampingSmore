package com.green.campingsmore.community.board;

import com.green.campingsmore.community.board.model.*;
import com.green.campingsmore.community.comment.model.CommentPageDto;
import com.green.campingsmore.community.comment.model.CommentRes;
import com.green.campingsmore.community.comment.model.CommentVo;
import lombok.RequiredArgsConstructor;
import com.green.campingsmore.community.board.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.ceil;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper mapper;
    private final int ROW = 15;
    private final int Page = 1;

    @Value("/home/download/")
    private String fileDir;

    @Transactional(rollbackFor = Exception.class)
    public Long postBoard(BoardInsDto dto, List<MultipartFile> pics) throws Exception {
        BoardEntity entity = new BoardEntity();
        entity.setIuser(dto.getIuser());
        entity.setIcategory(dto.getIcategory());
        entity.setTitle(dto.getTitle());
        entity.setCtnt(dto.getCtnt());
        Long result = mapper.insBoard(entity);
        if (pics != null) {
            String centerPath = String.format("boardPics/%d", entity.getIboard());
            String targetPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), centerPath);
            File file = new File(targetPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            List<BoardPicEntity> list = new ArrayList<>();

            for (int i = 0; i < pics.size(); i++) {
                String originFile = pics.get(i).getOriginalFilename();
                String saveName = FileUtils.makeRandomFileNm(originFile);
                File fileTarget = new File(targetPath + "/" + saveName);
                try {
                    pics.get(i).transferTo(fileTarget);
                } catch (IOException e) {
                    throw new Exception("파일저장을 실패했습니다");
                }
                BoardPicEntity picEntity = new BoardPicEntity();
                picEntity.setIboard(entity.getIboard());
                picEntity.setPic(saveName);
                list.add(picEntity);
                mapper.insBoardPic(list);
            }
//            if (!list.isEmpty()) {
//                mapper.insBoardPic(list);
//            }
            log.info("list:{}",list);
        }

        return result;// 게시판 등록
    }
    public List<BoardMyVo> selMyBoard(BoardMyDto dto){
        return mapper.selMyBoard(dto);

    }
    public Long delBoard(BoardDelDto dto){
        return mapper.delBoard(dto);
    }//게시글 삭제
    public BoardRes selBoardList(BoardPageDto dto){
        int num = dto.getPage() -1;
        dto.setStartIdx(num*dto.getRow());
        List<BoardListVo> list = mapper.selBoardList(dto);
        Long maxboard = mapper.maxBoard();
        int mp = (int) ceil((double) maxboard / dto.getRow());

        int isMore = mp > dto.getPage() ? 1:0;
        return BoardRes.builder().isMore(isMore)
                .row(dto.getRow()).maxPage(mp).list(list).build();//페이징
    }
    public BoardRes categoryBoardList(BoardPageDto dto){
        int num = dto.getPage() -1;
        dto.setStartIdx(num*dto.getRow());
        List<BoardListVo> list = mapper.categoryBoardList(dto);
        Long maxboard = mapper.maxBoard();
        int mp = (int) ceil((double) maxboard / dto.getRow());

        int isMore = mp > dto.getPage() ? 1:0;
        return BoardRes.builder().isMore(isMore)
                .row(dto.getRow()).maxPage(mp).list(list).build();
        //카테고리별 리스트
    }
    public BoardSelRes selBoard(BoardSelPageDto dto){

        String title = dto.getTitle();
        int num = dto.getPage()-1;
        dto.setStartIdx(num*dto.getRow());
        List<BoardSelVo> list = mapper.selBoard(dto);
        double maxpage = mapper.maxSelBoard(dto);
        int mp = (int) ceil(maxpage/dto.getRow());

        int isMore = mp>dto.getPage() ? 1:0;
        int page = mp - dto.getPage();
        return BoardSelRes.builder().isMore(isMore).title(dto.getTitle()).row(dto.getRow()).maxPage(mp).midPage(page).nowPage(dto.getPage()).list(list).build();
    }
}