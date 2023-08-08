package com.green.campingsmore.review;

import com.green.campingsmore.community.board.utils.FileUtils;
import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.review.model.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewMapper MAPPER;
    private final AuthenticationFacade FACADE;
    @Value("${file.dir}")
    private String fileDir;


    public String insReview(ReviewInsDto dto, MultipartFile pic) {
        ReviewEntity entity = new ReviewEntity();
        entity.setIuser(FACADE.getLoginUserPk());
        entity.setIorder(dto.getIorder());
        entity.setIitem(dto.getIitem());
        entity.setReviewCtnt(dto.getReviewCtnt());
        entity.setStarRating(dto.getStarRating());


        MAPPER.insReview(entity);
        if (pic != null) {

            String temp = "0";
            String centerPath = String.format("user/%d/review/%d", entity.getIuser(),entity.getIreview());
            String dicPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), centerPath);

            File dic = new File(dicPath);
            if (!dic.exists()) {
                dic.mkdirs();
            }

            String originFileName = pic.getOriginalFilename();
            String savedFileName = FileUtils.makeRandomFileNm(originFileName);
            String savedFilePath = String.format("%s/%s", centerPath, savedFileName);
            String targetPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), savedFilePath);
            File target = new File(targetPath);
            try {
                pic.transferTo(target);
            }catch (Exception e) {
                e.printStackTrace();
            }

            entity.setPic(savedFilePath);
            try {
                int result = MAPPER.updReviewPic(entity);
                if(result == Integer.parseInt(temp)) {
                    throw new Exception("사진을 등록할 수 없습니다.");
                }
            }catch (Exception e) {
                target.delete();
                return  "업로드 취소";
            }
            return "이미지 추가 업로드 완료";
        }

        return "업로드 완료";
    }



    public ReviewRes selReview(ReviewPageDto dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());
        int count = MAPPER.selLastReview(dto.getIitem());
        int maxPage = (int)Math.ceil((double) count /dto.getRow());
        int isMore = maxPage > dto.getPage() ? 1 : 0;


        List<ReviewSelVo> list = MAPPER.selReview(dto);
        return ReviewRes.builder()
                .iitem(dto.getIitem())
                .maxPage(maxPage)
                .startIdx(dto.getStartIdx())
                .isMore(isMore)
                .page(dto.getPage())
                .row(dto.getRow())
                .list(list)
                .build();
    }

    public String updReview(ReviewUpdDto dto, MultipartFile pic) {
        log.info("ReviewUpdDto dto : {}",dto);

        ReviewEntity entity = new ReviewEntity();
        entity.setIuser(FACADE.getLoginUserPk());
        entity.setIreview(dto.getIreview());
        entity.setReviewCtnt(dto.getReviewCtnt());
        entity.setStarRating(dto.getStarRating());


        int result = MAPPER.updReview(entity);
        if (result == 0) {
            System.out.println("유저확인필요");
            return null;
        }

        if (pic != null) {
            String centerPath = String.format("user/%d/review/%d", entity.getIuser(),entity.getIreview());
            String dicPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), centerPath);

            File dic = new File(dicPath);
            if (dic.exists()){
                File[] deleteFolderList = dic.listFiles();
                for (int i = 0; i < deleteFolderList.length; i++  ) {
                    deleteFolderList[i].delete();
                }
                dic.mkdirs();
            } else if (!dic.exists()) {
                dic.mkdirs();
            }

            String originFileName = pic.getOriginalFilename();
            String savedFileName = FileUtils.makeRandomFileNm(originFileName);
            String savedFilePath = String.format("%s/%s", centerPath, savedFileName);
            String targetPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), savedFilePath);
            File target = new File(targetPath);
            try {
                pic.transferTo(target);
            }catch (Exception e) {
                return "사진 업로드 실패";
            }

            entity.setPic(savedFilePath);
            try {
                int picResult = MAPPER.updReviewPic(entity);
                if(picResult == 0) {
                    throw new Exception("사진을 등록할 수 없습니다.");
                }
            }catch (Exception e) {
                target.delete();
                return  "업로드 취소";
            }
            return savedFilePath;
        }

        return "1";
    }

/*
    public String updReviewPic(MultipartFile pic, ReviewPicDto dto) {
        ReviewEntity entity = new ReviewEntity();
        entity.setIuser(dto.getIuser());
        entity.setIreview(dto.getIreview());

        String temp = "0";
        String centerPath = String.format("user/%d/review/%d", entity.getIuser(),entity.getIreview());
        String dicPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), centerPath);

        File dic = new File(dicPath);
        if (!dic.exists()) {
            dic.mkdirs();
        }

        String orignFileName = pic.getOriginalFilename();
        String savedFileName = FileUtils.makeRandomFileNm(orignFileName);
        String savedFilePath = String.format("%s/%s", centerPath, savedFileName);
        String targetPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), savedFilePath);
        File target = new File(targetPath);
        try {
            pic.transferTo(target);
        }catch (Exception e) {
            return "저장경로 에러";
        }

        entity.setPic(savedFilePath);
        try {
            int result = MAPPER.updReviewPic(entity);
            if(result == Integer.parseInt(temp)) {
                throw new Exception("사진을 등록할 수 없습니다.");
            }
        }catch (Exception e) {
            target.delete();
            return  temp;
        }
        return savedFilePath;
    }
*/




}
