package beans.setting;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.StringTokenizer;
import java.util.Collections;
import org.apache.tomcat.jdbc.pool.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import beans.tools.*;
import java.util.Vector;
import java.util.ListIterator;
/**
 * Session Bean implementation class Constants
 */
@Stateless
@LocalBean
public class Constants {
	static int quantityMsgShownPerPage=5;
	static String npmMhsTdkTerdaftar="0000000000002"; // mhs lama yang tidak terdaftar
	static String kdpstMhsTdkTerdaftar="UNREG";
	static String kdjenMhsTdkTerdaftar="Z";
	static String kdpti = "031031";
	static String namaKdpti = "Universitas Satyagama";
	static String defaultCountry="INDONESIA";
	static String kodePesanPengumuman="BcFromOperator-";
	static String rootWeb = "/ToUnivSatyagama";
	static String defaultNicknameUntukHeregistrasiByOperator="OPERATOR BAA";
	static String rootWebUniv = "www.satyagama.ac.id";
	static String dbSchema = "/USG";
	static String dbSchemaBankSoal = "/BANK_SOAL";
	static int rangeMgsInbox = 9;
	
	static final String  alamatIp="http://localhost:8080/com.otaku.rest/api";
	static String initCivFwdAddr="http://localhost:8080/ToUnivSatyagama/init.login";
	//static final String  alamatIp="http://localhost:8183/com.otaku.rest/api";
	//static String initCivFwdAddr="http://localhost:8183/ToUnivSatyagama/init.login";
	
	//static String rootFolder = "/home/me";
	static String folderPassPhoto = "/home/usg/USG/images/mhs/pass_photo"; //JANGAN DIPAKE LAGI
	static String folderBuktiBayaran = "/home/usg/USG/images/bukti_pymnt";
	//static String folderAudio = "/home/usg/USG/AUDIO";
	static String rootFolder = "/home/usg";

