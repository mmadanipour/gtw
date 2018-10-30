package com.gtw.dto;

import java.io.Serializable;

import com.gtw.common.enums.ResponseStatus;

public class ResponseDto<T> implements Serializable {

	private ResponseStatus status;
	
	private T response;
	
	
	public ResponseDto() {
	}
	
	
	public ResponseDto(T response, ResponseStatus responseStatus) {
		this.response = response;
		this.status = responseStatus;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}


	
	

}
