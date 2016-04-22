package quartz.example;
import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;  
import org.quartz.Job;  
import org.quartz.JobDataMap;  
import org.quartz.JobExecutionContext;  
import org.quartz.JobExecutionException;  

public class Scheduler implements Job {  


//package com.examples.quartz;  


  

      
    protected static final Log log = LogFactory.getLog(Scheduler.class);  
  
    public void execute(JobExecutionContext jobContext)   
        throws JobExecutionException {  
  
        log.info("entering the quartz config");  
  
        JobDataMap map = jobContext.getJobDetail().getJobDataMap();  
        String username = (String) map.get("username");  
        String password = (String) map.get("password");  
  
        log.info("mapped data: " + username + "/" + password);  
    }  
  
}  