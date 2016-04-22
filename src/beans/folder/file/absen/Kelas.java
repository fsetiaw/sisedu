package beans.folder.file.absen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;
import beans.folder.file.FileManagement;
import beans.setting.Constants;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import beans.tools.*;
import beans.sistem.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Session Bean implementation class Kelas
 */
@Stateless
@LocalBean
public class Kelas extends FileManagement {
	String schema,kdpst,npm;   
    /**
     * @see FileManagement#FileManagement(String)
     */
    public Kelas(String schema) {
        super(schema);
        this.schema=schema;
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see FileManagement#FileManagement()
     */
    public Kelas() {
        super();
        // TODO Auto-generated constructor stub
    }

    public java.io.File prepAbsenKelas(String thsms_now, String targetKdpst, String tkn_nakmk, String shiftKelas, String nama_dosen, Vector vMhsContainer,java.io.File outFile) {
    	java.io.File file = new File(Constants.getRootMasterAbsensiFile());
    	FileOutputStream fileOut = null;
    	//java.io.File outFile = new File(Constants.getTmpFile()+"/"+tkn_nakmk.replace("`", "_")+"_"+thsms_now+".xlsx");
    	if(file.exists()) {
    		try {
    		//System.out.println("excel ditemeukan");
    			InputStream inp = new FileInputStream(file);
    	    //InputStream inp = new FileInputStream("workbook.xlsx");
    		//System.out.println("1");
    			Workbook wb = WorkbookFactory.create(inp);
    			Sheet sheet = wb.getSheetAt(0);
    			if(vMhsContainer==null) {
    				vMhsContainer = new Vector();
    			}
    			ListIterator li = vMhsContainer.listIterator();
    			//nama makul
				Row row = sheet.getRow(0);
				if(row == null) {
					row = sheet.createRow(0);
				}
				Cell cell = row.getCell(2);
				if (cell == null) {
					cell = row.createCell(2);
				}    
				cell.setCellType(Cell.CELL_TYPE_STRING);
				tkn_nakmk = tkn_nakmk.replaceFirst("`", "");
				cell.setCellValue(tkn_nakmk.replace("`", " / "));
    			
				//thsms now
				row = sheet.getRow(1);
				if(row == null) {
					row = sheet.createRow(1);
				}
				cell = row.getCell(9);
				if (cell == null) {
					cell = row.createCell(9);
				}    
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(thsms_now);
    			
				//nmdos
				row = sheet.getRow(1);
				if(row == null) {
					row = sheet.createRow(1);
				}
				cell = row.getCell(2);
				if (cell == null) {
					cell = row.createCell(2);
				}    
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(nama_dosen);
				
				//kode_ruang-belum dipake
				/*
				row = sheet.getRow(1);
				if(row == null) {
					row = sheet.createRow(1);
				}
				cell = row.getCell(2);
				if (cell == null) {
					cell = row.createCell(2);
				}    
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(nama_dosen);
				*/
				
				//shift
				row = sheet.getRow(2);
				if(row == null) {
					row = sheet.createRow(2);
				}
				cell = row.getCell(2);
				if (cell == null) {
					cell = row.createCell(2);
				}    
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(shiftKelas);
				
    	    //bagian nama mhs
    			int no=1;
    			int brs_ke = 6;
    			//int kol_ke = 2;
    			while(li.hasNext()) {
    	    	//li.set(nmmhs+"`"+npmhs+"`"+kdpst+"`"+smawl);
    				//peralihan ganti kertas
    				if(brs_ke % 25 == 1) {
    					brs_ke = brs_ke+4;
    					//nama makul
    					row = sheet.getRow(brs_ke);
    					if(row == null) {
    						row = sheet.createRow(brs_ke);
    					}
    					cell = row.getCell(2);
    					if (cell == null) {
    						cell = row.createCell(2);
    					}    
    					cell.setCellType(Cell.CELL_TYPE_STRING);
    					tkn_nakmk = tkn_nakmk.replaceFirst("`", "");
    					cell.setCellValue(tkn_nakmk.replace("`", " / "));
    					brs_ke++;
    					
    					//thsms now
    					row = sheet.getRow(brs_ke);
    					if(row == null) {
    						row = sheet.createRow(brs_ke);
    					}
    					cell = row.getCell(9);
    					if (cell == null) {
    						cell = row.createCell(9);
    					}    
    					cell.setCellType(Cell.CELL_TYPE_STRING);
    					cell.setCellValue(thsms_now);
    	    			
    					//nmdos
    					row = sheet.getRow(brs_ke);
    					if(row == null) {
    						row = sheet.createRow(brs_ke);
    					}
    					cell = row.getCell(2);
    					if (cell == null) {
    						cell = row.createCell(2);
    					}    
    					cell.setCellType(Cell.CELL_TYPE_STRING);
    					cell.setCellValue(nama_dosen);
    					brs_ke++;
    					//kode_ruang-belum dipake
    					/*
    					row = sheet.getRow(1);
    					if(row == null) {
    						row = sheet.createRow(1);
    					}
    					cell = row.getCell(2);
    					if (cell == null) {
    						cell = row.createCell(2);
    					}    
    					cell.setCellType(Cell.CELL_TYPE_STRING);
    					cell.setCellValue(nama_dosen);
    					*/
    					
    					//shift
    					row = sheet.getRow(brs_ke);
    					if(row == null) {
    						row = sheet.createRow(brs_ke);
    					}
    					cell = row.getCell(2);
    					if (cell == null) {
    						cell = row.createCell(2);
    					}    
    					cell.setCellType(Cell.CELL_TYPE_STRING);
    					cell.setCellValue(shiftKelas);
    					brs_ke = brs_ke+4;
    				}
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String nmmhs = st.nextToken();
    				String npmhs = st.nextToken();
    				String kdpst = st.nextToken();
    				String smawl = st.nextToken();
    				String nimhs = st.nextToken();	
    				//col no
    				row = sheet.getRow(brs_ke);
    				if(row == null) {
    					row = sheet.createRow(brs_ke);
    				}
    				cell = row.getCell(0);
    				if (cell == null) {
    					cell = row.createCell(0);
    				}    
    				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
    				cell.setCellValue(no++);
    				//brs_ke++;
    				
    				//col nim
    				row = sheet.getRow(brs_ke);
    				if(row == null) {
    					row = sheet.createRow(brs_ke);
    				}
    				cell = row.getCell(1);
    				if (cell == null) {
    					cell = row.createCell(1);
    				}    
    				cell.setCellType(Cell.CELL_TYPE_STRING);
    				cell.setCellValue(nimhs+" / "+npmhs);
    				
    				
				//col nama
    				row = sheet.getRow(brs_ke);
    				if(row == null) {
    					row = sheet.createRow(brs_ke);
    				}
    				cell = row.getCell(2);
    				if (cell == null) {
    					cell = row.createCell(2);
    				}    
    				cell.setCellType(Cell.CELL_TYPE_STRING);
    				cell.setCellValue(nmmhs);
    				brs_ke++;
    				
    			}
    	    // Write the output to a file
    			fileOut = new FileOutputStream(outFile);
    			wb.write(fileOut);
    	    //System.out.println("14");
    			fileOut.close();
    	    //System.out.println("15");
    		}
    		catch (Exception e) {
    			System.out.println("poi err "+e);
    		}
    	}
    	else {
    		System.out.println("excel missing");
    	}
    	return outFile;
    	//System.out.println("done");
    }

}
