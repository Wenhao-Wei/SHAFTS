package com.shafts.ui;

public class TreadPool extends Thread{
	/**
     * 网络查询下载结果
     * @param jobid
     * @param path
     * @author xiaobao
     *
     */
	private String jobid;
	private String path;
    public TreadPool(String jobid, String path){
    	this.jobid = jobid;
    	this.path = path;
    }
    	public void run(){
    		SFTPConnection sftp = new SFTPConnection();
			sftp.connect();
	    	sftp.batchDownLoadFile(jobid, path);
	        sftp.disconnect();
	        this.start();
    	}
    }

