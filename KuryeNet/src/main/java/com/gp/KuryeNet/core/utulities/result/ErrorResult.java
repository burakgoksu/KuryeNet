package com.gp.KuryeNet.core.utulities.result;

public class ErrorResult extends Result{

	public ErrorResult(String message) {
		super(false,message);
	}
	
	public ErrorResult() {
		super(false);
	}

}
