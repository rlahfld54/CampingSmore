package com.green.campingsmore.wishlist;

import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.review.model.ReviewEntity;
import com.green.campingsmore.wishlist.model.WishDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "마이페이지")
@RequestMapping("/api/mypage")
public class WishListController {
    private final WishListService SERVICE;
    private final AuthenticationFacade FACADE;

    @PostMapping("/wishlist")
    @Operation(summary = "유저별로 찜하기 등록",
            description = "Try it out -> Execute 눌러주세요 \n\n "+
                    "iitem : 아이템 PK \n\n "
    )
    public int insertWishlist(@RequestParam int iitem){
        WishDto dto = new WishDto();
        dto.setIuser(Math.toIntExact(FACADE.getLoginUserPk()));
        dto.setIitem(iitem);
        return SERVICE.insertWishlist(dto);
    }

    @GetMapping("/wishlist")
    @Operation(summary = "유저별로 찜하기 목록 불러오기",
            description = "Try it out -> Execute 눌러주세요 \n\n "
    )
    public List<WishDto> getWishlist(){
        return SERVICE.getWishlist(Math.toIntExact(FACADE.getLoginUserPk()));
    }

    @GetMapping("/reviewlist")
    @Operation(summary = "유저별로 리뷰 목록 불러오기",
            description = "Try it out -> Execute 눌러주세요 \n\n "
    )
    public List<ReviewEntity> getReviewlist(){
        return SERVICE.getReviewlist(Math.toIntExact(FACADE.getLoginUserPk()));
    }


}
