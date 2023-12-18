package com.zj.union.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.union.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jiejoe499@gmail.com
 * @since 2023-08-17
 */
public interface TUserMapper extends BaseMapper<User> {
    @Select("select * FROM t_user WHERE userName=#{username}")
    Boolean selectByName(String username);

    Boolean uploadImage(Long id, String headPicture);

    @Insert("INSERT INTO t_user_role VALUES (#{userid},1)")
    Boolean activityUser(Long userid);
}
