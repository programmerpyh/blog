package com.ankhnotes.service;

import com.ankhnotes.domain.Link;
import com.ankhnotes.domain.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2024-03-06 15:45:37
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    //后台分页查询友链
    ResponseResult getLinkPage(Integer pageNum, Integer pageSize, String name, String status);

    //后台根据id查询友链详细信息
    ResponseResult getLinkInfo(Long id);
}
