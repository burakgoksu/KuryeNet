package com.gp.KuryeNet.core.utulities.result;

import lombok.Data;

@Data
public class DataResult<T> extends Result{

	private T data;
	
	public DataResult(T data,boolean success, String message) {
		super(success, message);
		this.data = data;
	}
	
	public DataResult(T data,boolean success) {
		super(success);
		this.data = data;
	}

}
