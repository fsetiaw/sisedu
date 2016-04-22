package beans.tools;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;
import java.util.LinkedHashSet;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.owasp.esapi.ESAPI;
/**
 * Session Bean implementation class Tool
 */
@Stateless
@LocalBean
public class Tool {

    /**
     * Default constructor. 
     */
    public Tool() {
        // TODO Auto-generated constructor stub
    }
    
    public static boolean isThsmsEqualsSmawl(String thsms,String smawl) {
    	boolean sama = false;
    	//ubah jadi lima digit semua
    	if(thsms!=null && thsms.length()==6) {
    		thsms = thsms.substring(0,4)+thsms.substring(5,6);
    	}
    	if(smawl!=null && smawl.length()==6) {
    		smawl = smawl.substring(0,4)+smawl.substring(5,6);
    	}
    	if(thsms!=null && smawl!=null && thsms.equalsIgnoreCase(smawl)) {
    		sama = true;
    	}
    	return sama;
    }
    
    
    /*
     * masih error
     
    public static String removeRedundanPath(String url) {
    	StringTokenizer st = new StringTokenizer(url,"/");
    	String prev="";
    	String nuUrl="";
    	if(st.hasMoreTokens()) {
    		prev = st.nextToken();
    		nuUrl = ""+prev;
    		while(st.hasMoreTokens()) {
    			String current = st.nextToken();
    			if(!current.equalsIgnoreCase(prev)) {
    				nuUrl=nuUrl+"/"+current;
    				prev = ""+current;
    			}
    		}
    	}
    	//nuUrl=nuUrl.replace("//", "/");
    	return nuUrl;
    }
    */
    public static String getTokenKe(String target_string,int norut, String char_pemisah) {
    	String tmp = "";
    	StringTokenizer st = new StringTokenizer(target_string,char_pemisah);
    	for(int i=0;i<norut;i++) {
    		tmp=st.nextToken();
    	}
    	return tmp;
    }
    
    
	public static String sortListTkn(String brs) {
    	String tmp = "";
    	if(brs!=null && !Checker.isStringNullOrEmpty(brs)) {
    		String seperator = "`";
    		StringTokenizer st = null;
    		if(brs.contains(seperator)) {
    			st = new StringTokenizer(brs,seperator);
    		}
    		else {
    			seperator=",";
    			if(brs.contains(seperator)) {
        			st = new StringTokenizer(brs,seperator);
        		}
    			else {
    				seperator="-";
        			if(brs.contains(seperator)) {
            			st = new StringTokenizer(brs,seperator);
            		}	
    			}
    		}
    		
    		if(st.hasMoreTokens()) {
    			Vector v = new Vector();
        		ListIterator li = v.listIterator();
        		do {
        			String tkn = st.nextToken();	
        			li.add(tkn);
        		}
        		while(st.hasMoreTokens());
    			Collections.sort(v);
    			li = v.listIterator();
    			while(li.hasNext()) {
    				tmp = tmp +(String)li.next();
    				if(li.hasPrevious()) {
    					tmp = tmp + seperator;
    				}
    			}
    		}
    	}
    	return tmp;
    }
    
    public static String getTokenKe(String target_string,int norut) {
    	String tmp = "";
    	StringTokenizer st = new StringTokenizer(target_string);
    	for(int i=0;i<norut;i++) {
    		tmp=st.nextToken();
    	}
    	return tmp;
    }
    
