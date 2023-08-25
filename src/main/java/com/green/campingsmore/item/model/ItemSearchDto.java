package com.green.campingsmore.item.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemSearchDto {
    private Long iitemCategory;
    private String text;
    private int sort;
    private int startIdx;
    private int page;
    private int row;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long iuser;
}
