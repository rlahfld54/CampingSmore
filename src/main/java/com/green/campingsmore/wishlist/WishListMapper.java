package com.green.campingsmore.wishlist;

import com.green.campingsmore.review.model.ReviewEntity;
import com.green.campingsmore.wishlist.model.WishDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WishListMapper {
    int insertWishlist(WishDto dto);
    List<WishDto> getWishlist(int iuser);
    List<ReviewEntity> getReviewlist(int iuser);
//    List<ReviewEntity> getOrderlist(int iuser);
}
