package com.shafts.database;

import java.util.*;

public class JobTable extends ShaftsDB<JobInfo> {
	public static final Class<JobInfo> _t=JobInfo.class;
	public List<JobInfo> getAll(){
		return execQuery(_t, "select * from shafts_jobs");
	}
	
	public JobInfo getAt(int id){
		return execQueryOne(_t, "select * from shafts_jobs where id=?",id);
	}
	
	public boolean modify(int id,String title){
		return execUpdate("update shafts_jobs set title=? where id=?",title,id);
	}
	
	public boolean add(int uid,String title){
		return execUpdate("insert into shafts_jobs values (NULL,?,?,DEFAULT)",uid,title);
	}
	
	public boolean delete(int id){
		return execUpdate("delete from shafts_jobs where id=?",id);
	}
}