	static String rootFolderDokSpmiInTomcat = "InnerFrame/Spmi/Dokument";
	static String masterKuiFile = rootFolder+dbSchema+"/Master/BAK/kuitansi.xlsx";
	static String masterKuiPmbFile = rootFolder+dbSchema+"/Master/BAK/KuitansiPMB.xlsx";
	static String rootMasterKrsFile = rootFolder+dbSchema+"/Master/KRS";
	static String rootMasterAbsensiFile = rootFolder+dbSchema+"/Master/ABSEN/master_absensi.xlsx";
	static String namaFileMasterKartuUjian = "MasterKartuUjian.xlsx";
	static String rootMasterKartuUjianFile = rootFolder+dbSchema+"/Master/KartuUjian";
	static String rootMasterKhsFile = rootFolder+dbSchema+"/Master/KHS";
	static String rootMasterUsrPwdFile = rootFolder+dbSchema+"/Master/USRPWD";
	static String rootFolderMasterIjazah = rootFolder+dbSchema+"/Master/IJAZAH";
	static String masterFormKurikulumFile = rootFolder+dbSchema+"/Master/TBKMK/MASTER_TBKMK.xlsx";
	static String tmpFile=rootFolder+dbSchema+"/tmp";
	static String incomingUploadFile=rootFolder+dbSchema+"/uploaded";
	static String urlLoginHtml = "/usr/share/tomcat7/webapps/ToUnivSatyagama";
	static String urlLoginHtmlImages = "/usr/share/tomcat7/webapps/ToUnivSatyagama/images";
	static String listNegara = "AFGHANISTAN,ALBANIA,ALGERIA,ANDORRA,ANGOLA,ANTIGUA AND BARBUDA,ARGENTINA,ARMENIA,AUSTRALIA,AUSTRIA,AZERBAIJAN,BAHAMAS,BAHRAIN,BANGLADESH,BARBADOS,BELARUS,BELGIUM,BELIZE,BENIN,BHUTAN,BOLIVIA,BOSNIA AND HERZEGOVINA,BOTSWANA,BRAZIL,BRUNEI,BULGARIA,BURKINA FASO,BURUNDI,CAMBODIA,CAMEROON,CANADA,CAPE VERDE,CENTRAL AFRICAN REPUBLIC,CHAD,CHILE,CHINA,COLOMBIA,COMOROS,CONGO,CONGO,COSTA RICA,COTE D'IVOIRE (IVORY COAST),CROATIA,CUBA,CYPRUS,CZECH REPUBLIC,DENMARK,DJIBOUTI,DOMINICA,DOMINICAN REPUBLIC,ECUADOR,EGYPT,EL SALVADOR,EQUATORIAL GUINEA,ERITREA,ESTONIA,ETHIOPIA,FIJI,FINLAND,FRANCE,GABON,GAMBIA,GEORGIA,GERMANY,GHANA,GREECE,GRENADA,GUATEMALA,GUINEA,GUINEA-BISSAU,GUYANA,HAITI,HONDURAS,HUNGARY,ICELAND,INDIA,INDONESIA,IRAN,IRAQ,IRELAND,ISRAEL,ITALY,JAMAICA,JAPAN,JORDAN,KAZAKHSTAN,KENYA,KIRIBATI,KOREA,KOREA,KUWAIT,KYRGYZSTAN,LAOS,LATVIA,LEBANON,LESOTHO,LIBERIA,LIBYA,LIECHTENSTEIN,LITHUANIA,LUXEMBOURG,MACEDONIA,MADAGASCAR,MALAWI,MALAYSIA,MALDIVES,MALI,MALTA,MARSHALL ISLANDS,MAURITANIA,MAURITIUS,MEXICO,MICRONESIA,MOLDOVA,MONACO,MONGOLIA,MONTENEGRO,MOROCCO,MOZAMBIQUE,MYANMAR (BURMA),NAMIBIA,NAURU,NEPAL,NETHERLANDS,NEW ZEALAND,NICARAGUA,NIGER,NIGERIA,NORWAY,OMAN,PAKISTAN,PALAU,PANAMA,PAPUA NEW GUINEA,PARAGUAY,PERU,PHILIPPINES,POLAND,PORTUGAL,QATAR,ROMANIA,RUSSIA,RWANDA,SAINT KITTS AND NEVIS,SAINT LUCIA,SAINT VINCENT AND THE GRENADINES,SAMOA,SAN MARINO,SAO TOME AND PRINCIPE,SAUDI ARABIA,SENEGAL,SERBIA,SEYCHELLES,SIERRA LEONE,SINGAPORE,SLOVAKIA,SLOVENIA,SOLOMON ISLANDS,SOMALIA,SOUTH AFRICA,SPAIN,SRI LANKA,SUDAN,SURINAME,SWAZILAND,SWEDEN,SWITZERLAND,SYRIA,TAJIKISTAN,TANZANIA,THAILAND,TIMOR-LESTE (EAST TIMOR),TOGO,TONGA,TRINIDAD AND TOBAGO,TUNISIA,TURKEY,TURKMENISTAN,TUVALU,UGANDA,UKRAINE,UNITED ARAB EMIRATES,UNITED KINGDOM,UNITED STATES,URUGUAY,UZBEKISTAN,VANUATU,VATICAN CITY,VENEZUELA,VIETNAM,YEMEN,ZAMBIA,ZIMBABWE,ABKHAZIA,CHINA,NAGORNO-KARABAKH,NORTHERN CYPRUS,PRIDNESTROVIE (TRANSNISTRIA),SOMALILAND,SOUTH OSSETIA,ASHMORE AND CARTIER ISLANDS,CHRISTMAS ISLAND,COCOS (KEELING) ISLANDS,CORAL SEA ISLANDS,HEARD ISLAND AND MCDONALD ISLANDS,NORFOLK ISLAND,NEW CALEDONIA,FRENCH POLYNESIA,MAYOTTE,SAINT BARTHELEMY,SAINT MARTIN,SAINT PIERRE AND MIQUELON,WALLIS AND FUTUNA,FRENCH SOUTHERN AND ANTARCTIC LANDS,CLIPPERTON ISLAND,BOUVET ISLAND,COOK ISLANDS,NIUE,TOKELAU,GUERNSEY,ISLE OF MAN,JERSEY,ANGUILLA,BERMUDA,BRITISH INDIAN OCEAN TERRITORY,BRITISH SOVEREIGN BASE AREAS,BRITISH VIRGIN ISLANDS,CAYMAN ISLANDS,FALKLAND ISLANDS (ISLAS MALVINAS),GIBRALTAR,MONTSERRAT,PITCAIRN ISLANDS,SAINT HELENA,SOUTH GEORGIA & SOUTH SANDWICH ISLANDS,TURKS AND CAICOS ISLANDS,NORTHERN MARIANA ISLANDS,PUERTO RICO,AMERICAN SAMOA,BAKER ISLAND,GUAM,HOWLAND ISLAND,JARVIS ISLAND,JOHNSTON ATOLL,KINGMAN REEF,MIDWAY ISLANDS,NAVASSA ISLAND,PALMYRA ATOLL,U.S. VIRGIN ISLANDS,WAKE ISLAND,HONG KONG,MACAU,FAROE ISLANDS,GREENLAND,FRENCH GUIANA,GUADELOUPE,MARTINIQUE,REUNION,ALAND,ARUBA,NETHERLANDS ANTILLES,SVALBARD,ASCENSION,TRISTAN DA CUNHA,AUSTRALIAN ANTARCTIC TERRITORY,ROSS DEPENDENCY,PETER I ISLAND,QUEEN MAUD LAND,BRITISH ANTARCTIC TERRITORY";
	static String[]bankAccount = {"acc-pasca", "acc-pusat", "acc-ekstension", "acc-stimik", "acc-yayasan"};
	static String[]optionGender = {"L Pria", "P Wanita"};
	static String[]tipeCivitas = {"B Baru", "P Pindahan"};
	static String[]listTipeNoIdDosen = {"NIP", "NIS", "NPP", "NIK"};
	static String[]listTandaPengenal = {"KTP", "SIM", "PASPOR"};
	static String[]statusDosen = {"A-AKTIF","C-CUTI","K-KELUAR","AL-ALMARHUM","P-PENSIUN","SL-STUDI LANJUT","TI-TUGAS INSTANSI LAIN"};
	static String[]tipeIkatanKerjaDosen = {"TP-Tetap","TT-Tidak Tetap","HR-Honorer","DK-PNS DPK","BN-Tetap BHMN"};
	static String[]listJenjangJabatanAkademik = {"AM-Ahli Madia","LE-Lektor","LK-Lektro Kepala","GB-Guru Besar"};
	static String[]optionStatusNikah = {"Sendiri","Nikah","Pernah Menikah"};
	static String[]optionAgama={"Islam","Kristen Protestan","Hindu","Buddha","Kristen Katolik","Khonghucu","Yahudi","Kristen Ortodoks"};
	static String[]listFormType={"KURIKULUM PRODI"};
	static String[]listAllowableObjNicknameSender={"OPERATOR KAPRODI S3 IP","OPERATOR KAPRODI S2 IP","OPERATOR KAPRODI MM","OPERATOR KAPRODI HI","OPERATOR KAPRODI IP","OPERATOR KAPRODI HUKUM","OPERATOR KAPRODI ARSITEKTUR","OPERATOR KAPRODI ELEKTRO","OPERATOR KAPRODI INDUSTRI","OPERATOR KAPRODI AKUNTANSI","OPERATOR KAPRODI MANAJEMEN","OPERATOR KAPRODI AGROBIS","OPERATOR KAPRODI AGROTEK","OPERATOR KAPRODI SIPIL","OPERATOR KAPRODI INFORMATIKA","OPERATOR KAPRODI S3 IP","OPERATOR KAPRODI PARISHOT","OPERATOR KAPRODI AGAMA","OPERATOR KAPRODI MI","OPERATOR KAPRODI KA","OPERATOR WEB ADMIN","OPERATOR BAK","OPERATOR KEPALA BAK","OPERATOR BAK PASCA","OPERATOR KEPALA BAK PASCA","OPERATOR BAA","OPERATOR KEPALA BAA","OPERATOR WAREK-1","OPERATOR BAA PASCA","OPERATOR KEPALA BAA PASCA","OPERATOR KEPALA PERSONALIA","OPERATOR PERSONALIA","OPERATOR TU FE","OPERATOR KTU FE","OPERATOR TU FAI","OPERATOR KTU FAI","OPERATOR TU FH","OPERATOR KTU FH","OPERATOR TU FTI","OPERATOR KTU FTI","OPERATOR TU FISIP","OPERATOR KTU FISIP","OPERATOR TU FTSP","OPERATOR KTU FTSP","OPERATOR TU PASCA","OPERATOR KTU PASCA","OPERATOR TU FAPERTA","OPERATOR KTU FAPERTA","MHS HUKUM","MHS TEKNIK ELEKTRO","MHS TEKNIK SIPIL","MHS ARSITEKTUR","MHS TEKNIK INDUSTRI","MHS AGRIBISNIS","MHS AGROTEKNOLOGI","MHS TEKNIK INFORMATIKA","MHS S2 MANAJEMEN","MHS MANAJEMEN","MHS AKUNTANSI","MHS ILMU HUBUNGAN INTERNASIONAL","MHS S3 ILMU PEMERINTAHAN","MHS S2 ILMU PEMERINTAHAN","MHS ILMU PEMERINTAHAN","MHS INDUSTRI PARIWISATA DAN PERHOTELAN","MHS AGAMA","MHS MANAJEMEN INFORMATIKA","MHS KOMPUTERISASI AKUNTANSI","TAMU","OPERATOR PMB SATYAGAMA"};
	static String[]listObjNicknameWeb={"OPERATOR WEB ADMIN"};
	static String[]listObjNicknameBak={"OPERATOR BAK","OPERATOR KEPALA BAK","OPERATOR BAK PASCA","OPERATOR KEPALA BAK PASCA"};
	static String[]listObjNicknameBaa={"OPERATOR BAA","OPERATOR KEPALA BAA","OPERATOR BAA PASCA","OPERATOR KEPALA BAA PASCA"};
	static String[]listObjNicknamePrs={"OPERATOR PERSONALIA","OPERATOR KEPALA PERSONALIA"};
	static String[]listObjNicknameWrk={"OPERATOR WAREK-1","OPERATOR WAREK-2","OPERATOR WAREK-3","OPERATOR WAREK-4"};
	static String[]listObjNicknameRek={"OPERATOR REKTOR"};
	static String[]listObjNicknameTu={"OPERATOR TU FE","OPERATOR KTU FE","OPERATOR TU FAI","OPERATOR KTU FAI","OPERATOR TU FH","OPERATOR KTU FH","OPERATOR TU FTI","OPERATOR KTU FTI","OPERATOR TU FISIP","OPERATOR KTU FISIP","OPERATOR TU FTSP","OPERATOR KTU FTSP","OPERATOR TU PASCA","OPERATOR KTU PASCA","OPERATOR TU FAPERTA","OPERATOR KTU FAPERTA"};
	static String[]listObjNicknameMhs={"MHS HUKUM","MHS TEKNIK ELEKTRO","MHS TEKNIK SIPIL","MHS ARSITEKTUR","MHS TEKNIK INDUSTRI","MHS AGRIBISNIS","MHS AGROTEKNOLOGI","MHS TEKNIK INFORMATIKA","MHS S2 MANAJEMEN","MHS MANAJEMEN","MHS AKUNTANSI","MHS ILMU HUBUNGAN INTERNASIONAL","MHS S3 ILMU PEMERINTAHAN","MHS S2 ILMU PEMERINTAHAN","MHS ILMU PEMERINTAHAN","MHS INDUSTRI PARIWISATA DAN PERHOTELAN","MHS AGAMA","MHS MANAJEMEN INFORMATIKA","MHS KOMPUTERISASI AKUNTANSI"};
	static String[]listObjNicknameTamu={"TAMU"};
	static String[]listObjNicknamePmb={"OPERATOR PMB SATYAGAMA"};
	static String[]listObjNicknameKpr={"OPERATOR KAPRODI S3 IP","OPERATOR KAPRODI S2 IP","OPERATOR KAPRODI MM","OPERATOR KAPRODI HI","OPERATOR KAPRODI IP","OPERATOR KAPRODI HUKUM","OPERATOR KAPRODI ARSITEKTUR","OPERATOR KAPRODI ELEKTRO","OPERATOR KAPRODI INDUSTRI","OPERATOR KAPRODI AKUNTANSI","OPERATOR KAPRODI MANAJEMEN","OPERATOR KAPRODI AGROBIS","OPERATOR KAPRODI AGROTEK","OPERATOR KAPRODI SIPIL","OPERATOR KAPRODI INFORMATIKA","OPERATOR KAPRODI S3 IP","OPERATOR KAPRODI PARISHOT","OPERATOR KAPRODI AGAMA","OPERATOR KAPRODI MI","OPERATOR KAPRODI KA"};
	static boolean isTunaiDefault = true;
	static String tknKdjenKdpst = "A S-3 B S-2 C S-1 D D-4 E D-3 F D-2 G D-1 H Sp-1 I Sp-2 J Profesi X NON-AKADEMIK";
	static String kdpstPasca = "65101 61101 65001";
	static String listKdwpl = "A WAJIB,B PILIHAN,C WAJIB-PEMINATAN,D PILIHAN-PEMINATAN,S TA/SKRIPSI/TESIS/DISERTASI";	
	static String listJenisMk = "0 N/A,H HISTORY,A ART,S SCIENCE";	
	static String listStatusMk="A AKTIF, H HAPUS";		
	static String listStatusKurikulum="A AKTIF, H HAPUS";	
	static String kuiSearchChar = "kui-";
	static String tipePerhitunganTranscript = "bestBobot";
	static String tipePerhitunganNlakhTunda = "excluded";
	static String listKeterKasirPmb = "BIAYA PENDAFTARAN";
	static String listKeterKasirUtama = "BIAYA PENDAFTARAN,";//blm dipake
	static String kdpstDosen = "00011";
	static String[] listKdfakNonProdi ={"LAIN","STRUK"};
	static String defaultShift = "REGULER PAGI"; //deprecated krn kalo pasca beda jadi di upd via fungsi
	static String defaultPymntReqApprovee = null;
	//listKdpstProdi 
	//kdpst no listKdpstProdi berarti tidak usah pake cek shift krn bukan mhs
	static String[] listKdpstProdi ={"20201","22201","23201","26201","54201","54211","55201","57301","57302","61101","61201","62201","64201","65001","65101","65201","74201","88888","93402"};
	/* BOBOT NILAI TUNDA
	 *bila exclude, ipk tidak dihitung (pembagi tidk termasuk sks mk dgn nilai tunda tapi sksmk terhitung untuk perhitungan skstt dan sksms
	 *jsp yang berimbas bila ada perubahan penggunaan kata exclude ataupun penambahan pilihan selain bilangan nominal:
	 *-HistKrsKhs.jsp
	*/
	static String bobotNilaiTunda = "exclude";
	//static String spmiFormGbpp = rootFolderDokSpmiInTomcat+"/GBPP";
    /**
     * Default constructor. 
     */
    public Constants() {
        // TODO Auto-generated constructor stub
    }

