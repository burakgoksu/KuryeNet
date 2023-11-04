package com.gp.KuryeNet.business.concretes.check;

import java.util.HashMap;
import java.util.Map;

import com.gp.KuryeNet.business.abstracts.check.BaseCheckService;

public abstract class BaseCheckManager implements BaseCheckService {

    protected Map<String, String> errors = new HashMap<>();

    public Map<String, String> getErrors() {
        Map<String, String> temp = errors;
        this.errors = new HashMap<>();
        return temp;
    }
    
}
