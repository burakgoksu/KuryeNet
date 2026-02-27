package com.gp.KuryeNet.core.utulities.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Result", description = "Base response envelope for all API responses.")
public class Result {
	
	@Schema(description = "Indicates if the request succeeded.", example = "true")
	private boolean success;
	@Schema(description = "Result message for clients.", example = "Operation completed")
	private String message;
	
	public Result(boolean success) {
		this.success = success;
	}
	
	public Result(boolean success,String message) {
		this(success);
		this.message = message;
	}
	
	

}
