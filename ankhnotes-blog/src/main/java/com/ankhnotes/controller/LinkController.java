package com.ankhnotes.controller;



import com.ankhnotes.annotation.mySystemLog;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 友链(Link)表控制层
 *
 * @author makejava
 * @since 2024-03-06 15:45:56
 */
@RestController
@RequestMapping("link")
public class LinkController{

    @Autowired
    LinkService linkService;

    @mySystemLog(logDescription = "获取所有友链")
    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }

}