    public static Vector removeDuplicateSdb_getMostRecentMsg_with_Sdb_getUnreadMsg(Vector vUnread, Vector vRecent) {
    	ListIterator liu=null, lir=null;
    	Vector vtmp = new Vector();
    	ListIterator litmp = vtmp.listIterator();
    	if(vUnread!=null && vRecent!=null) {
    		liu = vUnread.listIterator();
    		
    		while(liu.hasNext()) {
    			boolean match = false;
    			String unread = (String) liu.next();
    			//System.out.println("unread="+unread);
    			StringTokenizer st = new StringTokenizer(unread,"||");
    			String topik_idTopik = st.nextToken();
    			String topik_conten = st.nextToken();
    			String topik_npmhsCreator = st.nextToken();
    			String topik_nmmhsCreator = st.nextToken();
    			String topik_creatorObjId = st.nextToken();
    			String topik_creatorObjNickname = st.nextToken();
    			String topik_targetKdpst = st.nextToken();
    			String topik_targetNpmhs = st.nextToken();
    			String topik_targetSmawl = st.nextToken();
    			String topik_targetObjId = st.nextToken();
    			String topik_targetObjNickname = st.nextToken();
    			String topik_targetGroupId = st.nextToken();
    			String topik_groupPwd = st.nextToken();
    			String topik_shownToGroupOnly = st.nextToken();
    			String topik_deletedByCreator = st.nextToken();
    			String topik_hidenAtCreator = st.nextToken();
    			String topik_pinedAtCreator = st.nextToken();
    			String topik_markedAsReadAtCreator = st.nextToken();
    			String topik_deletedAtTarget = st.nextToken();
    			String topik_hidenAtTarget = st.nextToken();
    			String topik_pinedAtTarget = st.nextToken();
    			String topik_markedAsReasAsTarget = st.nextToken();
    			String topik_creatorAsAnonymous = st.nextToken();
    			String topik_creatorIsPetugas = st.nextToken();
    			String topik_updtm = st.nextToken();
    			String sub_id = st.nextToken();
    			String sub_idTopik = st.nextToken();
    			String sub_comment = st.nextToken();
    			String sub_npmhsSender = st.nextToken();
    			String sub_nmmhsSender = st.nextToken();
    			String sub_anonymousReply = st.nextToken();
    			String sub_shownToCreatorObly = st.nextToken();
    			String sub_commenterIsPetugas = st.nextToken();
    			String sub_markedAsReadAtCreator = st.nextToken();
    			String sub_markedAsReadAtSender = st.nextToken();
    			String sub_objNicknameSender = st.nextToken();
    			String sub_npmhsReceiver = st.nextToken();
    			String sub_nmmhsReceiver = st.nextToken();
    			String sub_objNicknameReceiver = st.nextToken();
    			String sub_updtm = st.nextToken();
    			lir = vRecent.listIterator();
    			while(lir.hasNext() && !match) {
    				String recent = (String) lir.next();
       				st = new StringTokenizer(recent,"||");
       				String topik_idTopik1 = st.nextToken();
       				String topik_conten1 = st.nextToken();
      				String topik_npmhsCreator1 = st.nextToken();
       				String topik_nmmhsCreator1 = st.nextToken();
       				String topik_creatorObjId1 = st.nextToken();
       				String topik_creatorObjNickname1 = st.nextToken();
       				String topik_targetKdpst1 = st.nextToken();
       				String topik_targetNpmhs1 = st.nextToken();
       				String topik_targetSmawl1 = st.nextToken();
       				String topik_targetObjId1 = st.nextToken();
       				String topik_targetObjNickname1 = st.nextToken();
       				String topik_targetGroupId1 = st.nextToken();
       				String topik_groupPwd1 = st.nextToken();
       				String topik_shownToGroupOnly1 = st.nextToken();
       				String topik_deletedByCreator1 = st.nextToken();
       				String topik_hidenAtCreator1 = st.nextToken();
       				String topik_pinedAtCreator1 = st.nextToken();
       				String topik_markedAsReadAtCreator1 = st.nextToken();
       				String topik_deletedAtTarget1 = st.nextToken();
       				String topik_hidenAtTarget1 = st.nextToken();
       				String topik_pinedAtTarget1 = st.nextToken();
       				String topik_markedAsReasAsTarget1 = st.nextToken();
       				String topik_creatorAsAnonymous1 = st.nextToken();
       				String topik_creatorIsPetugas1 = st.nextToken();
       				String topik_updtm1 = st.nextToken();
       				String sub_id1 = st.nextToken();
       				String sub_idTopik1 = st.nextToken();
       				String sub_comment1 = st.nextToken();
       				String sub_npmhsSender1 = st.nextToken();
       				String sub_nmmhsSender1 = st.nextToken();
       				String sub_anonymousReply1 = st.nextToken();
       				String sub_shownToCreatorObly1 = st.nextToken();
       				String sub_commenterIsPetugas1 = st.nextToken();
       				String sub_markedAsReadAtCreator1 = st.nextToken();
       				String sub_markedAsReadAtSender1 = st.nextToken();
       				String sub_objNicknameSender1 = st.nextToken();
       				String sub_npmhsReceiver1 = st.nextToken();
       				String sub_nmmhsReceiver1 = st.nextToken();
       				String sub_objNicknameReceiver1 = st.nextToken();
       				String sub_updtm1 = st.nextToken();
       				if((topik_idTopik.equalsIgnoreCase(topik_idTopik1) && sub_id.equalsIgnoreCase(sub_id1)) || (sub_id.equalsIgnoreCase(sub_id1) && !sub_id.equalsIgnoreCase("null") && !sub_id.equalsIgnoreCase("0"))  ) {
       				//if(topik_idTopik.equalsIgnoreCase(topik_idTopik1) || sub_id.equalsIgnoreCase(sub_id1)) {
       					//System.out.println("match topik_idTopik="+topik_idTopik+",topik_idTopik1="+topik_idTopik1+",sub_id="+sub_id+",sub_id1="+sub_id1);
       					match = true;
       					lir.remove();
       				}
       				
   				}
    			
    			//kalo untread ngga ada match maka artinya belum pernah dapat balasan jadi harus ditambah ke lir - krn lir hanya ada dari SUBTOPIK
    			//if(!match) {
    			//	litmp.add(unread);
    			//}
    		}
    	}
    	//litmp = vtmp.listIterator();
		//while(litmp.hasNext()) {
		//	String recent = (String) litmp.next();
		//	System.out.println("lier="+recent);
		//}	
    	return vRecent;			
    }
    
