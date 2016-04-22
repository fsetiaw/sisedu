package beans.folder;

import javax.ejb.LocalBean;
import java.io.File;
import javax.ejb.Stateless;
import beans.setting.*;
/**
 * Session Bean implementation class CekDataFolderExistence
 */
@Stateless
@LocalBean
public class FolderManagement {
	String schema,kdpst,npm;

    /**
     * Default constructor. 
     */
    public FolderManagement(String schema,String kdpst, String npm) {
        // TODO Auto-generated constructor stub
    	this.schema = schema;
    	this.kdpst = kdpst;
    	this.npm = npm;
    }
    public FolderManagement() {
        // TODO Auto-generated constructor stub

    }

    public void cekAndCreateFolderIfNotExist() {
   		java.io.File file = new File(Constants.getRootFolder()+Constants.getDbschema()+"/"+kdpst+"/"+npm+"/profile");
    	if(!file.exists()) {
   			file.mkdirs();
   		}
   		file = new File(Constants.getRootFolder()+Constants.getDbschema()+"/"+kdpst+"/"+npm+"/bak");
   		if(!file.exists()) {
   			file.mkdirs();
   		}
   		file = new File(Constants.getRootFolder()+Constants.getDbschema()+"/"+kdpst+"/"+npm+"/baa");
   		if(!file.exists()) {
   			file.mkdirs();
   		}
   		file = new File(Constants.getRootFolder()+Constants.getDbschema()+"/"+kdpst+"/"+npm+"/dll");
   		if(!file.exists()) {
   			file.mkdirs();
   		}
   		file = new File(Constants.getTmpFile());
   		if(!file.exists()) {
   			file.mkdirs();
   		}
   		file = new File(Constants.getIncomingUploadFile());
   		if(!file.exists()) {
   			file.mkdirs();
   		}
    }
}
