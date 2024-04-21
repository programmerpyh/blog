package com.ankhnotes.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) //开启链式编程   user.setName("aaa").setAge(18)
public class AdminUserInfoVO {

    private List<String> permissions;

    private List<String> roles;

    private UserInfoVO user;

}
