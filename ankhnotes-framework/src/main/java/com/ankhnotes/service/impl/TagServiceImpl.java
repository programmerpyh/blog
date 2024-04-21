package com.ankhnotes.service.impl;

import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.domain.Tag;
import com.ankhnotes.dto.TagListDto;
import com.ankhnotes.dto.UpdateTagDto;
import com.ankhnotes.mapper.TagMapper;
import com.ankhnotes.service.TagService;
import com.ankhnotes.utils.BeanCopyUtils;
import com.ankhnotes.vo.ListAllTagVO;
import com.ankhnotes.vo.PageVO;
import com.ankhnotes.vo.TagVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2024-03-14 17:08:50
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        //若以下两个条件存在则进行判断
        wrapper.like(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName());
        wrapper.like(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());
        //分页查询
        Page<Tag> tagPage = new Page<>(pageNum, pageSize);
        page(tagPage, wrapper);
        //包装响应
        List<TagVO> tagVOS = BeanCopyUtils.copyBeanList(tagPage.getRecords(), TagVO.class);
        return ResponseResult.okResult(new PageVO<>(tagVOS, tagPage.getTotal()));
    }

    @Override
    public ResponseResult deleteTagByIds(List<Long> ids) {
        ids.forEach( id -> {
            Tag tag = getById(id);
            if(tag!=null){
                //逻辑删除标签
                getBaseMapper().myUpdateById(id, 1);
            }else {
                throw new RuntimeException("欲删除标签不存在");
            }
        });
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagInfo(Long id) {
        Tag tag = getById(id);
        TagVO tagVO = BeanCopyUtils.copyBean(tag, TagVO.class);
        return ResponseResult.okResult(tagVO);
    }

    @Override
    public ResponseResult updateTag(UpdateTagDto updateTagDto) {
        Tag tag = BeanCopyUtils.copyBean(updateTagDto, Tag.class);
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        List<ListAllTagVO> vos
                = BeanCopyUtils.copyBeanList(list(), ListAllTagVO.class);
        return ResponseResult.okResult(vos);
    }
}