    public static String capFirstLetterInWord(String tkn) {
    	String upd = "";
    	if(tkn!=null) {
    		StringTokenizer st = new StringTokenizer(tkn);
    		while(st.hasMoreTokens()) {
    			String tmp = st.nextToken();
    			String first_letter = tmp.substring(0,1);
    			first_letter = first_letter.toUpperCase();
    			String the_rest = tmp.substring(1,tmp.length());
    			the_rest=the_rest.toLowerCase();
    			upd = upd+first_letter+the_rest;
    			if(st.hasMoreTokens()) {
    				upd = upd+" ";
    			}
    		}
    	}
    	return upd;
    }
    
	public static Vector returnTokensListThsms(String thsms1, String thhsms2, String basedThsms) {
		/*
		 * fungsi ini menghasilkan list berurutan dari thsms terendah sampai terkini ; baris pertama based thsms
		 * input; urutan thsms1&2 boleh dibolak balik
		 * basedThssms = mungkin isi kaya N/A ??
		 */
    	String listThsms="";
    	String starting =null,ending=null;
		if(thsms1.compareToIgnoreCase(thhsms2)==0) {
			starting = ""+thsms1;
			ending = ""+thsms1;
		}
		else {
			if(thsms1.compareToIgnoreCase(thhsms2)<0) {
				starting = ""+thsms1;
				ending = ""+thhsms2;
			}
			else {
				starting = ""+thhsms2;
				ending = ""+thsms1;
			}
		}
		if(starting.equalsIgnoreCase(ending)) {
			//listThsms="starting";
			listThsms=starting;
		}
		else {
			listThsms = listThsms+ending+",";
			while(!ending.equalsIgnoreCase(starting)) {
				ending = returnPrevThsmsGiven(ending);
				listThsms=listThsms+ending;
				if(!ending.equalsIgnoreCase(starting)) {
					listThsms=listThsms+",";
				}
				//System.out.println("listThsms="+listThsms);
			}
			//listThsms=listThsms+","+ending;
		}
		 
		StringTokenizer st = new StringTokenizer(listThsms,",");
		Vector thsmsList = new Vector();
		ListIterator li = 	thsmsList.listIterator();
		
		while(st.hasMoreTokens()) {
			li.add(st.nextToken());
		}
		Collections.sort(thsmsList);
		li = 	thsmsList.listIterator();
		li.add(basedThsms);
		return thsmsList;
    }
	
	public static Vector returnTokensListThsmsTpAntara(String thsms1, String thhsms2, String basedThsms) {
		/*
		 * fungsi ini menghasilkan list berurutan dari thsms terendah sampai terkini ; baris pertama based thsms
		 * input; urutan thsms1&2 boleh dibolak balik
		 * basedThssms = mungkin isi kaya N/A ??
		 */
    	String listThsms="";
    	String starting =null,ending=null;
		if(thsms1.compareToIgnoreCase(thhsms2)==0) {
			starting = ""+thsms1;
			ending = ""+thsms1;
		}
		else {
			if(thsms1.compareToIgnoreCase(thhsms2)<0) {
				starting = ""+thsms1;
				ending = ""+thhsms2;
			}
			else {
				starting = ""+thhsms2;
				ending = ""+thsms1;
			}
		}
		if(starting.equalsIgnoreCase(ending)) {
			//listThsms="starting";
			listThsms=starting;
		}
		else {
			listThsms = listThsms+ending+",";
			while(!ending.equalsIgnoreCase(starting)) {
				ending = returnPrevThsmsGivenTpAntara(ending);
				listThsms=listThsms+ending;
				if(!ending.equalsIgnoreCase(starting)) {
					listThsms=listThsms+",";
				}
				//System.out.println("listThsms="+listThsms);
			}
			//listThsms=listThsms+","+ending;
		}
		 
		StringTokenizer st = new StringTokenizer(listThsms,",");
		Vector thsmsList = new Vector();
		ListIterator li = 	thsmsList.listIterator();
		
		while(st.hasMoreTokens()) {
			li.add(st.nextToken());
		}
		Collections.sort(thsmsList);
		li = 	thsmsList.listIterator();
		li.add(basedThsms);
		return thsmsList;
    }
	
	public static Vector returnTokensListThsms(String thsms1, String thhsms2) {
		/*
		 * fungsi ini menghasilkan list berurutan dari thsms terendah sampai terkini ; baris pertama based thsms
		 * input; urutan thsms1&2 boleh dibolak balik
		 */
    	String listThsms="";
    	String starting =null,ending=null;
		if(thsms1.compareToIgnoreCase(thhsms2)==0) {
			starting = ""+thsms1;
			ending = ""+thsms1;
		}
		else {
			if(thsms1.compareToIgnoreCase(thhsms2)<0) {
				starting = ""+thsms1;
				ending = ""+thhsms2;
			}
			else {
				starting = ""+thhsms2;
				ending = ""+thsms1;
			}
		}
		if(starting.equalsIgnoreCase(ending)) {
			listThsms="starting";
		}
		else {
			listThsms = listThsms+ending+",";
			while(!ending.equalsIgnoreCase(starting)) {
				ending = returnPrevThsmsGiven(ending);
				listThsms=listThsms+ending;
				if(!ending.equalsIgnoreCase(starting)) {
					listThsms=listThsms+",";
				}
			}
		}
		 
		StringTokenizer st = new StringTokenizer(listThsms,",");
		Vector thsmsList = new Vector();
		ListIterator li = 	thsmsList.listIterator();
		
		while(st.hasMoreTokens()) {
			li.add(st.nextToken());
		}
		Collections.sort(thsmsList);
//		li = 	thsmsList.listIterator();
//		li.add(basedThsms);
		return thsmsList;
    }
	
