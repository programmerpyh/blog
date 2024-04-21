package com.ankhnotes.service;

import com.ankhnotes.domain.Menu;
import com.ankhnotes.domain.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2024-03-19 18:46:26
 */
public interface MenuService extends IService<Menu> {
    List<String> selectPermsByUserId(Long userId);

    //后台查询所有菜单, 模糊匹配menuName
    ResponseResult selectList(String status, String menuName);

    //后台获取单个菜单的详细信息
    ResponseResult getMenuInfo(Long menuId);

    //后台修改菜单
    ResponseResult updateMenu(Menu menu);

    //后台删除菜单(若有子菜单则不允许删除)
    ResponseResult deleteMenu(List<Long> menuIds);

    //后台获取菜单树供新建角色时匹配权限
    ResponseResult selectMenuTree();

    //后台根据角色id查询对应角色菜单列表树, 用于更新角色信息
    ResponseResult getRoleMenuTree(Long roleId);
}