    public static String getDefaultTargetNicknameBilaPembayaranDiinputOlehOperator(String kdjen) {
    	String target_nickname = null;
    	if(kdjen.equalsIgnoreCase("A")||kdjen.equalsIgnoreCase("B")) {
    		target_nickname = "OPERATOR BAK PASCA";
    	}
    	else {
    		target_nickname = "OPERATOR BAK";
    	}
    	return target_nickname;	
    }
    
    public static String getNamaKdpti() {
    	return namaKdpti;
    }
    
    public static String[] getListTandaPengenal() {
    	return listTandaPengenal;
    }
    
    public static String[] getListTipeNoIdDosen() {
    	return listTipeNoIdDosen;
    }
    
    
    
    public static String getRootMasterAbsensiFile() {
    	return rootMasterAbsensiFile;
    }
    
    public static String getDefaultNicknameUntukHeregistrasiByOperator() {
    	return defaultNicknameUntukHeregistrasiByOperator;
    }
    
    public static String getFolderBuktiBayaran(){
    	return folderBuktiBayaran;
    }
    
    public static String getDefaultShift(){
    	return defaultShift;
    }
 
    public static String[] getStatusDosen(){
    	return statusDosen;
    }

    public static String getDefaultShift(String kdjen){
    	if(kdjen.equalsIgnoreCase("A") || kdjen.equalsIgnoreCase("B")) {
    		defaultShift = "EKSEKUTIF PASCA";
    	}
    	else {
    		defaultShift = "REGULER PAGI";
    	}
    	return defaultShift;
    }
    
