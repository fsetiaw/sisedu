package beans.folder.file;

import javax.ejb.LocalBean;
import beans.setting.*;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.owasp.esapi.ESAPI;
import java.util.Collections;
import java.util.Vector;
import java.util.ListIterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.StringTokenizer;
import beans.tools.*;
import beans.setting.*;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import beans.dbase.*;
import beans.sistem.*;
//poi staff
import org.apache.poi.ss.usermodel.*;
/**
 * Session Bean implementation class FileManagement
 */
@Stateless
@LocalBean
public class FileManagement {
	String schema,kdpst,npm;
	
    /**
     * Default constructor. 
     */
    public FileManagement(String schema) {
        // TODO Auto-generated constructor stub
    	this.schema = schema;
    }
    public FileManagement() {
        // TODO Auto-generated constructor stub
    	this.schema = schema;
    }
    
    public String prosesFormKurikulum(String fileName, String kdpst, String msg, Vector v) {
    	msg = "proses form kurikulum "+kdpst;
		java.io.File file = new File(Constants.getIncomingUploadFile()+"/"+fileName);
		ListIterator li = v.listIterator();
    	if(file.exists()) {
    		try {
    			InputStream inp = new FileInputStream(file);
    			msg = msg+"<br /> file "+fileName+" ditemukan";
        	    //InputStream inp = new FileInputStream("workbook.xlsx");
    			Workbook wb = WorkbookFactory.create(inp);
    			int tot_sheet = wb.getNumberOfSheets();
    			msg = msg+"<br /> total sheet = "+tot_sheet;
    			
    			for(int i=0;i<tot_sheet;i++) {
    				////System.out.println("i="+i);
    				Sheet sheet = wb.getSheetAt(i);
    				String baris = "";
    				//get status
    				Row row = sheet.getRow(0);
					Cell cell = row.getCell(4);
					String value = "";
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						value = ""+cell.getRichStringCellValue().getString();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							value = ""+cell.getDateCellValue();
						} else {
							value = ""+cell.getNumericCellValue();
						}
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						value = ""+cell.getBooleanCellValue();
						break;
					case Cell.CELL_TYPE_FORMULA:
						value = ""+cell.getCellFormula();
						break;
					default:
						value = null;
					}
					baris = baris+value;
					////System.out.println("baris="+baris);
					li.add(i+","+baris);
					boolean stop = false;
    				for(int j=4;j<300 && !stop;j++) {
    					////System.out.println("j="+j);
    					String tmp = "";
    					baris="";
    					row = sheet.getRow(j);
    					for(int k=0;k<12 && !stop;k++) {
    						////System.out.println("i,j,k="+i+","+j+","+k);
    						cell = row.getCell(k);
    						value = "";
    						switch (cell.getCellType()) {
    						case Cell.CELL_TYPE_STRING:
    							value = ""+cell.getRichStringCellValue().getString();
    							break;
    						case Cell.CELL_TYPE_NUMERIC:
    							if (DateUtil.isCellDateFormatted(cell)) {
    								value = ""+cell.getDateCellValue();
    							} else {
    								value = ""+cell.getNumericCellValue();
    							}
    							break;
    						case Cell.CELL_TYPE_BOOLEAN:
    							value = ""+cell.getBooleanCellValue();
    							break;
    						case Cell.CELL_TYPE_FORMULA:
    							//value = ""+cell.getCellFormula();
    							switch(cell.getCachedFormulaResultType()) {
    				            case Cell.CELL_TYPE_NUMERIC:
    				                value =  ""+cell.getNumericCellValue();
    				                break;
    				            case Cell.CELL_TYPE_STRING:
    				                value =  ""+cell.getRichStringCellValue();
    				                break;
    				        }
    							break;
    						default:
    							value = null;
    						}
    						////System.out.println("value="+value);
    						tmp = tmp + value+",";
    						if((k==2 || k==3 || k==4 || k==10)&&(value==null)) {
    							tmp = null;
    							stop = true;
    						}
    					}//end for k
    					if(tmp!=null) {
    						baris = baris + tmp;
    						baris = baris.replaceAll("null", " ");
    						li.add(baris);
    					}
    					////System.out.println("baris1="+baris);
    				}	
    			}//end int i
    			////System.out.println("selesai");
    			Vector v1 = new Vector();
    			ListIterator li1 = v1.listIterator();
    			Vector vtp = new Vector();//utk ngecek thsms, cuma boleh 1 thsms per-file
    			ListIterator litp = vtp.listIterator();
    			li = v.listIterator();
    			Vector vTmp = new Vector();
				ListIterator liTmp = vTmp.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,",");
    				////System.out.println("tokens = "+st.countTokens());
    				
