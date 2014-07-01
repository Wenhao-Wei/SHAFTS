package com.shafts.database;

import java.util.*;

public final class UserTable extends ShaftsDB<UserInfo> {
	public static final Class<UserInfo> _t=UserInfo.class;
	public UserInfo getUserInfo(String userid){
		return execQueryOne(_t, "select * from shafts_users where userid=?",userid);
	}
	public  UserInfo getUserInfo(int id){
		return execQueryOne(_t, "select * from shafts_users where id=?",id);
	}
	public boolean checkauth(String userid,String password){
		return execQueryOne(_t,"SELECT userid,priv FROM shafts_users WHERE userid=? AND password=?",userid,password)!=null;
	}
	public boolean delete(String userid){
		return execUpdate("DELETE FROM shafts_users WHERE userid=?",userid);
	}
	public boolean add(String userid, String password, String email, String unit, String group, String phone){
		return execUpdate("INSERT INTO shafts_users VALUES (NULL,?,?,?,?,?,?,DEFAULT)",userid,password,email,unit,group,phone);
	}
	public boolean verify(String userid,String key){
		throw new AssertionError("verify is not implemented");
		/*
		def verify(userid,k):
			with mycnx() as conn:
				if myqueryone(conn,"""SELECT userid,priv FROM mogu_users WHERE userid=%s AND priv=%s""",(userid,r'ru({0})'.format(k))):
					mydml(conn,"""UPDATE mogu_users SET priv='ru,ar,cmt' WHERE userid=%s""",(userid,))
					return True
				else: return False
		 */
	}
	public boolean willBeUnique(String userid){
		return execQueryOne(_t,"SELECT userid,priv FROM shafts_users WHERE userid=?",userid)==null;
	}
	public List<UserInfo> getAll(){
		return execQuery(_t, "select * from shafts_users");
	}
}
