package com.example.dogapi.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorResponseUtil {
	public static Map<String, String> collectError(BindingResult bindingResult) {
		Map<String, String> errors = new HashMap<>();
		for (Object object : bindingResult.getAllErrors()) {
		    if(object instanceof FieldError) {
		        FieldError fieldError = (FieldError) object;
		        errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		    }
		}
		return errors;
	}

	public static String errorResponseClientRequest (String response){
		String error = response;
		Pattern pattern = Pattern.compile("message\":\"(.*?)\",\"code" , Pattern.DOTALL);
		Matcher matcher = pattern.matcher(error);
		while (matcher.find()) {
			error = matcher.group(1);
		}

		return error;
	}
}