    /*
     * harus sesuai dengan object di table objek
     */
    public static String getDefaultPymntReqApprovee(String kdjen){
    	
    	if(kdjen.equalsIgnoreCase("A") || kdjen.equalsIgnoreCase("B")) {
    		defaultPymntReqApprovee = "OPERATOR WAREK-1";
    	}
    	else {
    		defaultPymntReqApprovee = "OPERATOR KEPALA BAK";
    	}
    	return defaultPymntReqApprovee;
    }
    
    
    
    public static String[] getListJenjangJabatanAkademik() {
    	return listJenjangJabatanAkademik;
    }
    
    public static String[] getListKdpstProdi() {
    	return listKdpstProdi;
    }
    
    public static int getRangeMgsInbox() {
    	return rangeMgsInbox;
    }
    
    public static String[] getTipeIkatanKerjaDosen() {
    	return tipeIkatanKerjaDosen;
    }
    
    public static String getAlamatIp() {
    	return alamatIp;
    }
    
    public static String getNamaFileMasterKartuUjian() {
    	return namaFileMasterKartuUjian;
    }
    public static String getKodePesanPengumuman() {
    	return kodePesanPengumuman;
    }
    
    public static String getKdpstDosen() {
    	return kdpstDosen;
    }
    
    
    
    public static String[] getListKdfakNonProdi() {
    	return listKdfakNonProdi;
    }
    
