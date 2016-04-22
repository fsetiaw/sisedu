
	<form name="formUpload1" id="formUpload1">
		<table align="center" border="1" style="background:#d9e1e5;color:#fff;width:700px">	
				<tr>
					<td align="center" colspan="4" bgcolor="#369"><b>FORM UPLOAD BAYARAN (Part:1/2)</b>
						
				</tr>
				<tr>
					<td colspan="4" style="color:black;text-align:center">
					Harap dipersiakan softcopy kuitansi pembayaran bank untuk di upload/unggah 
					</td>
				</tr>	
				<tr>
					<td>
						<input type="hidden" value="<%=tmp%>" name="No-Kode_String_Wajib" readonly style="width:95%"/>
						<input type="hidden" value="<%=nmm %>" name="Nama_Huruf_Wajib" readonly style="width:95%"/>
						<input type="hidden" value="<%=npm%>" name="NPM_String_Wajib" readonly style="width:95%" />
						<table align="center" border="1" style="color:#000;width:690px">
							<tr>
								<td colspan="2"><label>Nama Penyetor</label></td><td colspan="2"><input type="text" name="Nama-Penyetor_Huruf_Opt"  style="width:99%" placeholder="Harap diisi bila disetorkan oleh orang lain"/></td>
							</tr>
							<tr>
								<td colspan="4" style="background:#A6BAC4"><label><b>Komponen Biaya: </b><br/>
								<div style="font-style:italic">Harap Mengupload bukti bayaran per 1(satu) slip dan diisikan sesuai dengan komponen biaya yang tertera pada slip bayaran bank terkait.</div>
								</label></td>
							</tr>
							<tr>
								<td colspan="4">
									<label>
									Biaya DPP Angkusran ke: <input type="text" name="Angsuran-DPP-Ke_Int_Opt_Group1" style="width:20px"/> Gelombang: <input type="text" name="Gelombang_Int_Opt_Group1" required style="width:20px"/> 
									sebesar <input type="text" name="Besaran-DPP_Double_Opt_Group1" required style="width:318px" placeholder="IDR (Rupiah)"/> 
									
									</label>
								</td>

							</tr>
							<tr>
								<td colspan="2"><label>Bia</label></td><td colspan="2"><input type="text" name="Nama-Penyetor_Huruf_Opt"  style="width:99%" placeholder="Harap diisi bila disetorkan oleh orang lain"/></td>
							</tr>	
								<td width="20%"><label>Jumlah Pembayaran</label></td><td width="30%" align="center"><input type="text" name="Jumlah-Pembayaran_Double_Wajib" required style="width:98%" placeholder="IDR (Rupiah)"/></td>
							
								<td width="25%"><label>Tanggal Transaksi Bank</label></td><td width="25%" align="center"><input type="date" value="" name="Tgl-Transaksi_Date_Wajib" style="width:99%"/></td>
								
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td bgcolor="#369" colspan="4" align="center"><input type="button" id="somebutton" value="press here" /></td>
				</tr>
			</table>	
		</from>	
