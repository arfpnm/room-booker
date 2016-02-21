package com.nhs.trust.common;
/**
 * @author arif.mohammed
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class TewvResponseExceptionHandler {

	@ExceptionHandler(TransactionSystemException.class)
	public @ResponseBody ExceptionJsonFormat handleContraintsException(HttpServletRequest request, HttpServletResponse response, TransactionSystemException ex){
		ExceptionJsonFormat exceptionJsonFormat = new ExceptionJsonFormat();
		exceptionJsonFormat.setMessage(ex.getApplicationException().getMessage());
		exceptionJsonFormat.setStatusCode(response.getStatus());
		return exceptionJsonFormat;
	}
	
	@ExceptionHandler(Exception.class)
	public @ResponseBody ExceptionJsonFormat handleGeneralException(HttpServletRequest request, HttpServletResponse response, Exception ex){
		ExceptionJsonFormat exceptionJsonFormat = new ExceptionJsonFormat();
		exceptionJsonFormat.setMessage(ex.getMessage());
		exceptionJsonFormat.setStatusCode(response.getStatus());
		return exceptionJsonFormat;
	}
	
	@ExceptionHandler(JSONException.class)
	public @ResponseBody ExceptionJsonFormat handleJsonException(HttpServletRequest request, HttpServletResponse response, JSONException ex){
		ExceptionJsonFormat exceptionJsonFormat = new ExceptionJsonFormat();
		exceptionJsonFormat.setMessage(ex.getMessage());
		exceptionJsonFormat.setStatusCode(response.getStatus());
		return exceptionJsonFormat;
	}
	
}