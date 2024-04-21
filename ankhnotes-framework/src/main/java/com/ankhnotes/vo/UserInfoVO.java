package com.ankhnotes.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {

    //头像
    private String avatar;

    private String email;

    private Long id;

    //昵称
    private String nickName;

    //用户性别（0男，1女，2未知）
    private String sex;

}
