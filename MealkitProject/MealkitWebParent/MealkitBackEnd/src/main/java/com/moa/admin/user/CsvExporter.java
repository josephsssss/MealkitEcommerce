package com.moa.admin.user;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.moa.common.entity.User;

public class CsvExporter {
	public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
		DateFormat dateFormmater = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timestamp = dateFormmater.format(new Date());
		String fileName = "user_" + timestamp + ".csv";
		response.setContentType("text/csv");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName;
		response.setHeader(headerKey, headerValue);
		
		
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader = {"아이디", "이메일", "이름", "역할", "활성여부"};
		
		csvWriter.writeHeader(csvHeader);
		csvWriter.close();
	}
}