	public static Vector returnTokensListThsmsTpAntara(String thsms1, String thhsms2) {
		/*
		 * fungsi ini menghasilkan list berurutan dari thsms terendah sampai terkini ; baris pertama based thsms
		 * input; urutan thsms1&2 boleh dibolak balik
		 */
    	String listThsms="";
    	String starting =null,ending=null;
		if(thsms1.compareToIgnoreCase(thhsms2)==0) {
			starting = ""+thsms1;
			ending = ""+thsms1;
		}
		else {
			if(thsms1.compareToIgnoreCase(thhsms2)<0) {
				starting = ""+thsms1;
				ending = ""+thhsms2;
			}
			else {
				starting = ""+thhsms2;
				ending = ""+thsms1;
			}
		}
		if(starting.equalsIgnoreCase(ending)) {
			listThsms="starting";
		}
		else {
			listThsms = listThsms+ending+",";
			while(!ending.equalsIgnoreCase(starting)) {
				ending = returnPrevThsmsGivenTpAntara(ending);
				listThsms=listThsms+ending;
				if(!ending.equalsIgnoreCase(starting)) {
					listThsms=listThsms+",";
				}
			}
		}
		 
		StringTokenizer st = new StringTokenizer(listThsms,",");
		Vector thsmsList = new Vector();
		ListIterator li = 	thsmsList.listIterator();
		
		while(st.hasMoreTokens()) {
			li.add(st.nextToken());
		}
		Collections.sort(thsmsList);
//		li = 	thsmsList.listIterator();
//		li.add(basedThsms);
		return thsmsList;
    }
	
	
    /*depricated
    public static String returnPrevThsmsGiven(String smawl) {
		String tahun = smawl.substring(0,4);
		String sms = smawl.substring(4,5);
		String thsms = "";
		if(sms.equalsIgnoreCase("1")) {
			thsms = (Long.valueOf(tahun).longValue()-1)+"2";
		} else {
			thsms = tahun+"1";
		}
		return thsms;
	}
    */
	public static String returnPrevThsmsGiven(String smawl) {
		
		String tahun = smawl.substring(0,4);
		String sms = smawl.substring(4,smawl.length());
		String thsms = "";
		if(sms.equalsIgnoreCase("1")) {
			thsms = (Long.valueOf(tahun).longValue()-1)+"2B";
		} else {
			if(sms.equalsIgnoreCase("2B")) {
				thsms = tahun+"2";
			}
			else {
				if(sms.equalsIgnoreCase("2")) {
					thsms = tahun+"1A";
				}
				else {
					if(sms.equalsIgnoreCase("1A")) {
						thsms = tahun+"1";
					}
				}
			}
		}
		return thsms;
	}
	
	public static String returnPrevThsmsGivenTpAntara(String smawl) {
		
		String tahun = smawl.substring(0,4);
		String sms = smawl.substring(4,smawl.length());
		String thsms = "";
		if(sms.equalsIgnoreCase("1")) {
			thsms = (Long.valueOf(tahun).longValue()-1)+"2";
		}
		else {
			if(sms.equalsIgnoreCase("2")) {
				thsms = tahun+"1";
			}
		}
		return thsms;
	}
	/*depricated
    public static String returnNextThsmsGiven(String smawl) {
		String tahun = smawl.substring(0,4);
		String sms = smawl.substring(4,5);
		String thsms = "";
		if(sms.equalsIgnoreCase("1")) {
			thsms = tahun+"2";
		} else {
			thsms = (Long.valueOf(tahun).longValue()+1)+"1";
		}
		return thsms;
	}
    */
	
    public static String returnNextThsmsGiven(String smawl) {
		String tahun = smawl.substring(0,4);
		String sms = smawl.substring(4,smawl.length());
		String thsms = "";
		if(sms.equalsIgnoreCase("1")) {
			thsms = tahun+"1A";
		} else {
			if(sms.equalsIgnoreCase("1A")) {
				thsms = tahun+"2";
			} else {
				if(sms.equalsIgnoreCase("2")) {
					thsms = tahun+"2B";
				} else {
					if(sms.equalsIgnoreCase("2B")) {
						thsms = (Long.valueOf(tahun).longValue()+1)+"1";
					}
				}
			}
		}
		return thsms;
	}
    
