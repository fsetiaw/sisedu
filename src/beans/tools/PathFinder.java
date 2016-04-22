package beans.tools;

import java.util.StringTokenizer;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class PathFinder
 */
@Stateless
@LocalBean
public class PathFinder {

    /**
     * Default constructor. 
     */
    public PathFinder() {
        // TODO Auto-generated constructor stub
    }

    
    public static String getPath(String uri, String target) {
    	java.util.StringTokenizer stUri = new StringTokenizer(uri,"/");
    	java.util.StringTokenizer stTarget = new StringTokenizer(target,"/");
    	String ff_url="";
    	boolean stillsameDir = false;
    	/*
		 * if target in the same directory
		 */
    	
    	if(stUri.countTokens()==stTarget.countTokens()) {
    		stillsameDir = true;
    		int counter = stUri.countTokens();
    		for(int i=1;i<counter && stillsameDir;i++) {
    			if(!stUri.nextToken().equalsIgnoreCase(stTarget.nextToken())) {
    				stillsameDir = false;
    			}
    		}
    		if(stillsameDir) {
    			ff_url = stTarget.nextToken();
    		}
    		else {
    			stillsameDir = false;
    		}
    	}
    	//System.out.println("same dir? "+stillsameDir);
    	if(!stillsameDir) {
    		java.util.StringTokenizer st = new StringTokenizer(uri,"/");
        	String root_ignore = st.nextToken();
    		while(st.hasMoreTokens() && st.countTokens()>1) {
    			st.nextToken();
    			ff_url = ff_url+"../";
    		}
    		st = new StringTokenizer(target,"/");
    		root_ignore = st.nextToken();
    		while(st.hasMoreTokens()) {
    			ff_url = ff_url+st.nextToken();
    			if(st.hasMoreTokens()) {
    				ff_url = ff_url+"/";
    			}
    		}
    	}	
    	//st = new StringTokenizer(ff_url);
    	//System.out.println("ff_url="+ff_url);
    	return ff_url;
    }
    /*
    public static String getPath(String uri, String target) {
    	String tmp = "/";
    	StringTokenizer stu = new StringTokenizer(uri,"/");
    	StringTokenizer stt = new StringTokenizer(target,"/");
    	int stu_tokens = stu.countTokens();
    	int stt_tokens = stt.countTokens();
    	if(stu_tokens==stt_tokens) {
    		while(stt.hasMoreTokens()) {
    			target = stt.nextToken();
    		}
    		tmp = ""+target;
    	}
    	else {
    		if(stu_tokens>stt_tokens) {
    			tmp = "";
    			int selisih = stu_tokens-stt_tokens;
    			for(int i=0;i<selisih;i++) {
    				tmp = tmp+"../";
    			}
    			while(stt.hasMoreTokens()) {
    				if(stt.countTokens()==1) {
    					tmp = tmp+stt.nextToken();
    				}
    				else {
    					stt.nextToken();
    				}
    			}
    			System.out.println(tmp);
    		}
    		else {
    			tmp="";
    			int selisih = stu.countTokens()-1;
    			for(int i=0;i<selisih;i++){
    				stt.nextToken();
    			}
    			while(stt.hasMoreTokens()) {
    				tmp = tmp+stt.nextToken();
    				if(stt.hasMoreTokens()) {
    					tmp = tmp+"/";
    				}
    			}
    			System.out.println(tmp);
    		}
    	}
    	return tmp;
    }
    */
}
