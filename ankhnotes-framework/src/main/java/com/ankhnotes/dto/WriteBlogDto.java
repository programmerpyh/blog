package com.ankhnotes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WriteBlogDto {

    private String title;

    private String thumbnail;

    private String isTop;

    private String isComment;

    private String content;

    private List<Long> tags;

    private Long categoryId;

    private String summary;

    private String status;

}
