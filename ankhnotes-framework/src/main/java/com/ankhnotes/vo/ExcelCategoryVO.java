package com.ankhnotes.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * category表映射到excel中
 */
public class ExcelCategoryVO {

    @ExcelProperty("分类名")
    private String name;

    @ExcelProperty("描述")
    private String description;

    //状态0:正常,1禁用
    @ExcelProperty("状态0:正常,1禁用")
    private String status;

}