    public static String redirectObjectNickname(String objNickname) {
    	boolean match = false;
    	String command = null;
    	while(!match) {
    		//web
    		for(int i=0;i<listObjNicknameWeb.length&&!match;i++) {
    			if(listObjNicknameWeb[i].contains(objNickname)) {
    				match = true;
    				command = "allowContactWEB";
    			}
    		}
    		//tu
    		if(!match) {
    			for(int i=0;i<listObjNicknameTu.length&&!match;i++) {
        			if(listObjNicknameTu[i].contains(objNickname)) {
        				match = true;
        				command = "allowContactTU";
        			}
        		}
    		}
    		//baa
    		if(!match) {
    			for(int i=0;i<listObjNicknameBaa.length&&!match;i++) {
        			if(listObjNicknameBaa[i].contains(objNickname)) {
        				match = true;
        				command = "allowContactBAA";
        			}
        		}
    		}
    		//bak
    		if(!match) {
    			for(int i=0;i<listObjNicknameBak.length&&!match;i++) {
        			if(listObjNicknameBak[i].contains(objNickname)) {
        				match = true;
        				command = "allowContactBAK";
        			}
        		}
    		}
    		//mhs
    		if(!match) {
    			for(int i=0;i<listObjNicknameMhs.length&&!match;i++) {
        			if(listObjNicknameMhs[i].contains(objNickname)) {
        				match = true;
        				command = "allowContactMHS";
        			}
        		}
    		}
    		//tamu
    		if(!match) {
    			for(int i=0;i<listObjNicknameTamu.length&&!match;i++) {
        			if(listObjNicknameTamu[i].contains(objNickname)) {
        				match = true;
        				command = "allowContactTAMU";
        			}
        		}
    		}
    		//kaprodi
    		if(!match) {
    			for(int i=0;i<listObjNicknameKpr.length&&!match;i++) {
        			if(listObjNicknameKpr[i].contains(objNickname)) {
        				match = true;
        				command = "allowContactKPR";
        			}
        		}
    		}
    		//warek
    		if(!match) {
    			for(int i=0;i<listObjNicknameWrk.length&&!match;i++) {
        			if(listObjNicknameWrk[i].contains(objNickname)) {
        				match = true;
        				command = "allowContactWRK";
        			}
        		}
    		}
    		//rektor
    		if(!match) {
    			for(int i=0;i<listObjNicknameRek.length&&!match;i++) {
        			if(listObjNicknameRek[i].contains(objNickname)) {
        				match = true;
        				command = "allowContactREK";
        			}
        		}
    		}
    		//pegawai/peronalia
    		if(!match) {
    			for(int i=0;i<listObjNicknamePrs.length&&!match;i++) {
        			if(listObjNicknamePrs[i].contains(objNickname)) {
        				match = true;
        				command = "allowContactPRS";
        			}
        		}
    		}
    		
    		//admisi dan promosi
    		if(!match) {
    			for(int i=0;i<listObjNicknamePmb.length&&!match;i++) {
        			if(listObjNicknamePmb[i].contains(objNickname)) {
        				match = true;
        				command = "allowContactPMB";
        			}
        		}
    		}
    	}
    	return command;
    }
    
    public static String[] getListObjectNickname(String atMenu) {
    	String [] objNickName = null;
    	if(atMenu.equalsIgnoreCase("baa")) {
    		objNickName=Constants.getListObjNicknameBaa();
    	}
    	else {
    		if(atMenu.equalsIgnoreCase("bak")) {
        		objNickName=Constants.getListObjNicknameBak();
        	}
        	else {
        		if(atMenu.equalsIgnoreCase("tu")) {
            		objNickName=Constants.getListObjNicknameTu();
            	}
            	else {
            		if(atMenu.equalsIgnoreCase("web")) {
                		objNickName=Constants.getListObjNicknameWeb();
                	}
                	else {
                		if(atMenu.equalsIgnoreCase("tamu")) {
                    		objNickName=Constants.getListObjNicknameTamu();
                    	}
                    	else {
                    		if(atMenu.contains("mhs")) {
                        		objNickName=Constants.getListObjNicknameMhs();
                        	}
                        	else {
                        		if(atMenu.contains("prs")) {
                            		objNickName=Constants.getListObjNicknamePrs();
                            	}
                            	else {
                            		if(atMenu.contains("rek")) {
                                		objNickName=Constants.getListObjNicknameRek();
                                	}
                                	else {
                                		if(atMenu.contains("wrk")) {
                                    		objNickName=Constants.getListObjNicknameWrk();
                                    	}
                                    	else {
                                    		if(atMenu.contains("kpr")) {
                                        		objNickName=Constants.getListObjNicknameKpr();
                                        	}
                                        	else {
                                        		if(atMenu.contains("pmb")) {
                                            		objNickName=Constants.getListObjNicknamePmb();
                                            	}
                                            	else {
                                            		//and so on and on
                                            	}
                                        	}
                                    	}
                                	}
                            	}
                        	}
                    	}
                	}
            	}
        	}
    	}
    	return objNickName;
    }
    
