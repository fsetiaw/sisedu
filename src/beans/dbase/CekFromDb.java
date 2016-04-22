package beans.dbase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Collections;
import org.apache.tomcat.jdbc.pool.*;
import beans.sistem.AskSystem;
import beans.setting.*;
import beans.tools.*;
import java.util.LinkedHashSet;
import java.util.Collections;
/**
 * Session Bean implementation class SearchDb
 */
@Stateless
@LocalBean
public class CekFromDb {
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;
    /**
     * Default constructor. 
     */
    public CekFromDb() {
        // TODO Auto-generated constructor stub
    }

    public CekFromDb(Connection con) {
    	this.con = con;
    }
    
    public Connection getCon() {
    	return con;
    }
    

    
    
    
}
