package com.ankhnotes.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "/content/tag/list接口的请求参数接收体")//swagger
public class TagListDto implements Serializable {

    //请求参数。用户可传可不传，这两个参数是给用户在搜索框根据name查询对应的标签，或根据remark查询对应的标签

    private String name;

    private String remark;

}
