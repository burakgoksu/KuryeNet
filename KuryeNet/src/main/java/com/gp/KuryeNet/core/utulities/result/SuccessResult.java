package com.gp.KuryeNet.core.utulities.result;

public class SuccessResult extends Result{

	public SuccessResult(String message) {
		super(true,message);
	}
	
	public SuccessResult() {
		super(true);
	}

}
