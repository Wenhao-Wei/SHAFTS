package com.shafts.database;
import java.util.*;
public class Test {

	public static void main(String[] args) {
		UserTable usertable=new UserTable();
		List<UserInfo>q=usertable.getAll();
		for(UserInfo i:q){
			System.out.println(i.userid);
		}
		System.out.println("q2.");
		UserInfo q2=usertable.getUserInfo(1);
		System.out.println(q2.id);
		System.out.println(q2.userid);
	}

}
