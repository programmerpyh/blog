package com.ankhnotes.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//用于返回登陆成功后的用户信息给前端
public class BlogUserLoginVO {

    private String token;

    UserInfoVO userInfo;

}
