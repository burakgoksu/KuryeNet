package com.gp.KuryeNet.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AIModelPredictionResponse {
	
	private double Prediction;
	
	private int PredictionRound;

}
