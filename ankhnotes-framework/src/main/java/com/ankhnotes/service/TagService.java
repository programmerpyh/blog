package com.ankhnotes.service;

import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.domain.Tag;
import com.ankhnotes.dto.TagListDto;
import com.ankhnotes.dto.UpdateTagDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2024-03-14 17:08:50
 */
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult deleteTagByIds(List<Long> ids);

    ResponseResult getTagInfo(Long id);

    ResponseResult updateTag(UpdateTagDto updateTagDto);

    ResponseResult listAllTag();

}
