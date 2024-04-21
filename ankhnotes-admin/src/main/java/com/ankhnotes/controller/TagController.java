package com.ankhnotes.controller;


import com.ankhnotes.annotation.mySystemLog;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.domain.Tag;
import com.ankhnotes.dto.TagListDto;
import com.ankhnotes.dto.UpdateTagDto;
import com.ankhnotes.enums.AppHttpCodeEnum;
import com.ankhnotes.exception.SystemException;
import com.ankhnotes.service.TagService;
import com.ankhnotes.utils.SystemUtils;
import com.ankhnotes.vo.TagVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @mySystemLog(logDescription = "分页查询标签")
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    @mySystemLog(logDescription = "添加标签")
    @PostMapping
    public ResponseResult addTag(@RequestBody Tag tag){
        //新增标签, 标签名字不能为空
        if(!StringUtils.hasText(tag.getName()))
            throw new SystemException(AppHttpCodeEnum.TAGNAME_NOT_NULL);
        tagService.save(tag);
        return ResponseResult.okResult();
    }

    @mySystemLog(logDescription = "删除标签")
    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable("id") String stringIds){
        List<Long> tagIds = SystemUtils.stringTransferToListLong(stringIds);
        return tagService.deleteTagByIds(tagIds);
    }

    @mySystemLog(logDescription = "获取标签信息")
    @GetMapping("/{id}")
    public ResponseResult getTagInfo(@PathVariable("id") Long tagId){
        return tagService.getTagInfo(tagId);
    }

    @mySystemLog(logDescription = "更新标签信息")
    @PutMapping
    public ResponseResult updateTag(@RequestBody UpdateTagDto updateTagDto){
        return tagService.updateTag(updateTagDto);
    }

    @mySystemLog(logDescription = "写博客页面查询标签信息")
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }
}
