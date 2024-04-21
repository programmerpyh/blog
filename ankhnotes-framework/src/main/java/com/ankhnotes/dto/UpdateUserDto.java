package com.ankhnotes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateUserDto {

    private String email;

    private Long id;

    private String nickName;

    private String sex;

    private String status;

    private String userName;

    private String phonenumber;

    private List<Long> roleIds;

}
