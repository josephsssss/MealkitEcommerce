package com.moa.admin.user.export;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.moa.admin.user.AbstractExporter;
import com.moa.common.entity.User;

public class UserCsvExporter extends AbstractExporter {
	public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "text/csv", ".csv");

		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

		String[] csvHeader = { "아이디", "사진", "이메일", "이름", "역할", "활성여부" };
		String[] fieldMapping = { "id", "photos", "email", "name", "roles", "enabled" };

		csvWriter.writeHeader(csvHeader);

		for (User user : listUsers) {
			csvWriter.write(user, fieldMapping);
		}

		csvWriter.close();
	}

}