    public static String redirectObjectNickname(String objNickname, String commandMenu) { //command menu blm kepake
    	//goto.dashContactBaa?atMenu=baa&targetObjNickName=OPERATOR BAA"
    	/*
    	 * harus disesuaikan dengan table object 
    	 */
    	//baa
    	String atmenuAndRedirect = null;
    	if((objNickname!=null && !Checker.isStringNullOrEmpty(objNickname))) {
    		if(objNickname.contains("OPERATOR BAA") || objNickname.contains("OPERATOR KEPALA BAA")) {
    			/*
    			 * dimana obj nickname disini = OPERATOR BAA = default.
    			 * hubungannya dengan scope masg2 usr ept ada yg bisa kontak OPERATOR KEPALA BAA
    			 */
    			atmenuAndRedirect = "baa||goto.dashContactBaa||OPERATOR BAA";//token ke tiga meaninglles, nanti di replace
    		}
    	}
    	else {
    		if(commandMenu.equalsIgnoreCase("allowContactBAA")) {
    			atmenuAndRedirect = "baa||goto.dashContactBaa||OPERATOR BAA";
    		}
    	}
    	
    	//tu	
    	if((objNickname!=null && !Checker.isStringNullOrEmpty(objNickname))) {
    		if(objNickname.contains("OPERATOR TU") || objNickname.contains("OPERATOR KTU")) {
    			atmenuAndRedirect = "tu||goto.dashContactTu||OPERATOR TU";
    		}
    	}
    	else {
    		if(commandMenu.equalsIgnoreCase("allowContactTU")) {
    			atmenuAndRedirect = "tu||goto.dashContactTu||OPERATOR TU";
    		}
    	}
    	
    	//web	
    	if((objNickname!=null && !Checker.isStringNullOrEmpty(objNickname))) {
    		if(objNickname.contains("OPERATOR WEB ADMIN")) {
    			atmenuAndRedirect = "web||goto.dashContactWeb||OPERATOR WEB ADMIN";
    		}
    	}
    	else {
    		if(commandMenu.equalsIgnoreCase("allowContactWEB")) {
    			atmenuAndRedirect = "web||goto.dashContactWeb||OPERATOR WEB ADMIN";
    		}
    	}
    	
    	//bak	
    	if((objNickname!=null && !Checker.isStringNullOrEmpty(objNickname))) {
    		if(objNickname.contains("OPERATOR BAK")) {
    			atmenuAndRedirect = "bak||goto.dashContactBak||OPERATOR BAK";
    		}
    	}
    	else {
    		if(commandMenu.equalsIgnoreCase("allowContactBAK")) {
    			atmenuAndRedirect = "bak||goto.dashContactBak||OPERATOR BAK";
    		}
    	}
    	
    	//mhs	
    	if((objNickname!=null && !Checker.isStringNullOrEmpty(objNickname))) {
    		if(objNickname.contains("MHS")) {
    			atmenuAndRedirect = "mhs||goto.dashContactMhs||MHS";
    		}
    	}
    	else {
    		if(commandMenu.equalsIgnoreCase("allowContactMHS")) {
    			atmenuAndRedirect = "mhs||goto.dashContactMhs||MHS";
    		}
    	}
    	
    	//tamu	
    	if((objNickname!=null && !Checker.isStringNullOrEmpty(objNickname))) {
    		if(objNickname.contains("TAMU")) {
    			atmenuAndRedirect = "tamu||goto.dashContactTamu||OPERATOR";
    		}
    	}
    	else {
    		if(commandMenu.equalsIgnoreCase("allowContactTAMU")) {
    			atmenuAndRedirect = "tamu||goto.dashContactTamu||OPERATOR";
    		}
    	}
    	
    	//rektor	
    	if((objNickname!=null && !Checker.isStringNullOrEmpty(objNickname))) {
    		if(objNickname.contains("REK")) {
    			atmenuAndRedirect = "rek||goto.dashContactRek||OPERATOR REKTOR";
    		}
    	}
    	else {
    		if(commandMenu.equalsIgnoreCase("allowContactREK")) {
    			atmenuAndRedirect = "rek||goto.dashContactRek||OPERATOR REKTOR";
    		}
    	}
    	
    	//warek	
    	if((objNickname!=null && !Checker.isStringNullOrEmpty(objNickname))) {
    		if(objNickname.contains("WRK")) {
    			atmenuAndRedirect = "warek||goto.dashContactWrk||WAREK";
    		}
    	}
    	else {
    		if(commandMenu.equalsIgnoreCase("allowContactWRK")) {
    			atmenuAndRedirect = "wrk||goto.dashContactWrk||WAREK";
    		}
    	}
    	
    	//kaprodi	
    	if((objNickname!=null && !Checker.isStringNullOrEmpty(objNickname))) {
    		if(objNickname.contains("KPR")) {
    			atmenuAndRedirect = "kaprodi||goto.dashContactKpr||KAPRODI";
    		}
    	}
    	else {
    		if(commandMenu.equalsIgnoreCase("allowContactKPR")) {
    			atmenuAndRedirect = "kaprodi||goto.dashContactKpr||KAPRODI";
    		}
    	}

    	//pesonalia	
    	if((objNickname!=null && !Checker.isStringNullOrEmpty(objNickname))) {
    		if(objNickname.contains("PRS")) {
    			atmenuAndRedirect = "personalia||goto.dashContactPrs||PERSONALIA";
    		}
    	}
    	else {
    		if(commandMenu.equalsIgnoreCase("allowContactPRS")) {
    			atmenuAndRedirect = "personalia||goto.dashContactPrs||PERSONALIA";
    		}
    	}
    	
    	//admisi dan proosi	
    	if((objNickname!=null && !Checker.isStringNullOrEmpty(objNickname))) {
    		if(objNickname.contains("PMB")) {
    			atmenuAndRedirect = "pmb||goto.dashContactPmb||PMB";
    		}
    	}
    	else {
    		if(commandMenu.equalsIgnoreCase("allowContactPMB")) {
    			atmenuAndRedirect = "pmb||goto.dashContactPmb||PMB";
    		}
    	}
    	return atmenuAndRedirect;
    }

