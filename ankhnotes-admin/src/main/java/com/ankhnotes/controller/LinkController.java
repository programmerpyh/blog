package com.ankhnotes.controller;

import com.ankhnotes.annotation.mySystemLog;
import com.ankhnotes.domain.Link;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.service.LinkService;
import com.ankhnotes.utils.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    LinkService linkService;

    @mySystemLog(logDescription = "后台分页查询友链列表")
    @GetMapping("/list")
    public ResponseResult getLinkPage(Integer pageNum, Integer pageSize, String name, String status){
        return linkService.getLinkPage(pageNum, pageSize, name, status);
    }

    @mySystemLog(logDescription = "后台新增友链")
    @PostMapping
    public ResponseResult addNewLink(@RequestBody Link link){
        //友链名称和地址不得为空
        if(!StringUtils.hasText(link.getName()))
            throw new RuntimeException("友链名称不得为空");
        if(!StringUtils.hasText(link.getAddress()))
            throw new RuntimeException("友链地址不得为空");

        boolean isSuccessful = linkService.save(link);
        if(!isSuccessful)
            throw new RuntimeException("友链添加失败");
        return ResponseResult.okResult();
    }

    @mySystemLog(logDescription = "后台根据id查询友链详细信息")
    @GetMapping("/{id}")
    public ResponseResult getLinkInfo(@PathVariable("id") Long id){
        return linkService.getLinkInfo(id);
    }

    @mySystemLog(logDescription = "后台修改友链状态")
    @PutMapping("/changeLinkStatus")
    public ResponseResult changeLinkStatus(@RequestBody Link link){
        boolean isSuccessful = linkService.updateById(link);
        if(!isSuccessful)
            throw new RuntimeException("友链状态改变失败");
        return ResponseResult.okResult();
    }

    @mySystemLog(logDescription = "后台修改友链")
    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link){
        //友链名称和地址不得为空
        if(!StringUtils.hasText(link.getName()))
            throw new RuntimeException("友链名称不得为空");
        if(!StringUtils.hasText(link.getAddress()))
            throw new RuntimeException("友链地址不得为空");

        boolean isSuccessful = linkService.updateById(link);
        if(!isSuccessful)
            throw new RuntimeException("友链修改失败");
        return ResponseResult.okResult();
    }

    @mySystemLog(logDescription = "后台逻辑删除友链")
    @DeleteMapping("/{id}")
    public ResponseResult deleteLinks(@PathVariable("id") String stringIds){
        List<Long> linkIds = SystemUtils.stringTransferToListLong(stringIds);

        boolean isSuccessful = linkService.removeByIds(linkIds);
        if(!isSuccessful)
            throw new RuntimeException("友链删除失败");

        return ResponseResult.okResult();
    }

}
