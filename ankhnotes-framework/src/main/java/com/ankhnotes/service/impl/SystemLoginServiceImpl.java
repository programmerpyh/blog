package com.ankhnotes.service.impl;

import com.ankhnotes.constants.SystemConstants;
import com.ankhnotes.domain.LoginUser;
import com.ankhnotes.domain.Menu;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.domain.User;
import com.ankhnotes.enums.AppHttpCodeEnum;
import com.ankhnotes.exception.SystemException;
import com.ankhnotes.mapper.MenuMapper;
import com.ankhnotes.service.MenuService;
import com.ankhnotes.service.RoleService;
import com.ankhnotes.service.SystemLoginService;
import com.ankhnotes.service.UserService;
import com.ankhnotes.utils.BeanCopyUtils;
import com.ankhnotes.utils.JwtUtil;
import com.ankhnotes.utils.RedisCache;
import com.ankhnotes.utils.SecurityUtils;
import com.ankhnotes.vo.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SystemLoginServiceImpl implements SystemLoginService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    MenuService menuService;

    @Autowired
    MenuMapper menuMapper;

    @Override
    public ResponseResult login(User user) {
        //封装Controller传来的用户输入的用户名和密码为Authentication类型的对象
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        //将封装的Authentication对象传入.authenticate()函数进行认证, 即校对用户名密码
        //会自动调用自定义的UserDetailsServiceImpl进行数据库查询并校对用户名密码, 若成功则返回非空的authentication对象
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        //校验用户是否通过认证
        if(Objects.isNull(authentication))
            throw new SystemException(AppHttpCodeEnum.LOGIN_ERROR);

        //获取UserDetails数据
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        String userId = loginUser.getUser().getId().toString();

        //将loginUser添加到Redis, 用于Security框架在认证之后的异常和授权过程中, 获取已登录用户的信息
        redisCache.setCacheObject("login:" + userId, loginUser);

        ////响应过程////////////////////////////

        //生成userId的JWT的token
        String jwt = JwtUtil.createJWT(userId);

        Map<String, String> resultData = new HashMap<>();
        resultData.put("token", jwt);
        return ResponseResult.okResult(resultData);
    }

    @Override
    public ResponseResult<AdminUserInfoVO> getInfo(Long loginUserId) {
        //查询后台登陆用户信息
        User loginUser = userService.getById(loginUserId);
        UserInfoVO userInfoVO = BeanCopyUtils.copyBean(loginUser, UserInfoVO.class);
        //查询后台登陆用户的角色信息
        List<String> roles = roleService.selectRoleKeyByUserId(loginUserId);
        //查询后台登陆用户的权限信息
        List<String> permissions = menuService.selectPermsByUserId(loginUserId);

        AdminUserInfoVO adminUserInfoVO = new AdminUserInfoVO();
        adminUserInfoVO.setPermissions(permissions).setRoles(roles).setUser(userInfoVO);
        return ResponseResult.okResult(adminUserInfoVO);
    }

    @Override
    public ResponseResult getRouters(Long loginUserId) {

        List<Menu> menuList = null;

        //如果是超级管理员则返回所有满足条件的权限菜单
        if(SystemConstants.Administrator_ID.equals(loginUserId)){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.TYPE_MENU, SystemConstants.TYPE_CATEGORY);
            wrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            wrapper.orderByAsc(Menu::getParentId, Menu::getOrderNum);
            menuList = menuService.list(wrapper);
        }else{
            menuList = menuMapper.selectMenusByOtherUserId(loginUserId);
        }
        List<RouterVO> routerVOS = BeanCopyUtils.copyBeanList(menuList, RouterVO.class);
        //perms属性有些为null, 需改为""
        routerVOS.forEach(routerVO -> {
            if(routerVO.getPerms()==null)
                routerVO.setPerms("");
        });
        //创建父子关系
        routerVOS = checkChildRouterVOS(routerVOS, SystemConstants.MENU_ROOT);
        return ResponseResult.okResult(new RoutersVO(routerVOS));
    }

    //构建父子关系
    private List<RouterVO> checkChildRouterVOS(List<RouterVO> routerVOS, Long parentId){
        List<RouterVO> answer = new ArrayList<>();
        for(RouterVO routerVO : routerVOS)
            if(routerVO.getParentId().equals(parentId)){
                routerVO.setChildren(checkChildRouterVOS(routerVOS, routerVO.getId()));
                answer.add(routerVO);
            }
        return answer;
    }

    @Override
    public ResponseResult logout(Long loginUserId) {

        redisCache.deleteObject("login:" + loginUserId);

        return ResponseResult.okResult();
    }


}