    public static String returnNextThsmsGivenTpAntara(String smawl) {
		String tahun = smawl.substring(0,4);
		String sms = smawl.substring(4,smawl.length());
		String thsms = "";
		if(sms.equalsIgnoreCase("1")) {
			thsms = tahun+"2";
		} else {
			if(sms.equalsIgnoreCase("2")) {
				thsms = (Long.valueOf(tahun).longValue()+1)+"1";
			}
		}
		return thsms;
	}
    
    
    public static Vector removeDuplicateFromVector(Vector v)throws Exception 
	{
		Vector v1 = new Vector(v);
		ListIterator li = v.listIterator();
		while(li.hasNext())
		{
			String baris = (String)li.next();
			//System.out.println("v "+baris);
		}	
	

		v1 = new Vector(new LinkedHashSet(v));
		ListIterator li1 = v1.listIterator();
		while(li1.hasNext())
		{
			String baris = (String)li1.next();
			//System.out.println("v1 "+baris);
		}	
		return v1;
	}

    
    public static int getTotalSoalUjian(String tokenKodeGroupAndListSoal) {
    	StringTokenizer st = null;
    	int soaltt = 0;
    	if(tokenKodeGroupAndListSoal!=null) {
    		if(tokenKodeGroupAndListSoal.contains("#")) {
    			st = new StringTokenizer(tokenKodeGroupAndListSoal,"#");
    		}
    		else {
    			if(tokenKodeGroupAndListSoal.contains("$$")) {
    				st = new StringTokenizer(tokenKodeGroupAndListSoal,"$$");
    			}
    		}
    		if(st!=null && st.countTokens()>0) {
    			while(st.hasMoreTokens()) {
    				String listSoal = (String)st.nextToken();
    				StringTokenizer st1 = new StringTokenizer(listSoal,",");
    				String kodeGroupIgnore = st1.nextToken();
    				while(st1.hasMoreTokens()) {
    					soaltt++;
    					String soalid = st1.nextToken();
    					String nosoal = st1.nextToken();
    				}
    			}
    		}
    	}
    	return soaltt;
    }
    
    public static String createTokenIdSoalAtChapterSesuaiNorutSoalForNavigasiIdSoalInMiddlePos(String tokenKodeGroupAndListSoal,String idSoal) {
    	String tkn = "";
    	String tknBeforMatched = "";
    	//if(Checker.isStringNullOrEmpty(idSoal)) {
    	//System.out.println("ttool="+tokenKodeGroupAndListSoal);
    	//System.out.println("idsoal="+idSoal);
    	String tmp = createTokenIdSoalAtChapterSesuaiNorutSoal(tokenKodeGroupAndListSoal);
    	//System.out.println("tmp1-"+tmp);
    	int norutNavigasiCounter = 0;
    	int counterBeforeMatched = 0;
    	boolean match = false;
    	int norutNavigasi = 0;
    	if(!Checker.isStringNullOrEmpty(idSoal)) {
    		//cek apakah id target idSoal ada di norut 1
    		//boolean startWith1 = false;
    		boolean first = true;
    		StringTokenizer st = new StringTokenizer(tmp,",");
    		String tmp_idSoal = null;
    		String tmp_atChapter = null;
    		boolean idsoalMatched = false;
    		int totalSoalUjian =  getTotalSoalUjian(tokenKodeGroupAndListSoal);
    		//if(totalSoalUjian>15) {
    		if(true) { // ngga ada bedanya antara total soal > 15
    			while(st.hasMoreTokens()&&norutNavigasiCounter<15) {
    				tmp_idSoal = st.nextToken();
					tmp_atChapter = st.nextToken();
					norutNavigasi++;
    				if(first) {
    					first =false;
    					//tmp_idSoal = st.nextToken();
    					//tmp_atChapter = st.nextToken();
    					if(tmp_idSoal.equalsIgnoreCase(idSoal)) {
            			//jika match at token #1
    						//norutNavigasi=1;
    						norutNavigasiCounter++;
    						idsoalMatched=true;
    						tkn = tkn+norutNavigasi+","+tmp_idSoal+","+tmp_atChapter+",";
    					}
    					else {
    						//norutNavigasi++;
    						counterBeforeMatched++;
    						tknBeforMatched=tknBeforMatched+norutNavigasi+","+tmp_idSoal+","+tmp_atChapter+",";
    					}
    				}
    				else {
    				//	norutNavigasi++;
    				//	counter++;
    					//tmp_idSoal = st.nextToken();
    					//tmp_atChapter = st.nextToken();
    					if(idsoalMatched) {
    					//jika sdh mathced
    						//norutNavigasi++;
    						norutNavigasiCounter++;
    						if(norutNavigasiCounter<=15) {
    							tkn = tkn+norutNavigasi+","+tmp_idSoal+","+tmp_atChapter+",";
    						}
    					}
    					else {
    					//matched tidak di token pertama
    						//tmp_idSoal = st.nextToken();
    						//tmp_atChapter = st.nextToken();
    						if(tmp_idSoal.equalsIgnoreCase(idSoal)) {
    							idsoalMatched = true;
    							norutNavigasiCounter++;//tambahan
    							//norutNavigasi++;
    							//System.out.println("match at "+counterBeforeMatched);
    							if(counterBeforeMatched<7) {
    								norutNavigasiCounter = norutNavigasiCounter+counterBeforeMatched;
    								//System.out.println("norutNavigasiCounter at "+norutNavigasiCounter);
    								//norutNavigasi = counterBeforeMatched++;
    								tkn = tknBeforMatched+norutNavigasi+","+tmp_idSoal+","+tmp_atChapter+",";
    							}
    							else {
    								//int sisa = totalSoalUjian - counterBeforeMatched;
    								int j = 0;
    								int atNoNav = counterBeforeMatched-5+15;
    								if(atNoNav<totalSoalUjian) {
    									j=5;
    									//norutNavigasiCounter=counterBeforeMatched-j;
    									//j=counterBeforeMatched-(counterBeforeMatched-5);
    									
    								}
    								else {
    									int sisa = totalSoalUjian - counterBeforeMatched;
    									j=15-sisa;
    									//norutNavigasiCounter=counterBeforeMatched-j;
    								}
    								//norutNavigasiCounter = j;
    								//if(sisa<=6) {
    								
    								//	j=15-sisa;
    								//}
    								//else {
    								//	j=7;
    								//}	
    								//if(sisa<=15) {
    								//System.out.println("j="+j);
    								String tknBeforMatchedReversed = reverseOrder3Tkn(tknBeforMatched);
    								StringTokenizer st1 = new StringTokenizer(tknBeforMatchedReversed,",");
    								String tmp1 = "";
    								//for(j=15-sisa;j>0;j--) {
    								norutNavigasiCounter=0;
    								for(int k=0;k<j;k++) {
    									norutNavigasiCounter++;
    									String norutNavigasi1=st1.nextToken();
    									String tmp_idSoal1=st1.nextToken();
    									String tmp_atChapter1=st1.nextToken();
    									tmp1 = tmp1+norutNavigasi1+","+tmp_idSoal1+","+tmp_atChapter1+",";
    								}
    								tknBeforMatched = reverseOrder3Tkn(tmp1);
    								tkn = tknBeforMatched+norutNavigasi+","+tmp_idSoal+","+tmp_atChapter+",";
    								norutNavigasiCounter++;
    								 
    							}
    						}
    						else {
    							counterBeforeMatched++;
    							tknBeforMatched=tknBeforMatched+norutNavigasi+","+tmp_idSoal+","+tmp_atChapter+",";
    						}
    					}
    				}	
    			}
    		}
    		else {
    			//if total ujian <15
    		}
    	}
    	else {
    		System.out.println("idsoal null");
    	}
    	
    	return tkn;
	}
    