    				if(st.countTokens()>2) {
    					String tkn1 = st.nextToken();
    					StringTokenizer stt = new StringTokenizer(tkn1,".");
    					tkn1 = stt.nextToken();
    					String tkn2 = st.nextToken();
    					stt = new StringTokenizer(tkn2,".");
    					tkn2 = stt.nextToken();
    					String tkn3 = st.nextToken();
    					stt = new StringTokenizer(tkn3,".");
    					tkn3 = stt.nextToken();
    					String tkn4 = st.nextToken();
    					stt = new StringTokenizer(tkn4,".");
    					tkn4 = stt.nextToken();
    					String tkn5 = st.nextToken();
    					stt = new StringTokenizer(tkn5,".");
    					tkn5 = stt.nextToken();
    					String tkn6 = st.nextToken();//kelompok MK
    					stt = new StringTokenizer(tkn6,".");
    					tkn6 = stt.nextToken();
    					String tkn7 = st.nextToken();//jenis mk
    					stt = new StringTokenizer(tkn7,".");
    					tkn7 = stt.nextToken();
    					String tkn8 = st.nextToken();
    					stt = new StringTokenizer(tkn8,".");
    					tkn8 = stt.nextToken();
    					String tkn9 = st.nextToken();
    					stt = new StringTokenizer(tkn9,".");
    					tkn9 = stt.nextToken();
    					String tkn10 = st.nextToken();
    					stt = new StringTokenizer(tkn10,".");
    					tkn10 = stt.nextToken();
    					String tkn11 = st.nextToken();
    					stt = new StringTokenizer(tkn11,".");
    					tkn11 = stt.nextToken();
    					String tkn12 = st.nextToken();//pengampu
    					stt = new StringTokenizer(tkn12,".");
    					tkn12 = stt.nextToken();
    					//String tkn13 = st.nextToken();
    					//stt = new StringTokenizer(tkn13,".");
    					//tkn13 = stt.nextToken();//reserved
    					liTmp.add(tkn1+","+tkn2+","+tkn5+","+tkn3+","+tkn4+","+tkn6+","+tkn7+","+tkn8+","+tkn9+","+tkn10+","+tkn11+","+tkn12);
    					////System.out.println(tkn1+","+tkn2+","+tkn5+","+tkn3+","+tkn4+","+tkn6+","+tkn7+","+tkn8+","+tkn9+","+tkn10+","+tkn11+","+tkn12+","+tkn13);
    					litp.add(tkn1);//buat cek 
    				}
    				else {
    					if(vTmp.size()>0) {
    						Collections.sort(vTmp);
    						li1.add(vTmp);
    					}
    					li1.add(brs);
    					vTmp = new Vector();
        				liTmp = vTmp.listIterator();
    				}
    			}	
    			/*
    			 * pastikan hanya satu thsms di filenya based n size vtp
    			 */
    			vtp = Tool.removeDuplicateFromVector(vtp);
    			litp = vtp.listIterator();
    			if(vtp.size()>1) {
    				//ada lebih dari satu thsms, terminated
    				msg = msg+"<br /> <h3>Ada lebih dari 1 thsms, proses terminated</h3>";
    			}
    			else {
    				/*
    				 * lolos pengecekan 1 thsms only
    				 */
    				String thsms_target =  (String)litp.next(); //thsms under work
    				/*
    				 * sort sehingga berurutan berdasar semes dan kdkmk
    				 */
    				Collections.sort(vTmp);
    				li1.add(vTmp);
    				li1 = v1.listIterator();
    			
    				/*
    				 * proses seleksi distinct mk pada tiap kurikulum
    				 * menggunakan vc
    				 * vt - menyimpan data status kurikulum (norut)A/H, untuk digunakan utk update status kurikulum
    				 */
    				Vector vt = new Vector();
    				ListIterator lit = vt.listIterator();
    				Vector vc = new Vector();
    				ListIterator lic = vc.listIterator();
    				
    				while(li1.hasNext()) {
    					String brs = (String)li1.next();
    					StringTokenizer st = new StringTokenizer(brs,",");
    					String nomokur = st.nextToken();
    					String statKur = st.nextToken();
    					if(statKur.equalsIgnoreCase("aktif")) {
    						statKur = nomokur+"A";
    						
    					}
    					else {
    						if(statKur.equalsIgnoreCase("non-aktif")) {
    							statKur = nomokur+"N";
    						}
    					}
    					lit.add(statKur);
    					v = (Vector)li1.next();
    					li = v.listIterator();
    					while(li.hasNext()) {
    						String baris = (String)li.next();
    						st = new StringTokenizer(baris,",");
    						String thsms = st.nextToken();
    						kdpst = st.nextToken();
    						String smsmk = st.nextToken();
    						String kdkmk = st.nextToken();
    						StringTokenizer stt = new StringTokenizer(kdkmk);
    						kdkmk = "";
    						while(stt.hasMoreTokens()) {
    							kdkmk = kdkmk+stt.nextToken();
    							if(stt.hasMoreTokens()) {
    								kdkmk = kdkmk+" ";
    							}
    						}
    						String nakmk = st.nextToken();
    						stt = new StringTokenizer(nakmk);
    						nakmk = "";
    						while(stt.hasMoreTokens()) {
    							nakmk = nakmk+stt.nextToken();
    							if(stt.hasMoreTokens()) {
    								nakmk = nakmk+" ";
    							}
    						}
    						String kdkel = st.nextToken();
    						String kdwpl = st.nextToken();
    						String skstm = st.nextToken();
    						String skspr = st.nextToken();
    						String skslp = st.nextToken();
    						String sksmk = st.nextToken();
    						String nodos = st.nextToken();
    						//String tkndos = st.nextToken();
    						/*
    						 * variable2 utk menentukan distinct
    						 */
    						//lic.add(kdkmk+" "+sksmk+" "+smsmk+" "+nakmk);
    						lic.add(smsmk+" "+kdkmk+" "+sksmk+" "+nakmk);
    					}
    				}	
    				/*
    				 * hapus prev rec di tbkmk
    				 */
    				UpdateDb udb = new UpdateDb();
    				udb.deletePrevRecTbkmk(thsms_target, kdpst);
    				msg = msg + "<br/>Menghapus rekord tbkmk untuk prodi="+kdpst+" dan thsms="+thsms_target;
    					
    				/*
    				 * insert init tbkmk - distinct thsms kdpst kdkmk nakmk ke tbkmk
    				 */
    				vc = Tool.removeDuplicateFromVector(vc);
    				udb.insertInitTbkmk(thsms_target,kdpst,vc);
    				msg = msg + "<br/>Insert rekord tbkmk baru untuk prodi="+kdpst+" dan thsms="+thsms_target;
    				/*
    				 * update status mk hapus
    				 */
    				msg = msg + "<br/>Cek Mata Kuliah yang Aktif / Hapus";
    				////System.out.println("mulai hapus");
    				li1 = v1.listIterator();
    				while(li1.hasNext()) {
    					String baris1=(String)li1.next();
    					Vector vtmp = (Vector)li1.next();
    					//ListIterator litmp = vtmp.listIterator();
    					StringTokenizer st = new StringTokenizer(baris1,",");
    					String nokur = st.nextToken();
    					String status = st.nextToken();
    					if(status.equalsIgnoreCase("NON-AKTIF")) {
    						/*
    						 * proses status hapus
    						 */
    						udb.setStatusMkTo(vtmp, "H");
    					}
    				}
    				/*
    				 * update status mk aktif
    				*/
    				msg = msg + "<br/>Cek Mata Kuliah yang Aktif / Hapus";
    				////System.out.println("mulai aktif");
    				li1 = v1.listIterator();
    				while(li1.hasNext()) {
    					String baris1=(String)li1.next();
    					Vector vtmp =(Vector)li1.next();
    					//ListIterator litmp = vtmp.listIterator();
    					StringTokenizer st = new StringTokenizer(baris1,",");
    					String nokur = st.nextToken();
    					String status = st.nextToken();
    					if(status.equalsIgnoreCase("AKTIF")) {
    						/*
    						 * proses status aktif
    						 */
    						udb.setStatusMkTo(vtmp, "A");
    					}
    				}
    				/*
    				 * set kur untuk masin2 kdkmk
    				 */
    				msg = msg+"<br/>Set kode Kurikulum per Mata Kuliah";
    				v = new Vector();
					li = v.listIterator();
    				//System.out.println("vc");
    				lic = vc.listIterator();
    				while(lic.hasNext()) {
    					String baris1 = (String)lic.next();
    					////System.out.println(baris1);
    					StringTokenizer st = new StringTokenizer(baris1);
    					String semes1 = st.nextToken();
    					String kdkmk1 = st.nextToken();
    					String sksmk1 = st.nextToken();
    					String nakmk1="";
    					while(st.hasMoreTokens()) {
    						nakmk1 = nakmk1 +st.nextToken();
    						if(st.hasMoreTokens()) {
    							nakmk1 = nakmk1+" ";
    						}
    					}
    					String tmp = semes1+","+sksmk1+","+kdkmk1+","+nakmk1+",";
    					////System.out.println("init tmp = "+tmp);;
    					String list_kur = "";
    					li1 = v1.listIterator();
    					while(li1.hasNext()){
    						String brs = (String)li1.next();
    						////System.out.println(brs);
    						Vector vtmp = (Vector)li1.next();
    						////System.out.println("lanjut-vsize="+vtmp.size());
        					st = new StringTokenizer(brs,",");
        					String nokur = st.nextToken();
        					String stkur = st.nextToken();
        					ListIterator litmp = vtmp.listIterator();
        					while(litmp.hasNext()) {
        						String baris = (String)litmp.next();
        						//System.out.println("baris ="+baris);
        						st = new StringTokenizer(baris,",");
        						String thsms = st.nextToken();
        						kdpst = st.nextToken();
        						String smsmk = st.nextToken();
        						String kdkmk = st.nextToken();
        						StringTokenizer stt = new StringTokenizer(kdkmk);
        						kdkmk = "";
        						while(stt.hasMoreTokens()) {
        							kdkmk = kdkmk+stt.nextToken();
        							if(stt.hasMoreTokens()) {
        								kdkmk = kdkmk+" ";
        							}
        						}
        						String nakmk = st.nextToken();
        						stt = new StringTokenizer(nakmk);
        						nakmk = "";
        						while(stt.hasMoreTokens()) {
        							nakmk = nakmk+stt.nextToken();
        							if(stt.hasMoreTokens()) {
        								nakmk = nakmk+" ";
        							}
        						}
        						String kdkel = st.nextToken();
        						String kdwpl = st.nextToken();
        						String skstm = st.nextToken();
        						String skspr = st.nextToken();
        						String skslp = st.nextToken();
        						String sksmk = st.nextToken();
        						String nodos = st.nextToken();
        						//String tkndos = st.nextToken();
        						if(semes1.equalsIgnoreCase(smsmk)&&kdkmk1.equalsIgnoreCase(kdkmk)&&sksmk1.equalsIgnoreCase(sksmk)&&nakmk1.equalsIgnoreCase(nakmk)){
        							list_kur = list_kur+nokur+" ";
        							////System.out.println(list_kur);
        						}
        					}
    					}
    					st = new StringTokenizer(list_kur);
    					list_kur = "";
    					while(st.hasMoreTokens()) {
    						list_kur = list_kur+st.nextToken();
    						if(st.hasMoreTokens()) {
    							list_kur = list_kur+" ";
    						}
    					}
    					tmp = tmp+list_kur;
    					li.add(tmp);
    					////System.out.println(tmp);
    				}
    				udb.setKodeKurikulumPerMk(v,thsms_target,kdpst);
    				/*
    				 * proses kurikulum status (aktif,non-aktif)
    				 */
    				vt = Tool.removeDuplicateFromVector(vt);
    				udb.setKurikulumSatus(vt,thsms_target,kdpst);
    				/*
    				 * update kelompok dan jenis mata kuliah dan pengampu
    				 */
    				//System.out.println("nue");
    				Vector v_nokur_stkur_sms_kdkmk_nakmk_sks_kodekelompok_keterkelompok_kodejenis_keterjenis_tkn_dosen = new Vector();
    				ListIterator liv = v_nokur_stkur_sms_kdkmk_nakmk_sks_kodekelompok_keterkelompok_kodejenis_keterjenis_tkn_dosen.listIterator();
    				li1 = v1.listIterator();
    				while(li1.hasNext()) {
    					String baris = (String)li1.next();
    					liv.add(baris);
    					Vector v2 = (Vector)li1.next();
    					//System.out.println(baris+" v size ="+v2.size());
    					ListIterator li2 = v2.listIterator();
    					String nokur=null,stkur=null;
    					String kode_kelompok = null;
    					String keter_kelompok = null;
    					String kode_jenis = null;
    					String keter_jenis = null;
    					StringTokenizer st = new StringTokenizer(baris,",");
    					nokur = st.nextToken();
    					stkur = st.nextToken();
    					while(li2.hasNext()) {
    						String brs = (String) li2.next();
    						st = new StringTokenizer(brs,",");
    	    		//		//System.out.println("brs>2 = "+brs);
    	    				String tkn1 = st.nextToken();
    	    				String tkn2 = st.nextToken();
    	    				String tkn3 = st.nextToken();
    	    				String tkn4 = st.nextToken();
    	    				String tkn5 = st.nextToken();
    	    				String tkn6 = st.nextToken();//kelompok MK
    	    				kode_kelompok = null;
    	    				keter_kelompok = null;
    	    				StringTokenizer stp = new StringTokenizer(tkn6,"_");
    	    				if(stp!=null && stp.countTokens()==2) {
    	    					kode_kelompok = stp.nextToken();
        	    				keter_kelompok = stp.nextToken();
    	    				}
    	    				
    	    				String tkn7 = st.nextToken();//jenis mk
    	    				kode_jenis = null;
    	    				keter_jenis = null;
    	    				stp = new StringTokenizer(tkn7,"_");
    	    				if(stp!=null && stp.countTokens()==2) {
    	    					kode_jenis = stp.nextToken();
        	    				keter_jenis = stp.nextToken();
    	    				}
    	    					
    	    				String tkn8 = st.nextToken();
    	    				String tkn9 = st.nextToken();
    	    				String tkn10 = st.nextToken();
    	    				String tkn11 = st.nextToken();
    	    				String tkn12 = st.nextToken();//pengampu
    	    				//liv.add();
    	    				//String tkn13 = st.nextToken();
    	    				//stt = new StringTokenizer(tkn13,".");
    	    				//tkn13 = stt.nextToken();//reserved
    	    				//liTmp.add(tkn1+","+tkn2+","+tkn5+","+tkn3+","+tkn4+","+tkn6+","+tkn7+","+tkn8+","+tkn9+","+tkn10+","+tkn11+","+tkn12);
    	    				////System.out.println(tkn1+","+tkn2+","+tkn5+","+tkn3+","+tkn4+","+tkn6+","+tkn7+","+tkn8+","+tkn9+","+tkn10+","+tkn11+","+tkn12+","+tkn13);
    	    				//litp.add(tkn1);//buat cek 
    	    				//liv.add(nokur+","+stkur+","+sms+","+kdkmk+","+nakmk+","+sks+","+kode_kelompok+","+keter_kelompok+","+kode_jenis+","+keter_jenis+","+tkn_dosen);
    	    				liv.add(nokur+","+stkur+","+tkn3+","+tkn4+","+tkn5+","+tkn11+","+kode_kelompok+","+keter_kelompok+","+kode_jenis+","+keter_jenis+","+tkn12);
    	    				
    					}
    				}
    				liv = v_nokur_stkur_sms_kdkmk_nakmk_sks_kodekelompok_keterkelompok_kodejenis_keterjenis_tkn_dosen.listIterator();
    				udb.setPengampuKodeDanJenisMataKuliah(v_nokur_stkur_sms_kdkmk_nakmk_sks_kodekelompok_keterkelompok_kodejenis_keterjenis_tkn_dosen,thsms_target,kdpst);
    			}//end lolos 1 thsms	
    		}
    		catch (Exception e) {
    			//System.out.println("poi err "+e);
    			msg = msg+"<br /> ada error ngga jelas";
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    		msg = msg+"<br /> file "+fileName+" tidak ditemukan";
    	}
    	return msg;
    }
    
    public String prosesUploadFile(String fileName) {
    	String msg = "";
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	StringTokenizer st = new StringTokenizer(fileName,"-");
    	String kdpst = st.nextToken();
    	kdpst = kdpst.substring(kdpst.length()-5,kdpst.length());
    	if(fileName.startsWith("form_kurikulum")) {
    		msg = msg + prosesFormKurikulum(fileName, kdpst, msg, v);
    	}
    	return msg;
    }
    
    public String getFormKurikulum(String kdpst, String keter)  {
    	//get aktif thsms
    	SearchDb sdb = new SearchDb();
    	String thsms = sdb.getThsmsAktif();
    	StringTokenizer st = new StringTokenizer(keter);
    	keter = "";
    	while(st.hasMoreTokens()) {
    		keter = keter+st.nextToken();
    		if(st.hasMoreTokens()) {
    			keter = keter+"_";
    		}
    	}
    	
    	java.io.File file = new File(Constants.getMasterFormKurikulumFile());
    	////System.out.println("out file = "+Constants.getTmpFile()+"/form_kurikulum_"+kdpst+".xlsx");
    	java.io.File outFile = new File(Constants.getTmpFile()+"/form_kurikulum_"+keter+"_"+kdpst+".xlsx");
    	String namafile = Constants.getTmpFile()+"/form_kurikulum_"+keter+"_"+kdpst+".xlsx";
    	//System.out.println("nama file ="+Constants.getTmpFile()+"/form_kurikulum_"+keter+"_"+kdpst+".xlsx");
    	if(file.exists()) {
    		try {
    			InputStream inp = new FileInputStream(file);
        	    //InputStream inp = new FileInputStream("workbook.xlsx");
    			Workbook wb = WorkbookFactory.create(inp);
        	    Sheet sheet = wb.getSheetAt(0);
        	    //col npm
        	    for(int i=0;i<300;i++) {
        	    	Row row = sheet.getRow(i+2);
        	    	Cell cell = row.getCell(0);
        	    	if (cell == null) {
        	    		cell = row.createCell(0);
        	    	}    
        	    	cell.setCellType(Cell.CELL_TYPE_STRING);
        	    	cell.setCellValue(thsms);
        	    	
        	    	row = sheet.getRow(i+2);
        	    	cell = row.getCell(1);
        	    	if (cell == null) {
        	    		cell = row.createCell(1);
        	    	}    
        	    	cell.setCellType(Cell.CELL_TYPE_STRING);
        	    	cell.setCellValue(kdpst);
        	    
        	    }
        	    //Workbook wb = WorkbookFactory.create(inp);
        	    FileOutputStream fileOut = new FileOutputStream(outFile);
        	    wb.write(fileOut);
        	    fileOut.close();
    		}
    		catch (Exception e) {
    			//System.out.println("poi err "+e);
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    	}
    	return namafile;
    }
    
    public void prepMasterKuitansiPembayaranMhs(String npm,String nim,String nmm,String kuiid,String norut,String tgkui,String tgtrs,String keter,String payee,String amont,String pymtp,String noacc,String opnpm,String opnmm,String setor,String nonpm,String voidd,String dbschema) {
    	java.io.File file = new File(Constants.getMasterKuiFile());
    	//System.out.println("out file = "+Constants.getTmpFile()+"/"+kuiid+".xlsx");
    	java.io.File outFile = new File(Constants.getTmpFile()+"/"+kuiid+".xlsx");
    	if(file.exists()) {
    		try {
    			//System.out.println("excel ditemeukan");
        		InputStream inp = new FileInputStream(file);
        	    //InputStream inp = new FileInputStream("workbook.xlsx");
        		//System.out.println("1");
        	    Workbook wb = WorkbookFactory.create(inp);
        	    Sheet sheet = wb.getSheetAt(0);
        	    //System.out.println("2");
        	    //col npm
        	    Row row = sheet.getRow(4);
        	    Cell cell = row.getCell(5);
        	    if (cell == null) {
        	        cell = row.createCell(5);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(npm+"/"+nim);
        	    //System.out.println("3");
        	    //col nama
        	    row = sheet.getRow(5);
        	    cell = row.getCell(5);
        	    if (cell == null) {
        	        cell = row.createCell(5);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(nmm.toUpperCase());
        	    //System.out.println("4");
        	  //col payee
        	    row = sheet.getRow(6);
        	    cell = row.getCell(5);
        	    //System.out.println("cell ="+cell);
        	    if (cell == null) {
        	        cell = row.createCell(5);
        	    }
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(payee.toUpperCase());
        	    //System.out.println("5");
          	    //col norut
        	    //System.out.println("norut="+norut);
        	    row = sheet.getRow(4);
        	    cell = row.getCell(12);
        	    if (cell == null) {
        	        cell = row.createCell(12);
        	    }
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(norut.toUpperCase());
        	    //System.out.println("6");
          	    //col tgl trs
        	    //System.out.println("tgtrs="+tgtrs);
        	    row = sheet.getRow(6);
        	    cell = row.getCell(12);
        	    if (cell == null) {
        	        cell = row.createCell(12);
        	    }
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    if(tgtrs.equalsIgnoreCase("null")) {
        	    	tgtrs = "N/A";
        	    }
        	    cell.setCellValue(tgtrs.toUpperCase());
        	    //System.out.println("7");
    			
    			//ganti dengan json style
        	    double total_pymnt = 0;
    			JSONArray joa = Getter.readJsonArrayFromUrl("/v1/pymnt/norut/"+norut);
    			int starting_row_ket = 9;
    			if(joa!=null && joa.length()>0) {
    				for(int j=0;j<joa.length();j++) {
    					JSONObject jobTmp = joa.getJSONObject(j);
    					String keter_detail = jobTmp.getString("KETER_PYMNT_DETAIL");
    					String amont_pymnt =  jobTmp.getString("AMONTPYMNT");
    					total_pymnt = total_pymnt + Double.parseDouble(amont_pymnt);
    					//no ket
    		    	    row = sheet.getRow(starting_row_ket);
    		    	    cell = row.getCell(0);
    		    	    if (cell == null) {
    		    	        cell = row.createCell(0);
    		    	    }
    		    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    		    	    cell.setCellValue(starting_row_ket-8+".");
    		    	  //ket
    		    	    row = sheet.getRow(starting_row_ket);
    		    	    cell = row.getCell(2);
    		    	    if (cell == null) {
    		    	        cell = row.createCell(2);
    		    	    }
    		    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    		    	    cell.setCellValue(keter_detail);
    		    	    //System.out.println("9");
    		      	  	//amnt
    		    	    row = sheet.getRow(starting_row_ket++);
    		    	    cell = row.getCell(12);
    		    	    if (cell == null) {
    		    	        cell = row.createCell(12);
    		    	    }
    		    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    		    	    cell.setCellValue(amont_pymnt);
    		    	    //System.out.println("10");
    		    	    //System.out.println("8");
    				}
    			}
    			
    		/*
      	    //no ket
    	    row = sheet.getRow(9);
    	    cell = row.getCell(0);
    	    if (cell == null) {
    	        cell = row.createCell(0);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue("1.");
    	    //System.out.println("8");
    	  
    	  //ket
    	    row = sheet.getRow(9);
    	    cell = row.getCell(2);
    	    if (cell == null) {
    	        cell = row.createCell(2);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(keter);
    	    //System.out.println("9");
      	  	//amnt
    	    row = sheet.getRow(9);
    	    cell = row.getCell(12);
    	    if (cell == null) {
    	        cell = row.createCell(12);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(amont);
    	    //System.out.println("10");
    	      */
    	    //total
    	    row = sheet.getRow(13);
    	    cell = row.getCell(12);
    	    if (cell == null) {
    	        cell = row.createCell(12);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    //cell.setCellValue(amont);total_pymnt
    	    cell.setCellValue(total_pymnt);
    	    //System.out.println("11");
    	  //tgl kui
    	    row = sheet.getRow(15);
    	    cell = row.getCell(12);
    	    if (cell == null) {
    	        cell = row.createCell(12);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(tgkui);

    	    //System.out.println("12");
      	    //nama operator
    	    row = sheet.getRow(20);
    	    cell = row.getCell(8);
    	    if (cell == null) {
    	        cell = row.createCell(8);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(opnmm);
    	    //System.out.println("13");
    	    // Write the output to a file
    	    FileOutputStream fileOut = new FileOutputStream(outFile);
    	    wb.write(fileOut);
    	    //System.out.println("14");
    	    fileOut.close();
    	    //System.out.println("15");
    		}
    		catch (Exception e) {
    			//System.out.println("poi err "+e);
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    	}
    	//System.out.println("done");
    }

    
    public void prepMasterKuitansiPmbMhs(String npm,String nim,String nmm,String kuiid,String norut,String tgkui,String tgtrs,String keter,String payee,String amont,String pymtp,String noacc,String opnpm,String opnmm,String setor,String nonpm,String voidd,String dbschema) {
    	java.io.File file = new File(Constants.getMasterKuiPmbFile());
    	//System.out.println("out file = "+Constants.getTmpFile()+"/"+kuiid+".xlsx");
    	java.io.File outFile = new File(Constants.getTmpFile()+"/"+kuiid+".xlsx");
    	if(file.exists()) {
    		try {
    		//System.out.println("excel ditemeukan");
    		InputStream inp = new FileInputStream(file);
    	    //InputStream inp = new FileInputStream("workbook.xlsx");
    		//System.out.println("1");
    	    Workbook wb = WorkbookFactory.create(inp);
    	    Sheet sheet = wb.getSheetAt(0);
    	    //System.out.println("2");
    	    //col npm
    	    Row row = sheet.getRow(4);
    	    Cell cell = row.getCell(5);
    	    if (cell == null) {
    	        cell = row.createCell(5);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(npm+"/"+nim);
    	    //System.out.println("3");
    	    //col nama
    	    row = sheet.getRow(5);
    	    cell = row.getCell(5);
    	    if (cell == null) {
    	        cell = row.createCell(5);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(nmm.toUpperCase());
    	    //System.out.println("4");
    	  //col payee
    	    row = sheet.getRow(6);
    	    cell = row.getCell(5);
    	    //System.out.println("cell ="+cell);
    	    if (cell == null) {
    	        cell = row.createCell(5);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(payee.toUpperCase());
    	    //System.out.println("5");
      	    //col norut
    	    //System.out.println("norut="+norut);
    	    row = sheet.getRow(4);
    	    cell = row.getCell(12);
    	    if (cell == null) {
    	        cell = row.createCell(12);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(norut.toUpperCase());
    	    //System.out.println("6");
      	    //col tgl trs
    	    //System.out.println("tgtrs="+tgtrs);
    	    row = sheet.getRow(6);
    	    cell = row.getCell(12);
    	    if (cell == null) {
    	        cell = row.createCell(12);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    if(tgtrs.equalsIgnoreCase("null")) {
    	    	tgtrs = "N/A";
    	    }
    	    cell.setCellValue(tgtrs.toUpperCase());
    	    //System.out.println("7");
      	    //no ket
    	    row = sheet.getRow(9);
    	    cell = row.getCell(0);
    	    if (cell == null) {
    	        cell = row.createCell(0);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue("1.");
    	    //System.out.println("8");
    	  //ket
    	    row = sheet.getRow(9);
    	    cell = row.getCell(2);
    	    if (cell == null) {
    	        cell = row.createCell(2);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(keter);
    	    //System.out.println("9");
      	  	//amnt
    	    row = sheet.getRow(9);
    	    cell = row.getCell(12);
    	    if (cell == null) {
    	        cell = row.createCell(12);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(amont);
    	    //System.out.println("10");
    	    //total
    	    row = sheet.getRow(13);
    	    cell = row.getCell(12);
    	    if (cell == null) {
    	        cell = row.createCell(12);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(amont);
    	    //System.out.println("11");
    	  //tgl kui
    	    row = sheet.getRow(15);
    	    cell = row.getCell(12);
    	    if (cell == null) {
    	        cell = row.createCell(12);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(tgkui);

    	    //System.out.println("12");
      	    //nama operator
    	    row = sheet.getRow(20);
    	    cell = row.getCell(8);
    	    if (cell == null) {
    	        cell = row.createCell(8);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(opnmm);
    	    //System.out.println("13");
    	    // Write the output to a file
    	    FileOutputStream fileOut = new FileOutputStream(outFile);
    	    wb.write(fileOut);
    	    //System.out.println("14");
    	    fileOut.close();
    	    //System.out.println("15");
    		}
    		catch (Exception e) {
    			//System.out.println("poi err "+e);
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    	}
    	//System.out.println("done");
    }
    
    

    public void prepMasterKrsMhs(String thsms,String kdpst,String npm,String nmm,Vector vKrsHist,String outNameFile) {
    	String info_kdpst = Converter.getDetailKdpst(kdpst);
    	String nmpst = "N/A";
    	String kdjen = "N/A";
    	String thn_sms = thsms.substring(0,4)+"/"+thsms.substring(4,5);
    	if(info_kdpst!=null) {
    		StringTokenizer st = new StringTokenizer(info_kdpst,"#&");
    		nmpst = st.nextToken();
    		kdjen = st.nextToken();
    		kdjen = Converter.getDetailKdjen(kdjen);
    	}	
    	java.io.File file = new File(Constants.getRootMasterKrsFile()+"/"+kdpst+"/krs.xlsx");
    	//System.out.println("out file = "+Constants.getRootMasterKrsFile()+"/"+kdpst+"/krs.xlsx");
    	//String outNameFile = "krs_"+npm;
    	java.io.File outFile = new File(Constants.getTmpFile()+"/"+outNameFile+".xlsx");
    	if(file.exists()) {
    		try {
    		////System.out.println("excel ditemeukan");
    		InputStream inp = new FileInputStream(file);
    	    //InputStream inp = new FileInputStream("workbook.xlsx");
    	    Workbook wb = WorkbookFactory.create(inp);
    	    Sheet sheet = wb.getSheetAt(0);
    	    
    	    //col nmm - D2
    	    Row row = sheet.getRow(1);
    	    Cell cell = row.getCell(3);
    	    if (cell == null) {
    	        cell = row.createCell(3);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(nmm);
    	  //col npm - N2
    	    row = sheet.getRow(1);
    	    cell = row.getCell(13);
    	    if (cell == null) {
    	        cell = row.createCell(13);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(npm);
    	    //col prodi - D3 
    	    row = sheet.getRow(2);
    	    cell = row.getCell(3);
    	    if (cell == null) {
    	        cell = row.createCell(3);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(nmpst+" ("+kdjen+")");
    	  //col prodi - N3 
    	    row = sheet.getRow(2);
    	    cell = row.getCell(13);
    	    if (cell == null) {
    	        cell = row.createCell(13);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(Converter.convertThsmsKeterOnly(thsms));
    	    ListIterator li = vKrsHist.listIterator();
    	    
    	    int i = 0;
    	   // int no_sta_pos_x = 0,kode_sta_pos_x = 0,nakmk_sta_pos_x = 0;
    	    int sta_pos_y = 6;//cuma butuh satu - kan inputnya perbaris
    	    String sksemi = "0";
    	    while(li.hasNext()) {
    	    	i++;
    	    	String brs = (String)li.next();
    	    	//System.out.println("krs baris = "+brs);
    	    	StringTokenizer st = new StringTokenizer(brs,"#&");
    	    	String thsmsi = st.nextToken();
    	    	String kdkmki = st.nextToken(); 
    	    	String nakmki = st.nextToken(); 
    	    	String nlakhi = st.nextToken();
    	    	String boboti = st.nextToken();
    	    	String sksmki = st.nextToken();
    	    	String kelasi = st.nextToken();
    	    	sksemi = st.nextToken();
    	    	String nlipsi = st.nextToken();
    	    	String skstti = st.nextToken();
    	    	String nlipki = st.nextToken();
    	    	//no A7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(0);
        	    if (cell == null) {
        	        cell = row.createCell(0);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(i);
        	    
        	  //kode B7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(1);
        	    if (cell == null) {
        	        cell = row.createCell(1);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(kdkmki);
        	    
        	  //nakmk D7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(3);
        	    if (cell == null) {
        	        cell = row.createCell(3);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(nakmki);
        	    
        	  //sksmk H7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(7);
        	    if (cell == null) {
        	        cell = row.createCell(7);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(sksmki);
        	    
        	    sta_pos_y++;
    	    }
    	  //sksem H18
    	    row = sheet.getRow(17);
    	    cell = row.getCell(7);
    	    if (cell == null) {
    	        cell = row.createCell(7);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(sksemi);
    	    
    	  //sksem k19
    	    row = sheet.getRow(18);
    	    cell = row.getCell(10);
    	    if (cell == null) {
    	        cell = row.createCell(10);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue("JAKARTA, "+AskSystem.getCurrentTimeInLongStringFormat());
    	    // Write the output to a file
    	    FileOutputStream fileOut = new FileOutputStream(outFile);
    	    wb.write(fileOut);
    	    //System.out.println("14");
    	    fileOut.close();
    	    //System.out.println("15");
    		}
    		catch (Exception e) {
    			//System.out.println("poi err "+e);
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    	}
    	//System.out.println("done");
    }
    
    
    public void prepMasterKhsMhs(String thsms,String kdpst,String npm,String nmm,Vector vKrsHist,String outNameFile) {
    	String info_kdpst = Converter.getDetailKdpst(kdpst);
    	String nmpst = "N/A";
    	String kdjen = "N/A";
    	String thn_sms = thsms.substring(0,4)+"/"+thsms.substring(4,5);
    	if(info_kdpst!=null) {
    		StringTokenizer st = new StringTokenizer(info_kdpst,"#&");
    		nmpst = st.nextToken();
    		kdjen = st.nextToken();
    		kdjen = Converter.getDetailKdjen(kdjen);
    	}	
    	java.io.File file = new File(Constants.getRootMasterKhsFile()+"/"+kdpst+"/khs.xlsx");
    	//System.out.println("out file = "+Constants.getRootMasterKhsFile()+"/"+kdpst+"/khs.xlsx");
    	//String outNameFile = "khs_"+npm;
    	java.io.File outFile = new File(Constants.getTmpFile()+"/"+outNameFile+".xlsx");
    	if(file.exists()) {
    		try {
    		////System.out.println("excel ditemeukan");
    		InputStream inp = new FileInputStream(file);
    	    //InputStream inp = new FileInputStream("workbook.xlsx");
    	    Workbook wb = WorkbookFactory.create(inp);
    	    Sheet sheet = wb.getSheetAt(0);
    	    
    	    //col nmm - D2
    	    Row row = sheet.getRow(1);
    	    Cell cell = row.getCell(3);
    	    if (cell == null) {
    	        cell = row.createCell(3);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(nmm);
    	  //col npm - N2
    	    row = sheet.getRow(1);
    	    cell = row.getCell(13);
    	    if (cell == null) {
    	        cell = row.createCell(13);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(npm);
    	    //col prodi - D3 
    	    row = sheet.getRow(2);
    	    cell = row.getCell(3);
    	    if (cell == null) {
    	        cell = row.createCell(3);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(nmpst+" ("+kdjen+")");
    	  //col prodi - N3 
    	    row = sheet.getRow(2);
    	    cell = row.getCell(13);
    	    if (cell == null) {
    	        cell = row.createCell(13);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(Converter.convertThsmsKeterOnly(thsms));
    	    ListIterator li = vKrsHist.listIterator();
    	    
    	    int i = 0;
    	   // int no_sta_pos_x = 0,kode_sta_pos_x = 0,nakmk_sta_pos_x = 0;
    	    int sta_pos_y = 6;//cuma butuh satu - kan inputnya perbaris
    	    String sksemi = "0";
    	    String nlipsi = "0";
	    	String skstti = "0";
	    	String nlipki = "0";
    	    while(li.hasNext()) {
    	    	i++;
    	    	String brs = (String)li.next();
    	    	//System.out.println(brs);
    	    	StringTokenizer st = new StringTokenizer(brs,"#&");
    	    	String thsmsi = st.nextToken();
    	    	String kdkmki = st.nextToken(); 
    	    	String nakmki = st.nextToken(); 
    	    	String nlakhi = st.nextToken();
    	    	String boboti = st.nextToken();
    	    	String sksmki = st.nextToken();
    	    	double mutu = Double.valueOf(boboti).doubleValue()*Double.valueOf(sksmki).doubleValue();
    	    	String kelasi = st.nextToken();
    	    	sksemi = st.nextToken();
    	    	nlipsi = st.nextToken();
    	    	skstti = st.nextToken();
    	    	nlipki = st.nextToken();
    	    	//no A7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(0);
        	    if (cell == null) {
        	        cell = row.createCell(0);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(i);
        	    
        	  //kode B7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(1);
        	    if (cell == null) {
        	        cell = row.createCell(1);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(kdkmki);
        	    
        	  //nakmk D7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(3);
        	    if (cell == null) {
        	        cell = row.createCell(3);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(nakmki);
        	    
        	  //sksmk L7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(11);
        	    if (cell == null) {
        	        cell = row.createCell(11);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(sksmki);
        	   
        	    //nlakh N7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(13);
        	    if (cell == null) {
        	        cell = row.createCell(13);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(nlakhi);
        	    
        	  //bobot O7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(14);
        	    if (cell == null) {
        	        cell = row.createCell(14);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(boboti);
        	    
        	  //mutu P7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(15);
        	    if (cell == null) {
        	        cell = row.createCell(15);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(""+mutu);
        	    
        	    
        	    sta_pos_y++;
    	    }
    	  //sksem N17
    	    row = sheet.getRow(16);
    	    cell = row.getCell(13);
    	    if (cell == null) {
    	        cell = row.createCell(13);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(sksemi);
    	    
    	  //nlipsi P17
    	    row = sheet.getRow(16);
    	    cell = row.getCell(15);
    	    if (cell == null) {
    	        cell = row.createCell(15);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(""+NumberFormater.return2digit(nlipsi));
    	    
    	  //skstti N18
    	    row = sheet.getRow(17);
    	    cell = row.getCell(13);
    	    if (cell == null) {
    	        cell = row.createCell(13);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(skstti);
    	    
    	  //nlipki P18
    	    row = sheet.getRow(17);
    	    cell = row.getCell(15);
    	    if (cell == null) {
    	        cell = row.createCell(15);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(NumberFormater.return2digit(nlipki));
    	  
    	    
    	    
    	  //tpt-tgl K19
    	    row = sheet.getRow(18);
    	    cell = row.getCell(10);
    	    if (cell == null) {
    	        cell = row.createCell(10);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue("JAKARTA, "+AskSystem.getCurrentTimeInLongStringFormat());
    	    // Write the output to a file
    	    FileOutputStream fileOut = new FileOutputStream(outFile);
    	    wb.write(fileOut);
    	    //System.out.println("14");
    	    fileOut.close();
    	    //System.out.println("15");
    		}
    		catch (Exception e) {
    			//System.out.println("poi err "+e);
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    	}
    	//System.out.println("done");
    }
    
    public void prepMasterUsrPwd(String npm,String nmm,String usr,String pwd,String outNameFile) {
    	////System.out.println("outNameFile="+outNameFile);	
    	java.io.File file = new File(Constants.getRootMasterUsrPwdFile()+"/usrpwd.xlsx");
    	////System.out.println("out file = "+Constants.getRootMasterUsrPwdFile()+"/usrpwd.xlsx");
    	//String outNameFile = "khs_"+npm;
    	java.io.File outFile = new File(Constants.getTmpFile()+"/"+outNameFile+".xlsx");
    	if(file.exists()) {
    		try {
    		////System.out.println("excel ditemeukan");
    		InputStream inp = new FileInputStream(file);
    	    //InputStream inp = new FileInputStream("workbook.xlsx");
    	    Workbook wb = WorkbookFactory.create(inp);
    	    Sheet sheet = wb.getSheetAt(0);
    	    
    	    //col nmm - B3
    	   // //System.out.println("1");
    	    Row row = sheet.createRow(2);
    	    row = sheet.getRow(2);
    	    ////System.out.println("1a");
    	    //if(row == null) {
    	    //	sheet.createRow(2);
    	    //}
    	    Cell cell = row.getCell(1);
    	    ////System.out.println("1b");
    	    if (cell == null) {
    	    	 ////System.out.println("1c");
    	        cell = row.createCell(1);
    	        ////System.out.println("1d");
    	    }    
    	    ////System.out.println("1e");
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    ////System.out.println("1f");
    	    cell.setCellValue(Tool.capFirstLetterInWord(nmm));
    	    ////System.out.println("2");
    	    //usrname D10
    	    row = sheet.getRow(9);
    	    cell = row.getCell(3);
    	    if (cell == null) {
    	        cell = row.createCell(3);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(usr);
    	    ////System.out.println("3");
    	  //usrpwd D11
    	    row = sheet.getRow(10);
    	    cell = row.getCell(3);
    	    if (cell == null) {
    	        cell = row.createCell(3);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(pwd);
    	    ////System.out.println("4");
    	    
    	    // Write the output to a file
    	    FileOutputStream fileOut = new FileOutputStream(outFile);
    	    wb.write(fileOut);
    	    ////System.out.println("14");
    	    fileOut.close();
    	    ////System.out.println("15");
    		}
    		catch (Exception e) {
    			//System.out.println("poi err "+e);
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    	}
    	//System.out.println("done");
    }

    
    
    public void prepMasterIjazahMhs(String kdpst_mhs,Vector v_ija) {
    //public void prepMasterIjazahMhs(String kdpst,String nmmhs,String nirl) {
    	//prep ijazah MM
		ListIterator lija = v_ija.listIterator();
		String nomoija = "";
		String namaija = "";
		String tptglhrija = "";
		String nonirlija = "";
		String nimija = "";
		String gelarija = "";
		if(lija.hasNext()) {
			nomoija = (String)lija.next();
			namaija = (String)lija.next();
			tptglhrija = (String)lija.next();
			nonirlija = (String)lija.next();
			nimija = (String)lija.next();
			gelarija = (String)lija.next();	
			
			StringTokenizer st = new StringTokenizer(namaija.toUpperCase());
			String nmfile = "";
			while(st.hasMoreTokens()) {
				nmfile = nmfile+st.nextToken();
				if(st.hasMoreTokens()) {
					nmfile=nmfile+"_";
				}
			}
		
			if(kdpst_mhs.equalsIgnoreCase("61101")) {
				java.io.File file = new File(Constants.getRootFolderMasterIjazah()+"/"+kdpst_mhs+"/"+kdpst_mhs+".xlsx");
    		//java.io.File file = new File(Constants.getMasterKuiFile());
				//System.out.println("out file = "+Constants.getTmpFile()+"/ijazah_"+nmfile+".xlsx");
				java.io.File outFile = new File(Constants.getTmpFile()+"/ijazah_"+nmfile+".xlsx");
				if(file.exists()) {
					try {
						//System.out.println("excel ditemeukan");
						InputStream inp = new FileInputStream(file);
						//InputStream inp = new FileInputStream("workbook.xlsx");
						//System.out.println("1");
						Workbook wb = WorkbookFactory.create(inp);
						Sheet sheet = wb.getSheetAt(0);
						//System.out.println("2");
    				
						//1.col noija
						Row row = sheet.getRow(0);
						if(row == null) {
							row = sheet.createRow(0);
						}
						Cell cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(nomoija);
						
						//2.col nama
						row = sheet.getRow(11);
						if(row == null) {
							row = sheet.createRow(11);
						}
						cell = row.getCell(6);
						if (cell == null) {
							cell = row.createCell(6);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(namaija);
    				
						//3.col tptglhr
						row = sheet.getRow(12);
						if(row == null) {
							row = sheet.createRow(12);
						}
						cell = row.getCell(6);
						if (cell == null) {
							cell = row.createCell(6);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(tptglhrija);
    				
						//4.col nimhs
						row = sheet.getRow(13);
						if(row == null) {
							row = sheet.createRow(13);
						}
						cell = row.getCell(6);
						if (cell == null) {
							cell = row.createCell(6);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(nimija);
    				
						//5.col gelar
						row = sheet.getRow(14);
						if(row == null) {
							row = sheet.createRow(14);
						}
						cell = row.getCell(6);
						if (cell == null) {
							cell = row.createCell(6);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(gelarija);
						
						
						//6.col nirl
						row = sheet.getRow(23);
						if(row == null) {
							row = sheet.createRow(23);
						}
						cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(nonirlija);
    				
						//6.col tgl terbit /download
						String tglTerbit = AskSystem.getCurrentTimeInLongStringFormat();
						tglTerbit = Tool.capFirstLetterInWord(tglTerbit);
						row = sheet.getRow(24);
						if(row == null) {
							row = sheet.createRow(24);
						}
						cell = row.getCell(10);
						if (cell == null) {
							cell = row.createCell(10);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(tglTerbit);
    				
    				
    				
    				
						FileOutputStream fileOut = new FileOutputStream(outFile);
						wb.write(fileOut);
						//System.out.println("14");
						fileOut.close();
						//System.out.println("15");
					}
					catch (Exception e) {
						//System.out.println("poi err "+e);
					}
				}
				else {
					//System.out.println("excel missing");
				}
				//System.out.println("done");
			}	
		}
    }	
    
    public String prepMasterAbsensiKelas(String info_kelas, Vector vListMhs) {
    	//kelasInfo" value="<%=target_kmp+"`"+kdkmk+"`"+nakmk+"`"+nmmdos+"`"+shift+"`"+unique_id+"`"+idkmk+"`"+idkur+"`"+kdpst+"`"+noKlsPll %>" />
    	String out_file = "";
    	int norut_interval = 16; //tot mhs dalam 1 halaman
    	StringTokenizer st = new StringTokenizer(info_kelas,"`");
    	String target_kmp = st.nextToken();
    	String kdkmk = st.nextToken();
    	String nakmk = st.nextToken();
    	String nmmdos = st.nextToken();
    	String shift = st.nextToken();
    	String unique_id = st.nextToken();
    	String idkmk = st.nextToken();
    	String idkur = st.nextToken();
    	String kdpst = st.nextToken();
    	String noKlsPll = st.nextToken();
    	String target_thsms = st.nextToken();
    	String nama_fakultas = Converter.getNamaFakultas(kdpst);
    	String npm_nama_singkatanKtu = Getter.getDefaultNpmNamaBerdasarkanJabatan(target_thsms, kdpst, "KEPALA TATA USAHA");
    	String npm_nama_singkatanKabaa = Getter.getDefaultNpmNamaBerdasarkanJabatan(target_thsms, "00003", "KEPALA BIRO AKADEMIK");
    	String npm_nama_singkatanKabauk = Getter.getDefaultNpmNamaBerdasarkanJabatan(target_thsms, "00005", "KEPALA BIRO UMUM DAN KEUANGAN");
    	//System.out.println(npm_nama_singkatanKtu);
    	//System.out.println(npm_nama_singkatanKabaa);
    	//System.out.println(npm_nama_singkatanKabauk);
    	out_file=Constants.getTmpFile()+"/absen"+unique_id+".xlsx";
    	java.io.File file = new File(Getter.getMasterTampletAbsenPath());
    	//System.out.println("out file = "+out_file);
    	java.io.File outFile = new File(out_file);
    	if(file.exists()) {
    		try {
    			//System.out.println("excel ditemeukan");
        		InputStream inp = new FileInputStream(file);
        	    //InputStream inp = new FileInputStream("workbook.xlsx");
        		//System.out.println("1");
        	    Workbook wb = WorkbookFactory.create(inp);
        	    Sheet sheet = wb.getSheetAt(0);
        	    //System.out.println("2");
        	    boolean first = true;
        	    ListIterator li = vListMhs.listIterator();
        	    int one_page_row = 34;
        	    int hal = 0;
        	    int no = 1;
        	    int row_mhs = 0;
				do {
					String brs = (String)li.next();
					//li.add(nmmhs+"`"+npmhs+"`"+kdpst+"`"+smawl+"`"+status);
					st = new StringTokenizer(brs,"`");
					String nmmhs = st.nextToken();
					String npmhs = st.nextToken();
					String kdpst_mhs = st.nextToken();
					String smawl = st.nextToken();
					String thsms = st.nextToken();
					String status = st.nextToken();
					if(first) {
						first = false;
						//1.C5 nama makul
						Row row = sheet.getRow(4+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(4+(hal*one_page_row));
						}
						Cell cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(kdkmk+" - "+nakmk.toUpperCase());
						
						//P5 kelas pll
						row = sheet.getRow(4+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(4+(hal*one_page_row));
						}
						cell = row.getCell(15);
						if (cell == null) {
							cell = row.createCell(15);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(noKlsPll);
						
						
						//2.C6 nama dosen
						row = sheet.getRow(5+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(5+(hal*one_page_row));
						}
						cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(nmmdos.toUpperCase());
						//
						
						//3.P6 thsms
						row = sheet.getRow(5+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(5+(hal*one_page_row));
						}
						cell = row.getCell(15);
						if (cell == null) {
							cell = row.createCell(15);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(Converter.ubahKeformatTahunAkademik(target_thsms));
						
						//3.C7 prodi
						row = sheet.getRow(6+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(6+(hal*one_page_row));
						}
						cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(Converter.getNamaKdpst(kdpst));
						
						//P7 shift
						row = sheet.getRow(6+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(6+(hal*one_page_row));
						}
						cell = row.getCell(15);
						if (cell == null) {
							cell = row.createCell(15);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(shift.toUpperCase());
						
						
						
						//A11 no urut mhs pertama
						row_mhs = 10+(hal*one_page_row);
						row = sheet.getRow(row_mhs);
						if(row == null) {
							row = sheet.createRow(row_mhs);
						}
						cell = row.getCell(0);
						if (cell == null) {
							cell = row.createCell(0);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(no++);
						//B11 npm mhs pertama
						row = sheet.getRow(row_mhs);
						if(row == null) {
							row = sheet.createRow(row_mhs);
						}
						cell = row.getCell(1);
						if (cell == null) {
							cell = row.createCell(1);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(npmhs);
						//C11 nama mhs pertama
						row = sheet.getRow(row_mhs);
						if(row == null) {
							row = sheet.createRow(row_mhs);
						}
						cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(nmmhs.toUpperCase());
						row_mhs++;
						
						if(!li.hasNext()) {
							//cetak bagian bawah part
							//C29 nama ktu
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(0);
							if (cell == null) {
								cell = row.createCell(0);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKtu, 2, "`"));
							
							//cetak bagian bawah part
							//G29 nama kbaak
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(5);
							if (cell == null) {
								cell = row.createCell(5);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabaa, 2, "`"));
							
							//cetak bagian bawah part
							//L29 nama kbauk
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(14);
							if (cell == null) {
								cell = row.createCell(14);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabauk, 2, "`"));
						}
						
					}
					else {
						if(no % norut_interval == 0) {
							//A11 no urut mhs pertama
							
							Row row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							Cell cell = row.getCell(0);
							if (cell == null) {
								cell = row.createCell(0);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(no++);
							//B11 npm mhs pertama
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(1);
							if (cell == null) {
								cell = row.createCell(1);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(npmhs);
							//C11 nama mhs pertama
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(2);
							if (cell == null) {
								cell = row.createCell(2);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(nmmhs.toUpperCase());
							row_mhs++;
							
							//cetak bagian bawah part
							//C29 nama ktu
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(0);
							if (cell == null) {
								cell = row.createCell(0);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKtu, 2, "`"));
							
							//cetak bagian bawah part
							//G29 nama kbaak
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(5);
							if (cell == null) {
								cell = row.createCell(5);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabaa, 2, "`"));
							
							//cetak bagian bawah part
							//L29 nama kbauk
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(14);
							if (cell == null) {
								cell = row.createCell(14);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabauk, 2, "`"));
								
							
							
							//yg berikutnya lembar baru
							first = true;
							hal++;
						}
						else {
							Row row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							Cell cell = row.getCell(0);
							if (cell == null) {
								cell = row.createCell(0);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(no++);
							//B11 npm mhs pertama
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(1);
							if (cell == null) {
								cell = row.createCell(1);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(npmhs);
							//C11 nama mhs pertama
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(2);
							if (cell == null) {
								cell = row.createCell(2);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(nmmhs.toUpperCase());
							row_mhs++;
							
							if(!li.hasNext()) {
								//cetak bagian bawah part
								//C29 nama ktu
								row = sheet.getRow(29+(hal*one_page_row));
								if(row == null) {
									row = sheet.createRow(29+(hal*one_page_row));
								}
								cell = row.getCell(0);
								if (cell == null) {
									cell = row.createCell(0);
								}    
								cell.setCellType(Cell.CELL_TYPE_STRING);
								cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKtu, 2, "`"));
								
								//cetak bagian bawah part
								//G29 nama kbaak
								row = sheet.getRow(29+(hal*one_page_row));
								if(row == null) {
									row = sheet.createRow(29+(hal*one_page_row));
								}
								cell = row.getCell(5);
								if (cell == null) {
									cell = row.createCell(5);
								}    
								cell.setCellType(Cell.CELL_TYPE_STRING);
								cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabaa, 2, "`"));
								
								//cetak bagian bawah part
								//L29 nama kbauk
								row = sheet.getRow(29+(hal*one_page_row));
								if(row == null) {
									row = sheet.createRow(29+(hal*one_page_row));
								}
								cell = row.getCell(14);
								if (cell == null) {
									cell = row.createCell(14);
								}    
								cell.setCellType(Cell.CELL_TYPE_STRING);
								cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabauk, 2, "`"));
							}
						}
					}
				} while(li.hasNext());
        	    
        	  
				
				//A11 data mhs
				
				
        	    // Write the output to a file
        	    FileOutputStream fileOut = new FileOutputStream(outFile);
        	    wb.write(fileOut);
        	    //System.out.println("14");
        	    fileOut.close();
        	    //System.out.println("15");
    		}
    		catch (Exception e) {
    			//System.out.println("poi err "+e);
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    	}
    	//System.out.println("done");
    	return out_file;
    }
    
}
