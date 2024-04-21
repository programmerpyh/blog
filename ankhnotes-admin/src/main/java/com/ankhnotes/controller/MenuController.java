package com.ankhnotes.controller;

import com.ankhnotes.annotation.mySystemLog;
import com.ankhnotes.domain.Menu;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.service.MenuService;
import com.ankhnotes.utils.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @mySystemLog(logDescription = "后台查询菜单表(不需要分页)")
    @GetMapping("/list")
    public ResponseResult selectList(String status, String menuName){
        return menuService.selectList(status, menuName);
    }

    @mySystemLog(logDescription = "后台新增菜单")
    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu){
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    @mySystemLog(logDescription = "后台获取单个菜单详细信息")
    @GetMapping("/{menuId}")
    public ResponseResult getMenuInfo(@PathVariable("menuId") Long menuId){
        return menuService.getMenuInfo(menuId);
    }

    @mySystemLog(logDescription = "后台修改菜单")
    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }

    @mySystemLog(logDescription = "后台删除菜单")
    @DeleteMapping("/{menuId}")
    public ResponseResult deleteMenu(@PathVariable("menuId") String stringIds){
        List<Long> menuIds = SystemUtils.stringTransferToListLong(stringIds);
        return menuService.deleteMenu(menuIds);
    }

    @mySystemLog(logDescription = "后台获取菜单树供新建角色时匹配权限")
    @GetMapping("/treeselect")
    public ResponseResult selectMenuTree(){
        return menuService.selectMenuTree();
    }

    @mySystemLog(logDescription = "后台根据角色id查询对应角色菜单列表树, 用于更新角色信息")
    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult getRoleMenuTree(@PathVariable("id") Long roleId){
        return menuService.getRoleMenuTree(roleId);
    }

}