    public static String reverseOrder3Tkn(String tokenOfThreeSeperatedByKoma) {
    	String reverse="";
    	StringTokenizer st = new StringTokenizer(tokenOfThreeSeperatedByKoma,",");
    	while(st.hasMoreTokens()) {
    		String first = st.nextToken();
    		String second = st.nextToken();
    		String third = st.nextToken();
    		reverse = first+","+second+","+third+","+reverse;
    	}	
    	return reverse;
    }
    
    public static String createTokenIdSoalAtChapterSesuaiNorutSoal(String tokenKodeGroupAndListSoal) {
    	String tkn="";
    	StringTokenizer st = null;
    	if(tokenKodeGroupAndListSoal!=null) {
    		if(tokenKodeGroupAndListSoal.contains("#")) {
    			st = new StringTokenizer(tokenKodeGroupAndListSoal,"#");
    		}
    		else {
    			if(tokenKodeGroupAndListSoal.contains("$$")) {
    				//tokenKodeGroupAndListSoal=tokenKodeGroupAndListSoal.substring(0,tokenKodeGroupAndListSoal.length()-2);
    				st = new StringTokenizer(tokenKodeGroupAndListSoal,"$$");
    			}
    		}
    		 
    		if(st!=null && st.countTokens()>0) {
    			int atChapter = 0;
    			while(st.hasMoreTokens()) {
    				atChapter++;
    				String tokens = (String)st.nextToken();
    				StringTokenizer st1 = new StringTokenizer(tokens,",");
    				String bagianIgnore = st1.nextToken();
    				while(st1.hasMoreTokens()) {
    					String idSoal = st1.nextToken();
    					String norutIgnore = st1.nextToken();
    					tkn=tkn+idSoal+","+atChapter+",";
    				}
    			}
    		}	
    	}
    	return tkn;
    }
    
