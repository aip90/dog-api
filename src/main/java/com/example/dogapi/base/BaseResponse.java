package com.example.dogapi.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseResponse {

	@JsonInclude(Include.NON_NULL)
	private int status;

	@JsonInclude(Include.NON_NULL)
	private String message;

	@JsonInclude(Include.NON_NULL)
	private Object data;

	public BaseResponse(int status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}
}
