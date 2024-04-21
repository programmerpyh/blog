package com.ankhnotes.service.impl;

import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.domain.Role;
import com.ankhnotes.domain.User;
import com.ankhnotes.dto.UpdateUserDto;
import com.ankhnotes.enums.AppHttpCodeEnum;
import com.ankhnotes.exception.SystemException;
import com.ankhnotes.mapper.UserMapper;
import com.ankhnotes.mapper.UserRoleMapper;
import com.ankhnotes.service.RoleService;
import com.ankhnotes.service.UserService;
import com.ankhnotes.utils.BeanCopyUtils;
import com.ankhnotes.utils.SecurityUtils;
import com.ankhnotes.vo.*;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2024-03-11 18:22:58
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    RoleService roleService;

    @Override
    public ResponseResult getUserInfo() {
        Long loginUserId = SecurityUtils.getLoginUserId();

        User loginUser = getById(loginUserId);

        UserInfoVO loginUserInfoVO = BeanCopyUtils.copyBean(loginUser, UserInfoVO.class);

        return ResponseResult.okResult(loginUserInfoVO);
    }

    @Override
    public ResponseResult updateUserInfo(User loginUser) {

        updateById(loginUser);

        return ResponseResult.okResult();
    }

    @Override
    //手机号码前端还没加上, 先不管
    public ResponseResult register(User registerUser) {
        String userName = registerUser.getUserName();
        String nickName = registerUser.getNickName();
        String email = registerUser.getEmail();
        String password = registerUser.getPassword();

        //用户名为空
        if(!StringUtils.hasText(userName)){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        //昵称为空
        if(!StringUtils.hasText(nickName)){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //邮箱为空
        if(!StringUtils.hasText(email)){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        //密码为空
        if(!StringUtils.hasText(password)){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }

        //用户名已存在
        if(isExist(User::getUserName, userName)){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        //昵称已存在
        if(isExist(User::getNickName, nickName)){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //邮箱已存在
        if(isExist(User::getEmail, email)){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        String encodedPassword = passwordEncoder.encode(password);
        registerUser.setPassword(encodedPassword);

        save(registerUser);

        return ResponseResult.okResult();

    }

    @Override
    public ResponseResult selectUserPage(Integer pageNum, Integer pageSize, String username
            , String phonenumber, String status) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(username), User::getUserName, username);
        wrapper.like(StringUtils.hasText(phonenumber), User::getPhonenumber, phonenumber);
        wrapper.eq(StringUtils.hasText(status), User::getStatus, status);

        Page<User> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        List<AdminUserVO> vos = BeanCopyUtils.copyBeanList(page.getRecords(), AdminUserVO.class);

        return ResponseResult.okResult(new PageVO<>(vos, page.getTotal()));
    }

    @Override
    public ResponseResult addNewUser(User user) {
        //判空
        if(!StringUtils.hasText(user.getUserName()))
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        if(!StringUtils.hasText(user.getPassword()))
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        if(!StringUtils.hasText(user.getNickName()))
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        if(!StringUtils.hasText(user.getSex()))
            throw new RuntimeException("性别不能为空");
        //判重复
        //用户名已存在
        if(isExist(User::getUserName, user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        //昵称已存在
        if(isExist(User::getNickName, user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //邮箱已存在
        if(isExist(User::getEmail, user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //手机号已存在
        if(isExist(User::getPhonenumber, user.getPhonenumber()))
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);

        //加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        boolean isSaveSuccessful = save(user);
        if(!isSaveSuccessful)
            throw new RuntimeException("新增用户失败");

        //添加角色身份到用户-角色关联表
        user.getRoleIds().forEach(roleId -> userRoleMapper.saveUserRole(user.getId(), roleId));

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserInfoToUpdate(Long id) {
        //查询并封装用户信息
        User user = getById(id);
        UserInfoToUpdateVO userVO = BeanCopyUtils.copyBean(user, UserInfoToUpdateVO.class);

        //查询用户的角色信息
        List<Long> roleIds = userRoleMapper.selectRoleIdsByUserId(id);
        List<Role> roleList = roleService.list();
        roleList.sort(Comparator.comparing(Role::getRoleSort));
        List<RoleListForUserVO> roleVOs = BeanCopyUtils.copyBeanList(roleList, RoleListForUserVO.class);

        //封装并返回
        return ResponseResult.okResult(new AdminUserInfoToUpdateVO(roleIds, roleVOs, userVO));
    }

    @Override
    public ResponseResult updateUser(UpdateUserDto dto) {
        User user = BeanCopyUtils.copyBean(dto, User.class);
        boolean isSuccessful = updateById(user);
        if(!isSuccessful)
            throw new RuntimeException("更新用户失败");
        //更新用户-角色关联表(先删除再添加)
        userRoleMapper.deleteUserRoleByUserId(dto.getId());
        dto.getRoleIds().forEach(roleId -> userRoleMapper.saveUserRole(dto.getId(), roleId));
        return ResponseResult.okResult();
    }

    private <T> Boolean isExist(SFunction<User, T> column, T value){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(column, value);
        return count(queryWrapper)>0;
    }
}
