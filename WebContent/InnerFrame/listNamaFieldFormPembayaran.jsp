<%
/*
 * ingat while(loop) dibawah ini harus sama dengan pada page
 * BuktiSetoran.java
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
	}
	
}
	

 
	
	
	
	
	
	
	
	
	
	
	
	
	 %>