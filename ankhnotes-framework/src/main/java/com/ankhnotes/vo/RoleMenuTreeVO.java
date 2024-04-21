package com.ankhnotes.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuTreeVO {

    private List<MenuTreeVO> menus;

    private List<Long> checkedKeys;

}
