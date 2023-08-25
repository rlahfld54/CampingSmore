package com.green.campingsmore.item;

import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.dataset.NaverApi;
import com.green.campingsmore.item.model.*;
import com.green.campingsmore.review.ReviewService;
import com.green.campingsmore.review.model.ReviewPageDto;
import com.green.campingsmore.review.model.ReviewRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemMapper MAPPER;
    private final NaverApi naverApi;
    private final ReviewService REVIEWSERVICE;
    private final AuthenticationFacade FACADE;

    // 카테고리
    public List<ItemSelCateVo> selCategory(){

        return MAPPER.selCategory();
    }

    //아이템 추가
    public Long insItem(ItemInsDto dto) {

        log.info(" List<String> picUrl: {}", dto.getPicUrl());
        ItemEntity entity = new ItemEntity();
        entity.setIitemCategory(dto.getIitemCategory());
        entity.setName(dto.getName());
        entity.setPic(dto.getPic());
        entity.setPrice(dto.getPrice());
        try {
            Long result = MAPPER.insItem(entity);

            if( result == 1L) {
                try {
                    for (int i = 0; i < dto.getPicUrl().size(); i++) {
                        ItemInsDetailDto picDto = new ItemInsDetailDto();
                        picDto.setIitem(entity.getIitem());

                        picDto.setPic(dto.getPicUrl().get(i).toString());
                        MAPPER.insDetailPic(picDto);
                    }
                } catch (Exception e){
                    log.info("아이템 사진 추가 실패");
                    return 0L;
                }
                return entity.getIitem();
            }

        } catch (Exception e1){
            log.info("아이템 추가 실패");
            return 0L;
        }
        return 0L;
    }

    public ItemSelDetailRes searchItem(ItemSearchDto dto) {
        // 아이템 리스트 뿌려주기 전에 로그인 상태 체크



        if(!FACADE.isLogin()) {
            dto.setIuser(0L);
        } else {
            dto.setIuser(FACADE.getLoginUserPk());
            log.info("iuser: {}",dto.getIuser());
        }
        dto.setStartIdx((dto.getPage()-1) * dto.getRow());
        List<ItemVo> list = MAPPER.searchItem(dto);
        int count = MAPPER.selLastItem(dto);
        int maxPage = (int)Math.ceil((double) count /dto.getRow());
        int isMore = maxPage > dto.getPage() ? 1 : 0;

    return ItemSelDetailRes.builder()
            .iitemCategory(dto.getIitemCategory())
            .text(dto.getText())
            .sort(dto.getSort())
            .maxPage(maxPage)
            .startIdx(dto.getStartIdx())
            .isMore(isMore)
            .page(dto.getPage())
            .row(dto.getRow())
            .itemList(list)
            .build();
}

    //아이템 삭제
    public int delItem(Long iitem) {
        return MAPPER.delItem(iitem);
    }


    // 아이템 상세
    public ItemDetailReviewVo selDetail(ItemSelDetailDto dto) {
        ItemSelDetailVo vo = MAPPER.selDetail(dto.getIitem());
        vo.setPicList(MAPPER.selDetailPic(dto.getIitem()));

        ReviewPageDto reviewDto = new ReviewPageDto();
        reviewDto.setIitem(dto.getIitem());
        reviewDto.setPage(dto.getPage());
        reviewDto.setRow(dto.getRow());
        reviewDto.setStartIdx((dto.getPage() - 1) * dto.getRow());
        ReviewRes reviewList = REVIEWSERVICE.selReview(reviewDto);

    return ItemDetailReviewVo.builder()
            .item(vo)
            .review(reviewList)
            .build();
    }

    //상세이미지 추가
    public int insDetailPic(ItemInsDetailPicDto dto) {
        // 아이템 PK에 사진이 있으면 삭제
        // 아이템 PK로 아이템 추가
        MAPPER.delDetailPic(dto.getIitem());


        for (int i = 0; i < dto.getPicUrl().size(); i++) {
            ItemInsDetailDto dto2 = new ItemInsDetailDto();
            dto2.setIitem(dto.getIitem());
            dto2.setPic(dto.getPicUrl().get(i));
            MAPPER.insDetailPic(dto2);
        }

        return dto.getPicUrl().size();
    }

    public int updItem(ItemUpdDto dto) {
        ItemEntity entity = new ItemEntity();
        entity.setIitem(dto.getIitem());
        entity.setIitemCategory(dto.getIitemCategory());
        entity.setName(dto.getName());
        entity.setPic(dto.getPic());
        entity.setPrice(dto.getPrice());

        try {
            MAPPER.updItem(entity);

            if( dto.getPicUrl() != null) {
                try {
                        MAPPER.delDetailPic(entity.getIitem());
                    for (int i = 0; i < dto.getPicUrl().size(); i++) {
                        ItemInsDetailDto picDto = new ItemInsDetailDto();
                        picDto.setIitem(entity.getIitem());

                        picDto.setPic(dto.getPicUrl().get(i).toString());
                        MAPPER.insDetailPic(picDto);
                    }
                } catch (Exception e){
                    log.info("아이템 사진 추가 실패");
                    return 0;
                }
                return 1;
            }

        } catch (Exception e1){
            log.info("아이템 추가 실패");
            return 0;
        }
        return 0;
    }



    public int delDetailPic(Long iitem) {
        return MAPPER.delDetailPic(iitem);
    }


/*    public ItemSelDetailVo selDetail(Long iitem) {
        return MAPPER.selDetail(iitem);
    }*/

    public int insBestItem(ItemInsBestDto dto) {
        return MAPPER.insBestItem(dto);
    }

    public List<ItemVo> selBestItem() {
    return MAPPER.selBestItem();
    }

}