    public static int getTotalSoalUjianAtBag(String tokenKodeGroupAndListSoal,String atChapter) {
    	//System.out.println("=="+tokenKodeGroupAndListSoal+"=="+atChapter);
    	StringTokenizer st = null;
    	int soaltt = 0;
    	if(tokenKodeGroupAndListSoal!=null) {
    		if(tokenKodeGroupAndListSoal.contains("#")) {
    			st = new StringTokenizer(tokenKodeGroupAndListSoal,"#");
    		}
    		else {
    			if(tokenKodeGroupAndListSoal.contains("$$")) {
    				//tokenKodeGroupAndListSoal=tokenKodeGroupAndListSoal.substring(0,tokenKodeGroupAndListSoal.length()-2);
    				st = new StringTokenizer(tokenKodeGroupAndListSoal,"$$");
    			}
    		}
    		boolean match = false;
    		if(st!=null && st.countTokens()>0) {
    			int tkntt=st.countTokens();
    			//System.out.println("countTokens="+st.countTokens());
    			for(int i=1;i<=tkntt;i++) {
    				String listSoal = (String)st.nextToken();
    				//System.out.println(i+".listSoal="+listSoal);
    				if(atChapter.equalsIgnoreCase(""+i)) {
    					match = true;
    					//System.out.println("match="+match);
    					
    					StringTokenizer st1 = new StringTokenizer(listSoal,",");
    					String kodeGroupIgnore = st1.nextToken();
    					while(st1.hasMoreTokens()) {
    						soaltt++;
    						String soalid = st1.nextToken();
    						String nosoal = st1.nextToken();
    					}
    				}
    				
    			}
    		}
    	}
    	return soaltt;
    }
    
    public static String gotoDataPrevSoal(String tokenKodeGroupAndListSoal,String idSoal) {
    	String tkn="";
    	//System.out.println("prev="+tokenKodeGroupAndListSoal+">"+idSoal);
    	StringTokenizer st = null;
    	//int soaltt = 0;
    	if(tokenKodeGroupAndListSoal!=null) {
    		if(tokenKodeGroupAndListSoal.contains("#")) {
    			st = new StringTokenizer(tokenKodeGroupAndListSoal,"#");
    		}
    		else {
    			if(tokenKodeGroupAndListSoal.contains("$$")) {
    				//tokenKodeGroupAndListSoal=tokenKodeGroupAndListSoal.substring(0,tokenKodeGroupAndListSoal.length()-2);
    				st = new StringTokenizer(tokenKodeGroupAndListSoal,"$$");
    			}
    		}
    	}	
    	String infoPrevSoal="";
    	boolean match = false;
    	if(st!=null && st.countTokens()>0) {
			int atChapter = 0;
			String tmp_idSoal="";
			while(st.hasMoreTokens()&&!match) {
				atChapter++;
				String tokens = (String)st.nextToken();
				StringTokenizer st1 = new StringTokenizer(tokens,",");
				String bagianIgnore = st1.nextToken();
				while(st1.hasMoreTokens()&&!match) {
					tmp_idSoal = st1.nextToken();
					String tmp_norutIgnore = st1.nextToken();
					if(tmp_idSoal.equalsIgnoreCase(idSoal)) {
						match=true;
					}
					if(!match) {
						infoPrevSoal = tmp_idSoal+","+atChapter;
					}
				}
				
			}
		}
//    	System.out.println("infoPrevSoal="+infoPrevSoal);
    	return infoPrevSoal;
    }
    
    public static String removeWhiteSpace(String brs) {
    	StringTokenizer st = new StringTokenizer(brs);
    	brs = "";
    	while(st.hasMoreTokens()) {
    		brs=brs+st.nextToken();
    	}
    	return brs;
    	
    }
    
    public static String gantiSpecialChar(String baris) {
    	//filter sebelum  stringtokenizer dengan pembatas "__"
    	String tmp = null;
    	tmp = baris.replace("_","tandaGarisBawah");
    	tmp = tmp.replace("\"","tandaKutipGanda");
    	tmp = tmp.replace("&", "tandaDan");
    	tmp = tmp.replace("#","tandaPagar");
    	return tmp;
    }

