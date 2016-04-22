package quartz.example;
import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;  
import org.quartz.Job;  
import org.quartz.JobDataMap;  
import org.quartz.JobExecutionContext;  
import org.quartz.JobExecutionException;

import beans.login.InitSessionUsr;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;

public class TestJob implements Job {

    @Override
    public void execute(final JobExecutionContext ctx)
            throws JobExecutionException {
    	HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
        System.out.println("Executing Job");

    }

}
