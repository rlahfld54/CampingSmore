package com.green.campingsmore.community.board;

import com.green.campingsmore.community.board.model.BoardEntity;
import com.green.campingsmore.community.board.model.BoardInsDto;
import com.green.campingsmore.community.board.model.BoardPicEntity;
import lombok.RequiredArgsConstructor;
import com.green.campingsmore.community.board.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper mapper;

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
            }
            if (!list.isEmpty()) {
                mapper.insBoardPic(list);
            }
        }

        return result;
    }
}