    public static String[] getListAllowableObjNicknameSender() {
    	return listAllowableObjNicknameSender;
    }
    
    public static String[] getListObjNicknameTamu() {
    	return listObjNicknameTamu;
    }
    
    
    
    public static String[] getListObjNicknameMhs() {
    	return listObjNicknameMhs;
    }

    public static String[] getListObjNicknameRek() {
    	return listObjNicknameRek;
    }

    public static String[] getListObjNicknameWrk() {
    	return listObjNicknameWrk;
    }

    public static String[] getListObjNicknameKpr() {
    	return listObjNicknameKpr;
    }
    
    public static String[] getListObjNicknamePmb() {
    	return listObjNicknamePmb;
    }

    public static String[] getListObjNicknameWeb() {
    	return listObjNicknameWeb;
    }
    
    public static String[] getListObjNicknamePrs() {
    	return listObjNicknamePrs;
    }

    
    public static String[] getListObjNicknameBak() {
    	return listObjNicknameBak;
    }

    
    public static String[] getListObjNicknameBaa() {
    	return listObjNicknameBaa;
    }
    
    public static String[] getListObjNicknameTu() {
    	return listObjNicknameTu;
    }
    
    public static String getDbSchemaBankSoal() {
    	return dbSchemaBankSoal;
    }
    
    public static String getRootMasterKrsFile() {
    	return rootMasterKrsFile;
    }

    public static String getRootMasterKhsFile() {
    	return rootMasterKhsFile;
    }

    public static String getRootMasterUsrPwdFile() {
    	return rootMasterUsrPwdFile;
    }

    public static int getQuantityMsgShownPerPage() {
    	return quantityMsgShownPerPage;
    }
    
    public static String getFolderPassPhoto() {
    	return folderPassPhoto;
    }
    
    public static String getTipePerhitunganNlakhTunda() {
    	return tipePerhitunganNlakhTunda;
    }
    
    //public static String getFolderAudio() {
    //	return folderAudio;
    //}
    
    public static String getTknKdjenKdpst() {
    	return tknKdjenKdpst;
    }
    
    public static String getInitCivFwdAddr() {
    	return initCivFwdAddr;
    }
    
    public static String getTipePerhitunganTranscript() {
    	return tipePerhitunganTranscript;
    }
    
    public static String getNpmMhsTdkTerdaftar() {
    	return npmMhsTdkTerdaftar;
    }
    
    public static String getKdpstMhsTdkTerdaftar() {
    	return kdpstMhsTdkTerdaftar;
    }
    
    public static String getKdjenMhsTdkTerdaftar() {
    	return kdjenMhsTdkTerdaftar;
    }
    
    public static String getUrlLoginHtml() {
    	return urlLoginHtml;
    }
    
    public static String getUrlLoginHtmlImages() {
    	return urlLoginHtmlImages;
    }
    
    public static String getBobotNilaiTunda() {
    	return bobotNilaiTunda;
    }

    public static String getRootFolderMasterIjazah() {
    	return rootFolderMasterIjazah;
    }
    	
    public static String getRootFolderDokSpmiInTomcat() {
    	return rootFolderDokSpmiInTomcat;
    }
    public static String getKuiSearchChar(){
    	return kuiSearchChar;
    }
    
    public static String getRootMasterKartuUjianFile() {
    	return rootMasterKartuUjianFile;
    }
    
    public static String getListKeterKasirPmb() {
    	return listKeterKasirPmb;
    }
    
