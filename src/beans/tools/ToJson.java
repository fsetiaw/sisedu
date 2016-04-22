package beans.tools;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.owasp.esapi.ESAPI;

import java.sql.ResultSet;
import java.sql.Timestamp;

public class ToJson {

	public static JSONArray toJSONArray(ResultSet rs) throws Exception {
		JSONArray json = new JSONArray();
		String tmp = null;
		try {
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			//System.out.println("java.sql.Types.DOUBLE="+java.sql.Types.DOUBLE);
			while(rs.next()) {
				int numColumns = rsmd.getColumnCount();
				JSONObject job = new JSONObject();
				for(int i=1;i<numColumns+1;i++) {
					String column_name = rsmd.getColumnName(i);
					//System.out.println("column_name ="+column_name);
					//System.out.println("column_type ="+rsmd.getColumnType(i));
					if(rsmd.getColumnType(i)==java.sql.Types.ARRAY) {
						job.put(column_name, rs.getArray(column_name));
						//System.out.println("to Ayyar");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT) {
						job.put(column_name, rs.getInt(column_name));
						//System.out.println("to bigint");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN) {
						job.put(column_name, rs.getBoolean(column_name));
						//System.out.println("to boolean");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.BLOB) {
						job.put(column_name, rs.getBlob(column_name));
						//System.out.println("to blob");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.DATE) {
						job.put(column_name, rs.getDate(column_name));
						//System.out.println("to Date");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE) {
						job.put(column_name, rs.getDouble(column_name));
						//System.out.println("to double");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT) {
						job.put(column_name, rs.getFloat(column_name));
						//System.out.println("to floar");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER) {
						job.put(column_name,rs.getLong(column_name));
						//System.out.println("to long");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT) {
						job.put(column_name,rs.getLong(column_name));
						//System.out.println("to long");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.TIME) {
						job.put(column_name, rs.getTime(column_name));
						//System.out.println("to time");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP) {
						job.put(column_name, rs.getTimestamp(column_name));	
					}	
					else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR) {
						tmp = rs.getString(column_name);
						tmp = ESAPI.encoder().canonicalize(tmp);
						tmp = ESAPI.encoder().encodeForHTML(tmp);
						job.put(column_name, tmp);
						//job.put(column_name, rs.getString(column_name));
						//System.out.println("to varchar");
					}
					else if(rsmd.getColumnType(i)==-1) { //TEXT
						tmp = rs.getString(column_name);
						//tmp = ESAPI.encoder().canonicalize(tmp);
						//tmp = ESAPI.encoder().encodeForHTML(tmp);
						job.put(column_name, tmp);
						//job.put(column_name, rs.getString(column_name));
						//System.out.println("to varchar");
					}
					else if(rsmd.getColumnType(i)==-6) { //Tiniint
						tmp = rs.getString(column_name);
						//tmp = ESAPI.encoder().canonicalize(tmp);
						//tmp = ESAPI.encoder().encodeForHTML(tmp);
						job.put(column_name, tmp);
						//job.put(column_name, rs.getString(column_name));
						//System.out.println("to tiniInt");
					}
					else if(rsmd.getColumnType(i)==3) { //decimal(15,0);
						tmp = rs.getString(column_name);
						//tmp = ESAPI.encoder().canonicalize(tmp);
						//tmp = ESAPI.encoder().encodeForHTML(tmp);
						job.put(column_name, tmp);
						//job.put(column_name, rs.getString(column_name));
						//System.out.println("to varchar");
					}
				}
				json.put(job);
			}
						
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	
}
