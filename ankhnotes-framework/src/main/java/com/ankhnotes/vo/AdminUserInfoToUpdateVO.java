package com.ankhnotes.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserInfoToUpdateVO {

    private List<Long> roleIds;

    private List<RoleListForUserVO> roles;

    private UserInfoToUpdateVO user;

}
