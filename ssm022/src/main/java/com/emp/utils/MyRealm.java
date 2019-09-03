package com.emp.utils;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.emp.entity.User;
import com.emp.service.UserService;
 
/**
 *    
 *  ��֤(��¼)
 *  ��Ȩ
 *  �ĺ���ҵ���߼�
 *
 */
public class MyRealm extends AuthorizingRealm {
 
	@Autowired
	private UserService userService;
 
	/**
	 * ��Ȩ����
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		/**
		 * ע��principals.getPrimaryPrincipal()��Ӧ
		 * new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName())�ĵ�һ������
		 */

		//��ȡ��ǰ��� �û���
		String username = (String) principals.getPrimaryPrincipal();
		//��Ȩ��֤���� ,��Ҫ��ǰ��¼�û����еĽ�ɫ��Ȩ����Ϣ
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		//�����ݿ��в��Ҹ��û��кν�ɫ��Ȩ��
		Set<String> roles = userService.queryRoles(username);
		Set<String> permissions = userService.queryPermissions(username);
		
		//Ϊ��Ȩ��֤�������Ӧ��ɫ��Ȩ��
		info.setRoles(roles);
		info.setStringPermissions(permissions);
		//��infoȡ�ж��û��Ƿ��з���ĳ����Դ
		//������ĳ�ֲ�����Ȩ��
		return info;

	}
 
	/**
	 * ��֤����
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// ��ȡ�û���
		String username = (String) token.getPrincipal();

        // ͨ��username��ѯ�û�����
        User user = userService.queryUser(username);
        //�û���������ƥ������
        if (user != null) {
             //1)principal����֤��ʵ����Ϣ��������userName��Ҳ���������ݿ���Ӧ���û���ʵ�����  
            Object principal = user.getUsername();

            //2)credentials�����ݿ��е�����  
            Object credentials = user.getPassword(); 

            //3)realmName����ǰrealm�����name�����ø����getName()��������  
            String realmName = getName();  

            //4)credentialsSalt��ֵ  
            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUsername());//ʹ���û�����Ϊ��ֵ  

            //�����û��������������AuthenticationInfo����,ͨ��ʹ�õ�ʵ����ΪSimpleAuthenticationInfo
            //5)�����ݿ����û�����������бȶԣ�������ֵ���ܣ���4����������realName��
            SimpleAuthenticationInfo authcInfo = new SimpleAuthenticationInfo(principal, credentials,credentialsSalt,realmName);
            return authcInfo;
        } else {
        	//��ѯ�����û�����null
        	//��֤ʧ��
            return null;
        }

	}
 
}
