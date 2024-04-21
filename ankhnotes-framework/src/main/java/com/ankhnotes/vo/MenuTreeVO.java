package com.ankhnotes.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeVO {

    private Long id;

    private String label;

    private Long parentId;

    private List<MenuTreeVO> children;

    public MenuTreeVO(Long id, String label, Long parentId) {
        this.id = id;
        this.label = label;
        this.parentId = parentId;
    }
}
