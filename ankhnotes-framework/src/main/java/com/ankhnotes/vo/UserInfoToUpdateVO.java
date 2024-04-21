package com.ankhnotes.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoToUpdateVO {

    private String email;

    private Long id;

    private String nickName;

    private String sex;

    private String status;

    private String userName;

    private String phonenumber;

}