    public static String kembaliSpecialChar(String baris) {
    	//filter sebelum  stringtokenizer dengan pembatas "__"
    	String tmp = null;
    	tmp = baris.replace("tandaGarisBawah","_");
    	tmp = tmp.replace("tandaKutipGanda","\"");
    	tmp = tmp.replace("tandaDan","&");
    	tmp = tmp.replace("tandaPagar","#");
    	return tmp;
    }
    /*    
    public static String gotoDataNextSoal(String tokenKodeGroupAndListSoal,String idSoal) {
    	String tkn="";
    	System.out.println("next="+tokenKodeGroupAndListSoal+">"+idSoal);
    	StringTokenizer st = null;
    	//int soaltt = 0;
    	if(tokenKodeGroupAndListSoal!=null) {
    		if(tokenKodeGroupAndListSoal.contains("#")) {
    			st = new StringTokenizer(tokenKodeGroupAndListSoal,"#");
    		}
    		else {
    			if(tokenKodeGroupAndListSoal.contains("$$")) {
    				//tokenKodeGroupAndListSoal=tokenKodeGroupAndListSoal.substring(0,tokenKodeGroupAndListSoal.length()-2);
    				st = new StringTokenizer(tokenKodeGroupAndListSoal,"$$");
    			}
    		}
    	}	
    	String infoNextSoal="";
    	boolean match = false;
    	boolean afterMatch = false;
    	if(st!=null && st.countTokens()>0) {
			int atChapter = 0;
			String tmp_idSoal="";
			while(st.hasMoreTokens()&&!match) {
				atChapter++;
				String tokens = (String)st.nextToken();
				StringTokenizer st1 = new StringTokenizer(tokens,",");
				String bagianIgnore = st1.nextToken();
				while(st1.hasMoreTokens()&&!match) {
					tmp_idSoal = st1.nextToken();
					String tmp_norutIgnore = st1.nextToken();
					if(tmp_idSoal.equalsIgnoreCase(idSoal)) {
						match=true;
					}
					if(match && st1.hasMoreTokens()) {
						tmp_idSoal = st1.nextToken();
						tmp_norutIgnore = st1.nextToken();
						infoNextSoal = tmp_idSoal+","+atChapter;
					}
					else {
						if(match && !st1.hasMoreTokens()) {
							tokens = (String)st.nextToken();
							st1 = new StringTokenizer(tokens,",");
							bagianIgnore = st1.nextToken();
							while(st1.hasMoreTokens()&&match) {
								tmp_idSoal = st1.nextToken();
								tmp_norutIgnore = st1.nextToken();
								infoNextSoal = tmp_idSoal+","+atChapter;
							}	
						}
					}
				}
			}
		}
    	System.out.println("infoNextSoal="+infoNextSoal);
    	return infoNextSoal;
    }
*/
    /*
     * depricated = gunakan yg di beans ToolSoalUjian

    public static String gotoDataNextSoal(String tokenKodeGroupAndListSoal,String idSoal) {
    	String tkn="";
    	System.out.println("next="+tokenKodeGroupAndListSoal+">"+idSoal);
    	StringTokenizer st = null;
    	//int soaltt = 0;
    	if(tokenKodeGroupAndListSoal!=null) {
    		if(tokenKodeGroupAndListSoal.contains("#")) {
    			st = new StringTokenizer(tokenKodeGroupAndListSoal,"#");
    		}
    		else {
    			if(tokenKodeGroupAndListSoal.contains("$$")) {
    				//tokenKodeGroupAndListSoal=tokenKodeGroupAndListSoal.substring(0,tokenKodeGroupAndListSoal.length()-2);
    				st = new StringTokenizer(tokenKodeGroupAndListSoal,"$$");
    			}
    		}
    	}	
    	String infoNextSoal="";
    	boolean match = false;
    	boolean afterMatch = false;
    	if(st!=null && st.countTokens()>0) {
			int atChapter = 0;
			String tmp_idSoal="";
			while(st.hasMoreTokens()&&!match) {
				atChapter++;
				String tokens = (String)st.nextToken();
				StringTokenizer st1 = new StringTokenizer(tokens,",");
				String bagianIgnore = st1.nextToken();
				while(st1.hasMoreTokens()&&!match) {
					tmp_idSoal = st1.nextToken();
					String tmp_norutIgnore = st1.nextToken();
					if(tmp_idSoal.equalsIgnoreCase(idSoal)) {
						match=true;
					}
					if(match && st1.hasMoreTokens()) {
						tmp_idSoal = st1.nextToken();
						tmp_norutIgnore = st1.nextToken();
						infoNextSoal = tmp_idSoal+","+atChapter;
					}
					else {
						if(match && !st1.hasMoreTokens()) {
							atChapter++;
							tokens = (String)st.nextToken();
							st1 = new StringTokenizer(tokens,",");
							bagianIgnore = st1.nextToken();
							//st1.hasMoreTokens()&&match) {
							tmp_idSoal = st1.nextToken();
							tmp_norutIgnore = st1.nextToken();
							infoNextSoal = tmp_idSoal+","+atChapter;
							//}	
						}
					}
				}
			}
			if(!match) {
				//kasus kalo dah di akhir soal maka balik ke no1
				if(tokenKodeGroupAndListSoal!=null) {
		    		if(tokenKodeGroupAndListSoal.contains("#")) {
		    			st = new StringTokenizer(tokenKodeGroupAndListSoal,"#");
		    		}
		    		else {
		    			if(tokenKodeGroupAndListSoal.contains("$$")) {
		    				//tokenKodeGroupAndListSoal=tokenKodeGroupAndListSoal.substring(0,tokenKodeGroupAndListSoal.length()-2);
		    				st = new StringTokenizer(tokenKodeGroupAndListSoal,"$$");
		    			}
		    		}
		    	}	
				atChapter = 0;
				if(st.hasMoreTokens()) {
					atChapter++;
					String tokens = (String)st.nextToken();
					StringTokenizer st1 = new StringTokenizer(tokens,",");
					String bagianIgnore = st1.nextToken();
					tmp_idSoal = st1.nextToken();
					String tmp_norutIgnore = st1.nextToken();
					infoNextSoal = tmp_idSoal+","+atChapter;
				}
			}
		}
    	System.out.println("infoNextSoal="+infoNextSoal);
    	return infoNextSoal;
    }
         */
    public static String jsobGetString(JSONObject job,String cmd) {
    	String tmp="null";
    	try {
    		tmp = job.getString(cmd);
    	}
    	catch(JSONException e) {
    		e.printStackTrace();
    	}
    	return tmp;
    }
    
    
}
