package servlets.update;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.keu.*;
import beans.login.InitSessionUsr;
import beans.tools.Checker;

import java.util.StringTokenizer;
/**
 * Servlet implementation class BuktiSetoran
 */
@WebServlet("/BuktiSetoran")
public class BuktiSetoran extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuktiSetoran() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("bukti setoran");
		HttpSession session = request.getSession(true);
		 
 		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String namaFileSetoran = (String) session.getAttribute("nuFileName");
		session.removeAttribute("nuFileName");
		//System.out.println("namaFileSetoran="+namaFileSetoran);
		
		/*
		* deklare all form variable here !!!
		* harus sama dgn uploadPymnt.jsp
		*/
		String tipeForm = null;
		String fwdPg=null;
		//=============form 1 & 4=============================
		String tglTransCash = null;
		String biayaSelect1 = null;
		String biayaSelect2 = null;
		String biayaSelect3 = null;
		String biayaSelect4 = null;
		String biayaSelect5 = null;
		String biayaSelect6 = null;
		String biayaSelect7 = null;
		String biayaSelect8 = null;
		String biayaSelect9 = null;
		String select1 = null;
		String select2 = null;
		String select3 = null;
		String select4 = null;
		String select5 = null;
		String select6 = null;
		String select7 = null;
		String select8 = null;
		String select9 = null;
		//=============end form 1=============================
		
		//=============form 2=============================
		String namaPenyetor = null;
		String besaran = null;
		String tglTrans = null;
		String angsuranKe = null;
		String gelombangKe = null;
		String biayaJaket = null;
		String sumberDana = null;
		//============end form2======================
		//=============form 3=============================
		String bppKe = null;
		String besaranBpp = null;
		String pendaftaranSmsKe = null;
		String besaranHeregistrasi = null;
		String totSks = null;
		String sksSmsKe = null;
		String biayaSks = null;
		String biayaBinaan = null;
		String biayaDkm = null;
		String biayaPraktik = null;
		String biayaBimbinganSkripsi = null;
		String biayaUjianSkripsi = null;
		String biayaSumbanganBuku = null;
		String biayaJurnal = null;
		String biayaIjazah = null;
		String biayaWisuda = null;
		String biayaKp = null; 
		String biayaAdmBank = null; //untuk beasiswa s1
		//=============end form 3=============================
		String objId = null;
		String nmm = null;
		String npm = null;
		String kdpst = null;
		String obj_lvl = null;

		String targett = "get.histPymnt";
		String fieldAndValue = (String)session.getAttribute("fieldAndValue");
		System.out.println("fieldAndValue@nuktiSetoran="+fieldAndValue);
		if(fieldAndValue!=null) {
			StringTokenizer st = new StringTokenizer(fieldAndValue,"||");
			/*
			 * ingat while(loop) dibawah ini harus sama dengan pada page: 
			 * listNamaFieldFormPembayaran.jsp
			 * dan berefec pada f() insertPymntTransitTableForm3WithBukti
			 */
			while(st.hasMoreTokens()) {
				String fieldName = st.nextToken();
				String fieldValue = st.nextToken();
				if(fieldName.equalsIgnoreCase("idObj_Int_Opt")) {
					objId = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("StringfwdPageIfValid_String_Opt")) {
					fwdPg = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("objLvl_Int_Opt")) {
					obj_lvl = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("kdpst_String_Opt")) {
					kdpst = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("nmm_String_Opt")) {
					nmm = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("npm_String_Opt")) {
					npm = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("tipeForm_String_Opt")) {
					tipeForm = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Nama-Penyetor_Huruf_Opt")) {
					namaPenyetor = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Besaran_Double_Opt_Angsuran-DPP-Ke_Wajib")) {
					besaran = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Tgl-Transaksi-Bank_Date_Wajib")) {
					tglTrans = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Tgl-Transaksi-Cash_Date_Wajib")) {
					tglTransCash = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Angsuran-DPP-Ke_Int_Opt_Besaran_Wajib")) {
					angsuranKe = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Gelombang_Int_Opt_Besaran_Wajib")) {
					gelombangKe = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Biaya-Jaket-Almamater_Double_Wajib_Besaran_Opt")) {
					biayaJaket = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Pembayaran-BPP-Semester-Ke_Int_Opt_Besaran-Biaya-BPP_Wajib")) {
					bppKe = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Besaran-Biaya-BPP_Double_Opt_Pembayaran-BPP-Semester-Ke_Wajib")) {
					besaranBpp = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Pembayaran-Daftar-Ulang-Semester-Ke_Int_Opt_Besaran-Biaya-Daftar-Ulang_Wajib")) {
					pendaftaranSmsKe = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Besaran-Biaya-Daftar-Ulang_Double_Opt_Pembayaran-Daftar-Ulang-Semester-Ke_Wajib")) {
					besaranHeregistrasi = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Tot-SKS-Diambil_Double_Opt_Besaran-Biaya-SKS_Wajib")) {
					totSks = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Pembayaran-SKS-Semester-Ke_Int_Opt_Besaran-Biaya-SKS_Wajib")) {
					sksSmsKe = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Besaran-Biaya-SKS_Double_Opt_Pembayaran-SKS-Semester-Ke_Wajib")) {
					biayaSks = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Besaran-Dana-Kemahasiswaan_Double_Opt")) {
					biayaDkm = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Besaran-Praktikum_Double_Opt")) {
					biayaPraktik = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Besaran-Bimbingan-Skripsi_Double_Opt")) {
					biayaBimbinganSkripsi = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Besaran-Ujian-Skripsi_Double_Opt")) {
					biayaUjianSkripsi = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Besaran-Sumbangan-Buku_Double_Opt")) {
					biayaSumbanganBuku = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Besaran-Jurnal_Double_Opt")) {
					biayaJurnal = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Besaran-Ijazah_Double_Opt")) {
					biayaIjazah = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Besaran-Wisuda_Double_Opt")) {
					biayaWisuda = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Besaran-Pembinaan_Double_Opt")) {
					biayaBinaan = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Besaran-Sidang-Kerja-Praktek_Double_Opt")) {
					biayaKp = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Besaran-Biaya-Administrasi_Double_Opt")) {
					biayaAdmBank = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Selection-1_Double_Opt")) {
					biayaSelect1 = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Selection-2_Double_Opt")) {
					biayaSelect2 = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Selection-3_Double_Opt")) {
					biayaSelect3 = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Selection-4_Double_Opt")) {
					biayaSelect4 = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Selection-5_Double_Opt")) {
					biayaSelect5 = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Selection-6_Double_Opt")) {
					biayaSelect6 = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Selection-7_Double_Opt")) {
					biayaSelect7 = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Selection-8_Double_Opt")) {
					biayaSelect8 = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Selection-9_Double_Opt")) {
					biayaSelect9 = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("selection1_String_Opt")) {
					select1 = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("selection2_String_Opt")) {
					select2 = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("selection3_String_Opt")) {
					select3 = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("selection4_String_Opt")) {
					select4 = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("selection5_String_Opt")) {
					select5 = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("selection6_String_Opt")) {
					select6 = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("selection7_String_Opt")) {
					select7 = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("selection8_String_Opt")) {
					select8 = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("selection9_String_Opt")) {
					select9 = ""+fieldValue;
				}
				else if(fieldName.equalsIgnoreCase("Sumber-Dana_Huruf_Opt")) {
					sumberDana = ""+fieldValue;
					StringTokenizer stt = new StringTokenizer(sumberDana,",");
					sumberDana = ""+stt.nextToken();
				}
			}
		}
		
		if(namaPenyetor==null || Checker.isStringNullOrEmpty(namaPenyetor)) {
			namaPenyetor = ""+nmm;
		}
		if(tipeForm.equalsIgnoreCase("form2")) {
			//proses form2
			UpdateDbKeu udb = new UpdateDbKeu(isu.getNpm());
			//cek target akun apakah extension ato bukan
			
			//bank account = acc-yayasan
			System.out.println("pre-biayaJaket="+biayaJaket);
			int i = udb.insertPymntTransitTableForm2WithBukti(objId,fwdPg,obj_lvl,kdpst,nmm,npm,tipeForm,namaPenyetor,besaran,tglTrans,angsuranKe,gelombangKe,biayaJaket,"acc-yayasan",namaFileSetoran,sumberDana);
			//request.getRequestDispatcher("get.histPymnt?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&atMenu=riwayatBayaran").forward(request,response);
		}
		else if(tipeForm.equalsIgnoreCase("form3")) {
			UpdateDbKeu udb = new UpdateDbKeu(isu.getNpm());
			//bank account = acc-pusat / Universitas Satyagama
			
			//int i = udb.insertPymntTransitTableForm3WithBukti(tglTrans,objId,fwdPg,obj_lvl,kdpst,nmm,npm,tipeForm, bppKe, besaranBpp, pendaftaranSmsKe, besaranHeregistrasi, totSks, sksSmsKe, biayaSks, biayaDkm, biayaPraktik, biayaBimbinganSkripsi, biayaUjianSkripsi, biayaSumbanganBuku, biayaJurnal, biayaIjazah, biayaWisuda);
			int i = udb.insertPymntTransitTableForm3WithBukti(tglTrans,namaFileSetoran,namaPenyetor, objId, fwdPg, obj_lvl, kdpst, nmm, npm, tipeForm, bppKe, besaranBpp, pendaftaranSmsKe, besaranHeregistrasi, totSks, sksSmsKe, biayaSks, biayaDkm, biayaPraktik, biayaBimbinganSkripsi, biayaUjianSkripsi, biayaSumbanganBuku, biayaJurnal, biayaIjazah, biayaWisuda, biayaBinaan, biayaKp, sumberDana, biayaAdmBank);
			//int i = udb.insertPymntTransitTableForm3WithBukti(objId,fwdPg,obj_lvl,kdpst,nmm,npm,tipeForm,namaPenyetor,tglTrans,bppKe,gelombangKe,"acc-yayasan",namaFileSetoran);
		}
		else if(tipeForm.equalsIgnoreCase("form1")) {//form tunai
			UpdateDbKeu udb = new UpdateDbKeu(isu.getNpm());

			int i = udb.insertCashPymntForm1Tunai(tglTransCash,namaPenyetor, objId, fwdPg, obj_lvl, kdpst, nmm, npm, tipeForm, select1, biayaSelect1, select2, biayaSelect2, select3, biayaSelect3, select4, biayaSelect4, select5, biayaSelect5, select6, biayaSelect6, select7, biayaSelect7, select8, biayaSelect8, select9, biayaSelect9);
			//int i = udb.insertPymntTransitTableForm3WithBukti(objId,fwdPg,obj_lvl,kdpst,nmm,npm,tipeForm,namaPenyetor,tglTrans,bppKe,gelombangKe,"acc-yayasan",namaFileSetoran);
		}
		else if(tipeForm.equalsIgnoreCase("form4")) {//pasac via bank
			UpdateDbKeu udb = new UpdateDbKeu(isu.getNpm());
			System.out.println("tglTrans befor update="+tglTrans);
			int i = udb.insertPymntTransitTableForm4WithBukti(tglTrans,namaFileSetoran,namaPenyetor, objId, fwdPg, obj_lvl, kdpst, nmm, npm, tipeForm, select1, biayaSelect1, select2, biayaSelect2, select3, biayaSelect3, select4, biayaSelect4, select5, biayaSelect5, select6, biayaSelect6, select7, biayaSelect7, select8, biayaSelect8, select9, biayaSelect9, sumberDana);
			//int i = udb.insertPymntTransitTableForm3WithBukti(objId,fwdPg,obj_lvl,kdpst,nmm,npm,tipeForm,namaPenyetor,tglTrans,bppKe,gelombangKe,"acc-yayasan",namaFileSetoran);
		}
		request.getRequestDispatcher("get.histPymnt?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&atMenu=riwayatBayaran").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
