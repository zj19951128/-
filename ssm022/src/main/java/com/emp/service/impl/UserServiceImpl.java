package com.emp.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import com.emp.dao.UserDao;
import com.emp.entity.User;
import com.emp.service.UserService;

@Service
public class UserServiceImpl
  implements UserService{
   //ע��Dao����
	@Resource
	private UserDao userDao;
	
	@Override
	public User queryUser(String username) {
		   User user = userDao.queryByUserName(username);
		return user;
	}

	@Override
	public Set<String> queryRoles(String username) {
		  Set<String> roles = userDao.queryRoles(username);
		return roles;
	}

	@Override
	public Set<String> queryPermissions(String username) {
		Set<String> pers = userDao.queryPermissions(username);
		return pers;
	}

	@Override
	public void addUser(User user) {
           //���Σ����ܵ�Ч��
		   //MD5  "123"+"/asd"-->XXOOdasds434-->X))**&&-->...-->....
		 //��������
        String password  // �㷨   ��Ҫ���ܵ�����    ��    ���ܵĴ���
        = new SimpleHash("MD5", user.getPassword(), user.getUsername(), 1024).toString();
        // password  ���Ǽ��ܺ������
        // �û����ܺ�������û�ԭ����ҳ�洫��������
        user.setPassword(password);
        //��user���浽���ݿ���
        userDao.save(user);
	}

}
