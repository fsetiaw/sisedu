package beans.folder.file.kartu;

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
 * Session Bean implementation class Uts
 */
@Stateless
@LocalBean
public class Uts extends FileManagement {
	String schema,kdpst,npm;   
    /**
     * @see FileManagement#FileManagement(String)
     */
    public Uts(String schema) {
        super(schema);
        this.schema=schema;
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see FileManagement#FileManagement()
     */
    public Uts() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void prepKartuUTS(String thsms,String kdpst,String nmfak,String nmpst,String nimhs,String  nmmhs,Vector vKdmk_nakmk,String namaUjian,String outNameFile,String namaPencetak,String namaValidator) {
    	java.io.File file = new File(Constants.getRootMasterKartuUjianFile()+"/"+namaUjian+"/"+Constants.getNamaFileMasterKartuUjian());
    	//System.out.println("out file = "+Constants.getTmpFile()+"/"+namaUjian+"/"+kuiid+".xlsx");
    	java.io.File outFile = new File(Constants.getTmpFile()+"/"+outNameFile+".xlsx");
    	if(file.exists()) {
    		try {
    		System.out.println("excel ditemeukan");
    		InputStream inp = new FileInputStream(file);
    	    //InputStream inp = new FileInputStream("workbook.xlsx");
    		System.out.println("1");
    	    Workbook wb = WorkbookFactory.create(inp);
    	    Sheet sheet = wb.getSheetAt(0);
    	    System.out.println("2");
    	    //col nim
    	    Row row = sheet.getRow(1);
    	    if(row == null) {
				row = sheet.createRow(1);
			}
    	    Cell cell = row.getCell(2);
    	    if (cell == null) {
    	        cell = row.createCell(2);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(nimhs+"-"+nmmhs);
    	   
    	    //col semester
    	    row = sheet.getRow(1);
    	    if(row == null) {
				row = sheet.createRow(1);
			}
    	    cell = row.getCell(8);
    	    if (cell == null) {
    	        cell = row.createCell(8);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(Converter.convertThsmsKeterOnlyFromatThnAkademik(thsms));
    	    
    	  //col nama fakultas
    	    row = sheet.getRow(2);
    	    if(row == null) {
				row = sheet.createRow(2);
			}
    	    cell = row.getCell(2);
    	    if (cell == null) {
    	        cell = row.createCell(2);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(nmfak.toUpperCase());
    	    
    	  //col nama prodi
    	    row = sheet.getRow(2);
    	    if(row == null) {
				row = sheet.createRow(2);
			}
    	    cell = row.getCell(8);
    	    if (cell == null) {
    	        cell = row.createCell(8);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(nmpst.toUpperCase());
    	    
    	    int norut=0;
    	    int baris=4;
    	    
    	    if(vKdmk_nakmk!=null && vKdmk_nakmk.size()>0) {
    	    	ListIterator li = vKdmk_nakmk.listIterator();
    	    	while(li.hasNext()) {
    	    		norut++;
    	    		String brs = (String)li.next();
    	    		StringTokenizer st = new StringTokenizer(brs,"$");
    	    		String kdkmk = st.nextToken();
    	    		String nakmk = st.nextToken();
    	    		boolean noGanjil = Checker.bilanganGanjilKah(""+norut);
    	    		//col no urut
    	    	    row = sheet.getRow(baris);
    	    	    if(row == null) {
    					row = sheet.createRow(baris);
    				}
    	    	    cell = row.getCell(0);
    	    	    if (cell == null) {
    	    	        cell = row.createCell(0);
    	    	    }    
    	    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    	    cell.setCellValue(norut+".");
    	    	    
    	    	  //col nama
    	    	    row = sheet.getRow(baris);
    	    	    if(row == null) {
    					row = sheet.createRow(baris);
    				}
    	    	    cell = row.getCell(3);
    	    	    if (cell == null) {
    	    	        cell = row.createCell(3);
    	    	    }    
    	    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    	    cell.setCellValue(nakmk);
    	    	    
    	    	    if(noGanjil) {
    	    	    	//col paraf
        	    	    row = sheet.getRow(baris);
        	    	    if(row == null) {
        					row = sheet.createRow(baris);
        				}
        	    	    cell = row.getCell(8);
        	    	    if (cell == null) {
        	    	        cell = row.createCell(8);
        	    	    }    
        	    	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    	    cell.setCellValue(norut+". ______");
    	    	    }
    	    	    else {
    	    	    	//col paraf
        	    	    row = sheet.getRow(baris);
        	    	    if(row == null) {
        					row = sheet.createRow(baris);
        				}
        	    	    cell = row.getCell(9);
        	    	    if (cell == null) {
        	    	        cell = row.createCell(9);
        	    	    }    
        	    	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    	    cell.setCellValue("______ ."+norut);
    	    	    	
    	    	    }
    	    	    baris++;
    	    	}
    	    }
    	  //col trmpat tanggal
    	    row = sheet.getRow(15);
    	    if(row == null) {
				row = sheet.createRow(15);
			}
    	    cell = row.getCell(3);
    	    if (cell == null) {
    	        cell = row.createCell(3);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue("Jakarta, "+AskSystem.getCurrentTimeInLongStringFormat());
    	    
    	  //col pencetak
    	    //row = sheet.getRow(baris+3);
    	    row = sheet.getRow(18);
    	    if(row == null) {
				row = sheet.createRow(18);
			}
    	    cell = row.getCell(1);
    	    if (cell == null) {
    	        cell = row.createCell(1);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(namaPencetak);
    	    
    	  //col validator
    	    row = sheet.getRow(18);
    	    if(row == null) {
				row = sheet.createRow(18);
			}
    	    cell = row.getCell(8);
    	    if (cell == null) {
    	        cell = row.createCell(8);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(namaValidator);
    	    
    	    // Write the output to a file
    	    FileOutputStream fileOut = new FileOutputStream(outFile);
    	    wb.write(fileOut);
    	    System.out.println("14");
    	    fileOut.close();
    	    System.out.println("15");
    		}
    		catch (Exception e) {
    			System.out.println("poi err "+e);
    		}
    	}
    	else {
    		System.out.println("excel missing");
    	}
    	System.out.println("done");
    }

}
