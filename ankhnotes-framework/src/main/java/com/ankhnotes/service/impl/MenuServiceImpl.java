package com.ankhnotes.service.impl;

import com.ankhnotes.constants.SystemConstants;
import com.ankhnotes.domain.Menu;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.enums.AppHttpCodeEnum;
import com.ankhnotes.exception.SystemException;
import com.ankhnotes.mapper.MenuMapper;
import com.ankhnotes.mapper.RoleMenuMapper;
import com.ankhnotes.service.MenuService;
import com.ankhnotes.utils.BeanCopyUtils;
import com.ankhnotes.vo.MenuInfoVO;
import com.ankhnotes.vo.MenuListVO;
import com.ankhnotes.vo.MenuTreeVO;
import com.ankhnotes.vo.RoleMenuTreeVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2024-03-19 18:54:49
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    RoleMenuMapper roleMenuMapper;

    @Override
    public List<String> selectPermsByUserId(Long userId) {
        //如果是超级管理员则直接返回所有有效权限
        if(SystemConstants.Administrator_ID.equals(userId)){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            //菜单类型为"菜单"和"按钮"
            queryWrapper.in(Menu::getMenuType, SystemConstants.TYPE_MENU, SystemConstants.TYPE_BUTTON);
            queryWrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> menuList = list(queryWrapper);
            List<String> perms = new ArrayList<>();
            menuList.forEach(menu -> perms.add(menu.getPerms()));
            return perms;
        }

        //非超级管理员需要连表查询权限
        return getBaseMapper().selectPermsByOtherUserId(userId);
    }

    @Override
    public ResponseResult selectList(String status, String menuName) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(status), Menu::getStatus, status);
        wrapper.like(StringUtils.hasText(menuName), Menu::getMenuName, menuName);
        wrapper.orderByAsc(Menu::getParentId, Menu::getOrderNum);
        List<Menu> menuList = list(wrapper);
        List<MenuListVO> menuListVOS = BeanCopyUtils.copyBeanList(menuList, MenuListVO.class);
        return ResponseResult.okResult(menuListVOS);
    }

    @Override
    public ResponseResult getMenuInfo(Long menuId) {
        Menu menu = getById(menuId);
        MenuInfoVO vo = BeanCopyUtils.copyBean(menu, MenuInfoVO.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        //如果此次更新将原菜单的parentId设置为了原菜单自己, 则抛出异常
        if(menu.getId().equals(menu.getParentId()))
            throw new RuntimeException("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(List<Long> menuIds) {
        menuIds.forEach(menuId -> {
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Menu::getParentId, menuId);
            List<Menu> childs = list(wrapper);
            if(!childs.isEmpty())
                throw new RuntimeException("存在子菜单未删除, 需先删除子菜单");
            removeById(menuId);
        });
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectMenuTree() {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Menu::getParentId, Menu::getOrderNum);
        List<Menu> menuList = list(wrapper);
        List<MenuTreeVO> vos = new ArrayList<>();
        menuList.forEach(menu -> vos.add(new MenuTreeVO(menu.getId(), menu.getMenuName(), menu.getParentId())));
        List<MenuTreeVO> menuTreeRoots = createMenuTree(vos, SystemConstants.MENU_ROOT);
        return ResponseResult.okResult(menuTreeRoots);
    }

    //递归构造
    private List<MenuTreeVO> createMenuTree(List<MenuTreeVO> menuList, Long parentId){
        List<MenuTreeVO> menuTreeRoots = new ArrayList<>();
        for(MenuTreeVO vo : menuList){
            if(parentId.equals(vo.getParentId())){
                vo.setChildren(createMenuTree(menuList, vo.getId()));
                menuTreeRoots.add(vo);
            }
        }
        return menuTreeRoots.isEmpty()?null:menuTreeRoots;
    }

    @Override
    public ResponseResult getRoleMenuTree(Long roleId) {
        RoleMenuTreeVO roleMenuTreeVO = new RoleMenuTreeVO();
        List<MenuTreeVO> menuTree = (List<MenuTreeVO>)selectMenuTree().getData();
        List<Long> checkedKeys = roleMenuMapper.selectMenuIdsByRoleId(roleId);
        return ResponseResult.okResult(new RoleMenuTreeVO(menuTree, checkedKeys));
    }
}
