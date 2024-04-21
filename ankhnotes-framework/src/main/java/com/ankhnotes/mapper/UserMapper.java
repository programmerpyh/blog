package com.ankhnotes.mapper;

import com.ankhnotes.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-07 14:20:22
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
