package com.moa.admin.user;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;


public abstract class AbstractExporter {
	public void setResponseHeader(HttpServletResponse response, String contentType, String extension) throws IOException {
		DateFormat dateFormmater = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timestamp = dateFormmater.format(new Date());
		String fileName = "user_" + timestamp + extension;
		// response.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("CP949");
		response.setContentType(contentType);

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName;
		response.setHeader(headerKey, headerValue);
	}
}
