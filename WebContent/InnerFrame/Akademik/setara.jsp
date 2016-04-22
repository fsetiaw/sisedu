<%
if(vSeataraYgDibuka!=null && vSeataraYgDibuka.size()>0) {
		//System.out.println("sampai kesini 2a");
		%>
		<br/>
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:700px">	
		<tr>
			<td style="background:#369;color:#fff;text-align:center;font-size:25px" colspan="6"><label><B>MATAKULIAH YANG DAPAT DIAMBIL PADA FAKULTAS / PRODI LAINNYA</B></label></td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:center;width:60px"><label><B>KODE</B> </label></td>
			<td style="background:#369;color:#fff;text-align:left;width:330px"><label><B>MATAKULIAH</B> </label></td>
			<td style="background:#369;color:#fff;text-align:left;width:10px"><label><B>SKS</B> </label></td>
			<td style="background:#369;color:#fff;text-align:center;width:300px" ><label><B>INFO PRODI & KELAS</B> </label></td>
		</tr>
		<%
		Collections.sort(vSeataraYgDibuka);
		ListIterator liv = vSeataraYgDibuka.listIterator();
		boolean first = true;
		boolean new_makul = true;
		String prev_makul_kdkmk = null;
		String prev_makul_kdkmk_unique_id = null;
		Vector vTmpAlter = new Vector(); // nampung kelas alternativ untuk tiap makul ori
		ListIterator litmp = null;
		while(liv.hasNext()) {
			String brs = (String)liv.next();
			//System.out.println("barisan="+brs);
			StringTokenizer st = new StringTokenizer(brs,",");
			if(st.countTokens()>1) {
			//kdkmk+","+nakmk+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+kdpsttmp+","+kdkmkmakul+","+nakmkmakul);
				String idkmk_ori = st.nextToken();
				String kdkmk_ori = st.nextToken();
				String nakmk_ori = st.nextToken();
				String shift = st.nextToken();
				String norutKelasParalel = st.nextToken();
				String currStatus = st.nextToken();
				String npmdos = st.nextToken();
				String npmasdos = st.nextToken();
				String canceled = st.nextToken();
				String kodeKelas = st.nextToken();
				String kodeRuang = st.nextToken();
				String kodeGedung = st.nextToken();
				String kodeKampus = st.nextToken();
				String tknDayTime = st.nextToken();
				String nmmdos = st.nextToken();
				String nmmasdos = st.nextToken();
				String enrolled = st.nextToken();
				String maxEnrolled = st.nextToken();
				String minEnrolled = st.nextToken();
				String kdpsttmp = st.nextToken();
				String kdkmkmakul = st.nextToken();
				String nakmkmakul = st.nextToken();
				String uniqueIdClassPool = st.nextToken();
				if(first) {
					first = false;
					prev_makul_kdkmk = new String(kdkmk_ori);
					//prev_makul_kdkmk_unique_id = new String(uniqueIdClassPool);
					vTmpAlter = new Vector();
					litmp = vTmpAlter.listIterator();
					litmp.add(shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+kdpsttmp+","+kdkmkmakul+","+nakmkmakul+","+uniqueIdClassPool);
				}
				else {
					if(!prev_makul_kdkmk.equalsIgnoreCase(kdkmk_ori)) {
						//kalo ganti makul ori baru 
			%>
			<tr>
				<td style="color:#000;text-align:center;width:60px"><label><B><%=kdkmk_ori %></B> </label></td>
				<td style="color:#000;text-align:left;width:330px"><label><B><%=nakmk_ori.replace("tandaKoma",",") %></B> </label></td>
				<td style="color:#000;text-align:center;width:10px"><label><B><%=0 %></B> </label></td>
				<td>
					<%
						String shift_ = "null";
						String norutKelasParalel_ = "null";
						String currStatus_ = "null";
						String npmdos_ = "null";
						String npmasdos_ = "null";
						String canceled_ = "null";
						String kodeKelas_ = "null";
						String kodeRuang_ = "null";
						String kodeGedung_ = "null";
						String kodeKampus_ = "null";
						String tknDayTime_ = "null";
						String nmmdos_ = "null";
						String nmmasdos_ = "null";
						String enrolled_ = "null";
						String maxEnrolled_ = "null";
						String minEnrolled_ = "null";
						String kdpsttmp_prodi_lain = "null";
						String kdkmkmakul_prodi_lain = "null";
						String nakmkmakul_prodi_lain = "null";
						String uniqueIdClass_Pool = "null";
					%>
					//System.out.println("kesini"");
				<select name="kelasSelected" style="width:300px">
					<option value="null">--PILIH KELAS--</option>
						<%
						litmp = vTmpAlter.listIterator();
						while(litmp.hasNext()) {
							String brs_ = (String)litmp.next();
							StringTokenizer st_ = new StringTokenizer(brs_,",");
							while(st_.hasMoreTokens()) {
								shift = st_.nextToken();
								norutKelasParalel = st_.nextToken();
								currStatus = st_.nextToken();
								npmdos = st_.nextToken();
								npmasdos = st_.nextToken();
								canceled = st_.nextToken();
								kodeKelas = st_.nextToken();
								kodeRuang = st_.nextToken();
								kodeGedung = st_.nextToken();
								kodeKampus = st_.nextToken();
							////System.out.println("kodeKampus="+kodeKampus);
								tknDayTime = st_.nextToken();
								nmmdos = st_.nextToken();
								nmmasdos = st_.nextToken();
								enrolled = st_.nextToken();
								maxEnrolled = st_.nextToken();
								minEnrolled= st_.nextToken();
								kdpsttmp_prodi_lain = st_.nextToken();
								kdkmkmakul_prodi_lain = st_.nextToken();
								nakmkmakul_prodi_lain = st_.nextToken();
								uniqueIdClass_Pool = st_.nextToken();
							 //idkmk                                  //kdkmk
							%>
								              
					<option value="<%=idkmk_ori%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk_ori%>,null,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueIdClass_Pool%>">PRODI : <%=Converter.getNamaKdpst(kdpsttmp_prodi_lain) %>, INFO MK :<%= nakmkmakul_prodi_lain%> (<%= kdkmkmakul_prodi_lain%>) Kelas #<%=norutKelasParalel%> (<%=shift%>/<%=kodeKampus %>); Jadwal:<%=tknDayTime%></option>
						
						<%
									
							}
						}	
				%>
					</select>
				</td>
			</tr>		
		<%
						litmp = vTmpAlter.listIterator();
						litmp.add(shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+kdpsttmp+","+kdkmkmakul+","+nakmkmakul+","+uniqueIdClassPool);
						prev_makul_kdkmk = new String(kdkmk_ori);
						//prev_makul_kdkmk_unique_id = new String(uniqueIdClassPool);
						new_makul = true;
					}
					else {
						new_makul = false;
						litmp.add(shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+kdpsttmp+","+kdkmkmakul+","+nakmkmakul+","+uniqueIdClassPool);
					}
				}
				//System.out.println("loop="+vTmpAlter.size());
				if(!liv.hasNext()) {
					////System.out.println("masuk="+vTmpAlter.size());
			%>
			<tr>
				<td style="color:#000;text-align:center;width:60px"><label><B><%=kdkmk_ori %></B> </label></td>
				<td style="color:#000;text-align:left;width:330px"><label><B><%=nakmk_ori.replace("tandaKoma",",") %></B> </label></td>
				<td style="color:#000;text-align:center;width:10px"><label><B><%=0 %></B> </label></td>
				<td>
					<%
					String shift_ = "null";
					String norutKelasParalel_ = "null";
					String currStatus_ = "null";
					String npmdos_ = "null";
					String npmasdos_ = "null";
					String canceled_ = "null";
					String kodeKelas_ = "null";
					String kodeRuang_ = "null";
					String kodeGedung_ = "null";
					String kodeKampus_ = "null";
					String tknDayTime_ = "null";
					String nmmdos_ = "null";
					String nmmasdos_ = "null";
					String enrolled_ = "null";
					String maxEnrolled_ = "null";
					String minEnrolled_ = "null";
					String kdpsttmp_prodi_lain = "null";
					String kdkmkmakul_prodi_lain = "null";
					String nakmkmakul_prodi_lain = "null";
					String uniqueIdClass_Pool = "null";
					%>
				<select name="kelasSelected" style="width:300px">
					<option value="null">--PILIH KELAS--</option>
						<%
					litmp = vTmpAlter.listIterator();
					while(litmp.hasNext()) {
						String brs_ = (String)litmp.next();
						StringTokenizer st_ = new StringTokenizer(brs_,",");
						while(st_.hasMoreTokens()) {
							shift = st_.nextToken();
							norutKelasParalel = st_.nextToken();
							currStatus = st_.nextToken();
							npmdos = st_.nextToken();
							npmasdos = st_.nextToken();
							canceled = st_.nextToken();
							kodeKelas = st_.nextToken();
							kodeRuang = st_.nextToken();
							kodeGedung = st_.nextToken();
							kodeKampus = st_.nextToken();
							////System.out.println("kodeKampus="+kodeKampus);
							tknDayTime = st_.nextToken();
							nmmdos = st_.nextToken();
							nmmasdos = st_.nextToken();
							enrolled = st_.nextToken();
							maxEnrolled = st_.nextToken();
							minEnrolled= st_.nextToken();
							kdpsttmp_prodi_lain = st_.nextToken();
							kdkmkmakul_prodi_lain = st_.nextToken();
							nakmkmakul_prodi_lain = st_.nextToken();
							 %>				uniqueIdClass_Pool = st_.nextToken();
							 //idkmk                                  //kdkmk
							%>								              
					<option value="<%=idkmk_ori%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk_ori%>,null,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueIdClass_Pool%>">PRODI : <%=Converter.getNamaKdpst(kdpsttmp_prodi_lain) %>, INFO MK :<%= nakmkmakul_prodi_lain%> (<%= kdkmkmakul_prodi_lain%>) Kelas #<%=norutKelasParalel%> (<%=shift%>/<%=kodeKampus %>); Jadwal:<%=tknDayTime%></option>
						<%
									
						}
					}	
				%>
					</select>
				</td>
			</tr>		
								<%
				}
				%>
			
				<%
			}			
		}
		%>
		</table><br/>
		<%
	}
	%>