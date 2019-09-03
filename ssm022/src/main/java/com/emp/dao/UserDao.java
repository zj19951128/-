package com.emp.dao;

import java.util.Set;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.emp.entity.User;

public  interface UserDao {
     //�����û��������û�
	@Select("select id,username,password "
			+ "  from sh_user where username=#{username} ")
	User queryByUserName(@Param("username")String username);
	//ע��
	//����û�
	@Insert(" insert into sh_user(username,password) values (#{username},#{password})")
	void save(User user);
	
	 //�����û�����ѯ�û����еĽ�ɫ
	//����ֵ������Shiro��ܶ����
   @Select(" select r.rolename "
   		+ " from sh_user u inner join sh_user_role ur "
   		+ " on u.id = ur.user_id "
   		+ " inner join sh_role r "
   		+ " on ur.role_id = r.id "
   		+ " where u.username=#{username} ")
	Set<String> queryRoles(@Param("username")String username);
	 //�����û�����ѯ�û����е�Ȩ��
    @Select(" select distinct p.permission_name "
    		+ " from sh_user u inner join sh_user_role ur "
    		+ " on u.id = ur.user_id "
    		+ " inner join sh_role r "
    		+ " on ur.role_id = r.id "
    		+ " inner join sh_role_permission rp "
    		+ " on r.id = rp.role_id "
    		+ " inner join sh_permission p "
    		+ " on rp.permission_id = p.id "
    		+ " where u.username=#{username} ")
	Set<String> queryPermissions(@Param("username")String username);
	
	
	
}
