package beans.tools;

import java.util.StringTokenizer;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import java.util.Collections;
import java.util.Vector;
import java.util.ListIterator;
/**
 * Session Bean implementation class ToolSoalUjian
 */
@Stateless
@LocalBean
public class ToolSoalUjian extends Tool {
       
    /**
     * @see Tool#Tool()
     */
    public ToolSoalUjian() {
        super();
        // TODO Auto-generated constructor stub
    }

    //========duplicat ======= ada fn ini di Tool juga========================================================
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
    	String tmp = createTokenIdSoalAtChapterSesuaiNorutSoal(tokenKodeGroupAndListSoal);
    	System.out.println("tmp1-"+tmp);
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
    		if(totalSoalUjian>15) {
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
    							System.out.println("match at "+counterBeforeMatched);
    							if(counterBeforeMatched<7) {
    								norutNavigasiCounter = norutNavigasiCounter+counterBeforeMatched;
    								System.out.println("norutNavigasiCounter at "+norutNavigasiCounter);
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
    								System.out.println("j="+j);
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
    	System.out.println("=="+tokenKodeGroupAndListSoal+"=="+atChapter);
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
    			System.out.println("countTokens="+st.countTokens());
    			for(int i=1;i<=tkntt;i++) {
    				String listSoal = (String)st.nextToken();
    				System.out.println(i+".listSoal="+listSoal);
    				if(atChapter.equalsIgnoreCase(""+i)) {
    					match = true;
    					System.out.println("match="+match);
    					
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
    	System.out.println("prev="+tokenKodeGroupAndListSoal+">"+idSoal);
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
						if(match && !st1.hasMoreTokens() && st.hasMoreTokens()) {
							atChapter++;
							tokens = (String)st.nextToken();
							st1 = new StringTokenizer(tokens,",");
							bagianIgnore = st1.nextToken();
							//while(st1.hasMoreTokens()&&match) {
							tmp_idSoal = st1.nextToken();
							tmp_norutIgnore = st1.nextToken();
							infoNextSoal = tmp_idSoal+","+atChapter;
							//}	
						}
						else {
							System.out.println("looping");
							if(match && !st1.hasMoreTokens() && !st.hasMoreTokens()) {
							//at nomor terakhir harus balik ke 1
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
								tokens = (String)st.nextToken();
								st1 = new StringTokenizer(tokens,",");
								bagianIgnore = st1.nextToken();
								tmp_idSoal = st1.nextToken();
								tmp_norutIgnore = st1.nextToken();
								infoNextSoal = tmp_idSoal+",1";
							}	
						}
					}
				}
			}
    	}	
    	System.out.println("infoNextSoal="+infoNextSoal);
    	return infoNextSoal;
    }
    
    //==========================end duplicate fn==============================================================

    public static Vector acakMultipleChoice(String tknMultipleChoice, String charPemisah) {
    	System.out.println("tknMultipleChoice="+tknMultipleChoice);
    	Vector v= new Vector();
    	if(charPemisah==null) {
    		//default char pemisah ||
    		charPemisah="||";
    	}
    	if(tknMultipleChoice!=null && tknMultipleChoice.length()>0) {
    		ListIterator li = v.listIterator();
    		StringTokenizer st = new StringTokenizer(tknMultipleChoice,charPemisah);
    		while(st.hasMoreTokens()) {
    			//String tmp = 
    			li.add(st.nextToken());
    		}
    		Collections.shuffle(v);
    	}
    	return v;
    }
}
