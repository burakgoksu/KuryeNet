package com.gp.KuryeNet.core.utulities.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DataResult", description = "Response envelope that includes a data payload.")
public class DataResult<T> extends Result{

	@Schema(description = "Response payload.")
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