    public static Vector getListThsms() {
    	/*
    	 * input ini menghasilkan vector sort thsms berdasarkan table calendar.RANGE_LIST_THSMS, 
    	 * baris pertama adalah default thsms
    	 */
    	DataSource ds = null;
		Connection con = null;
		String[]listThsms=null;
		Vector v = null;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			PreparedStatement stmt = con.prepareStatement("select * from CALENDAR where AKTIF=?");
			stmt.setBoolean(1,true);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				String tokens = rs.getString("RANGE_LIST_THSMS");
				StringTokenizer st = new StringTokenizer(tokens,",");
				String staThsms = st.nextToken();
				String slcThsms = st.nextToken();
				String endThsms = st.nextToken();
				v = Tool.returnTokensListThsms(staThsms,endThsms,slcThsms);
			}	
    	}
    	catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		finally {
			if (con!=null) {
				try {
					con.close();
				}
				catch (Exception ignore) {
					System.out.println(ignore);
				}
			}
		}
    	return v;
    }

    
    public static Vector getListThsmsFromSmawl(String npmhs) {
    	/*
    	 * input ini menghasilkan vector sort thsms berdasarkan table calendar.RANGE_LIST_THSMS, 
    	 * baris pertama adalah default thsms
    	 */
    	DataSource ds = null;
		Connection con = null;
		String[]listThsms=null;
		Vector v = null;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			PreparedStatement stmt = con.prepareStatement("select * from CIVITAS where NPMHSMSMHS=?");
			stmt.setString(1,npmhs);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			String smawl = rs.getString("SMAWLMSMHS"); //pasti 5 digit
			stmt = con.prepareStatement("select * from CALENDAR where AKTIF=?");
			stmt.setBoolean(1,true);
			rs = stmt.executeQuery();
			if(rs.next()) {
				String tokens = rs.getString("RANGE_LIST_THSMS");
				StringTokenizer st = new StringTokenizer(tokens,",");
				String staThsms = st.nextToken();
				String slcThsms = st.nextToken();
				String endThsms = st.nextToken();
				v = Tool.returnTokensListThsms(staThsms,endThsms,slcThsms);
			}	
			ListIterator li = v.listIterator();
			while(li.hasNext()) {
				String thsms = (String)li.next();
				if(thsms.length()>5) {
					thsms = thsms.substring(0,4)+thsms.substring(5, 6);
				}
				//System.out.print(thsms+","+smawl);
				//if(thsms.compareToIgnoreCase(smawl)<0) {
				//	li.remove();
					//System.out.println(" remove");
				//}
				//else {
					//System.out.println("");
				//}
			}
    	}
    	catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		finally {
			if (con!=null) {
				try {
					con.close();
				}
				catch (Exception ignore) {
					System.out.println(ignore);
				}
			}
		}
    	return v;
    }
    
    
    
    
    public static String getListStatusKurikulum() {
    	return listStatusKurikulum;
    }
    public static String getListStatusMk() {
    	return listStatusMk;
    }
    
    public static String[] getListFormType() {
    	return listFormType;
    }
    public static boolean isThisKdpstPasca(String kdpst) {
    	boolean pasca = false;
    	if(kdpstPasca.contains(kdpst)) {
    		pasca = true;
    	}
    	return pasca;
    }
    public static String getListKdwpl(){
    	return listKdwpl;
    }
    public static String getListJenisMk(){
    	return listJenisMk;
    }
    public static String getDefaultCountry() {
    	return defaultCountry;
    }
    public static String[] getTipeCivitas() {
    	return tipeCivitas;
    }
    public static String[] getOptionGender() {
    	return optionGender;
    }
    public static String[] getOptionAgama() {
    	return optionAgama;
    }

    public static String getListNegara() {
    	return listNegara;
    }
    public static String[] getOptionStatusNikah() {
    	return optionStatusNikah;
    }
    public static String getRootWeb() {
    	return rootWeb;
    }
    public static String getRootWebUniv() {
    	return rootWebUniv;
    }    
    
    public static String getKdpti() {
    	return kdpti;
    }
    public static boolean getIsTunaiDefault() {
    	return isTunaiDefault;
    }
    public static String[]getBankAccount() {
    	return bankAccount;
    }
    public static String getRootFolder() {
    	return rootFolder;
    }
    public static String getMasterKuiFile() {
    	return masterKuiFile;
    }
    public static String getMasterKuiPmbFile() {
    	return masterKuiPmbFile;
    }

    public static String getMasterFormKurikulumFile() {
    	return masterFormKurikulumFile;
    }
    
    public static String getTmpFile() {
    	return tmpFile;
    }
    public static String getDbschema() {
    	return dbSchema;
    }
    public static String getIncomingUploadFile() {
    	return incomingUploadFile;
    }
    
    public static String getTokenTahunGiven(String thsms) {
    	/*
    	 * return token thsms, kalo sms genap ada 2 tahun
    	 */
    	String token_thsms = null;
    	int tahun = Integer.valueOf(thsms.substring(0,4)).intValue();
    	String sms = thsms.substring(4,thsms.length());
    	//int sms = Integer.valueOf(thsms.substring(4,5)).intValue();
    	if(sms.equalsIgnoreCase("1")||sms.equalsIgnoreCase("A")||sms.equalsIgnoreCase("1A")) {
    		token_thsms = ""+tahun;
    	}
    	else {
    		//if(sms==2) {
    		token_thsms = ""+tahun+" "+(tahun+1);
    		//}
    	}
    	return token_thsms;
    }
}
