package com.wechat.auth.dao;

import com.wechat.auth.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

/**
 * Created by 30825 on 2017/5/18.
 */

//JpaRepository<T,ID> T:实体对象  ID:主键的类型
@RepositoryDefinition(domainClass = User.class,idClass = Integer.class)
public interface UserDao{
    @Query(nativeQuery = true , value = "select count(1) from user")
    long getCount();

    User findDistinctByOpenId(String openId);

    @Query(nativeQuery = true , value = "select u.nick_name from user u where open_id = :openId")
    String findnickNameDistinctByOpenId(@Param("openId") String openId);


    @Modifying()
    @Query(nativeQuery = true , value = "update user u set u.open_id = :openId where u.nick_name = :nickName")
    void updateUser(@Param("nickName") String nickName, @Param("openId") String openId);


}
