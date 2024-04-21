package com.ankhnotes.service.impl;

import com.ankhnotes.constants.SystemConstants;
import com.ankhnotes.domain.Link;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.mapper.LinkMapper;
import com.ankhnotes.service.LinkService;
import com.ankhnotes.utils.BeanCopyUtils;
import com.ankhnotes.vo.AdminLinkVO;
import com.ankhnotes.vo.LinkVO;
import com.ankhnotes.vo.PageVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2024-03-06 15:45:37
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> linkWrapper = new LambdaQueryWrapper<>();
        linkWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);

        List<Link> linkList = list(linkWrapper);

        List<LinkVO> linkVOS = BeanCopyUtils.copyBeanList(linkList, LinkVO.class);

        return ResponseResult.okResult(linkVOS);
    }

    @Override
    public ResponseResult getLinkPage(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(status), Link::getStatus, status);
        wrapper.like(StringUtils.hasText(name), Link::getName, name);

        Page<Link> page = new Page<>(pageNum, pageSize);

        page(page, wrapper);

        List<AdminLinkVO> vos = BeanCopyUtils.copyBeanList(page.getRecords(), AdminLinkVO.class);

        return ResponseResult.okResult(new PageVO<>(vos, page.getTotal()));
    }

    @Override
    public ResponseResult getLinkInfo(Long id) {
        Link link = getById(id);

        AdminLinkVO vo = BeanCopyUtils.copyBean(link, AdminLinkVO.class);

        return ResponseResult.okResult(vo);
    }
}
