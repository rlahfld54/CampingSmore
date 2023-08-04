package com.green.campingsmore.wishlist;

import com.green.campingsmore.review.model.ReviewEntity;
import com.green.campingsmore.wishlist.model.WishDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListMapper MAPPER;

    public int insertWishlist(WishDto dto){
        return MAPPER.insertWishlist(dto);
    }

    public List<WishDto> getWishlist(int iuser){
        return MAPPER.getWishlist(iuser);
    }

    public List<ReviewEntity> getReviewlist(int iuser){
        return MAPPER.getReviewlist(iuser);
    }

//    public List<ReviewEntity> getOrderlist(int iuser){
//        return MAPPER.getReviewlist(iuser);
//    }
}
