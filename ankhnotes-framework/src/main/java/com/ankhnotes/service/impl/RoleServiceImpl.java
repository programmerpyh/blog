package com.ankhnotes.service.impl;

import com.ankhnotes.constants.SystemConstants;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.domain.Role;
import com.ankhnotes.dto.AddNewRoleDto;
import com.ankhnotes.dto.RoleChangeStatusDto;
import com.ankhnotes.dto.UpdateRoleDto;
import com.ankhnotes.exception.SystemException;
import com.ankhnotes.mapper.RoleMapper;
import com.ankhnotes.mapper.RoleMenuMapper;
import com.ankhnotes.service.RoleService;
import com.ankhnotes.utils.BeanCopyUtils;
import com.ankhnotes.vo.PageVO;
import com.ankhnotes.vo.RoleInfoVO;
import com.ankhnotes.vo.RoleListForUserVO;
import com.ankhnotes.vo.RolePageVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2024-03-19 18:53:27
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMenuMapper roleMenuMapper;


    @Override
    public List<String> selectRoleKeyByUserId(Long userId) {


        //特殊处理超级管理员, 超级管理员的角色列表不需要查询, 只需返回"admin"
        if(SystemConstants.Administrator_ID.equals(userId)){
            List<String> roles = new ArrayList<>();
            roles.add("admin");
            return roles;
        }

        //非超级管理员需要连表查询
        return getBaseMapper().selectRoleKeyByOtherUserId(userId);
    }

    @Override
    public ResponseResult selectRolePage(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(status), Role::getStatus, status);
        wrapper.like(StringUtils.hasText(roleName), Role::getRoleName, roleName);
        wrapper.orderByAsc(Role::getRoleSort);

        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);

        List<Role> roleList = page.getRecords();
        List<RolePageVO> vos = BeanCopyUtils.copyBeanList(roleList, RolePageVO.class);
        return ResponseResult.okResult(new PageVO<>(vos, page.getTotal()));
    }

    @Override
    public ResponseResult changeRoleStatus(RoleChangeStatusDto dto) {
        Role role = getById(dto.getRoleId());
        if(role==null)
            throw new RuntimeException("找不到该角色");
        role.setStatus(dto.getStatus());
        boolean isSuccessful = updateById(role);
        if(!isSuccessful)
            throw new RuntimeException("修改角色状态失败");
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addNewRole(AddNewRoleDto dto) {
        Role role = BeanCopyUtils.copyBean(dto, Role.class);
        boolean isSaveSuccessful = save(role);
        if(!isSaveSuccessful)
            throw new RuntimeException("角色添加失败");

        //添加角色-菜单表的相关记录
        dto.getMenuIds().forEach(menuId -> roleMenuMapper.saveRoleMenu(role.getId(), menuId));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleInfo(Long roleId) {
        Role role = getById(roleId);
        if(role==null)
            throw new RuntimeException("找不到该角色");
        RoleInfoVO roleInfoVO = BeanCopyUtils.copyBean(role, RoleInfoVO.class);
        return ResponseResult.okResult(roleInfoVO);
    }

    @Override
    public ResponseResult updateRole(UpdateRoleDto dto) {
        Role role = BeanCopyUtils.copyBean(dto, Role.class);
        boolean isUpdateSuccessful = updateById(role);
        if(!isUpdateSuccessful)
            throw new RuntimeException("角色更新失败");
        //更新角色-菜单关联表信息
        roleMenuMapper.deleteAllByRoleId(dto.getId());
        dto.getMenuIds().forEach(menuId -> roleMenuMapper.saveRoleMenu(dto.getId(), menuId));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectAllRoles() {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        wrapper.orderByAsc(Role::getRoleSort);

        List<Role> roleList = list(wrapper);
        List<RoleListForUserVO> vos = BeanCopyUtils.copyBeanList(roleList, RoleListForUserVO.class);

        return ResponseResult.okResult(vos);
    }
}
