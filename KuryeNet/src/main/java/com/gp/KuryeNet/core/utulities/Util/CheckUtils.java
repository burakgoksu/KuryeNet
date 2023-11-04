package com.gp.KuryeNet.core.utulities.Util;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.springframework.data.repository.CrudRepository;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CheckUtils {

	 	public boolean notExistsById(CrudRepository<?, Integer> dao, Integer id) {
	        return id == null || id <= 0 || !dao.existsById(id);
	    }

	    public boolean notExistsById(CrudRepository<?, Short> dao, Short id) {
	        return id == null || id <= 0 || !dao.existsById(id);
	    }

	    public boolean invalidDateFormat(String date) {
	        return date == null || !Pattern.matches(Utils.Const.DATE_REGEXP, date);
	    }
	    
	    public boolean invalidPhone(String phone) {
	        return phone == null || phone.length() <= 9 || phone.length() >= 23 || !Pattern.matches(Utils.Const.PHONE_NUM_REGEXP, phone);
	    }

	    public boolean startEndConflict(short start, Short end) {
	        return end != null && start > end;
	    }

	    public boolean equals(String x, String y) {
	        return (x == null && y == null) || (x != null && x.equals(y));
	    }

	    public boolean equals(Number x, Number y) {
	        return (x == null && y == null) || (x != null && x.equals(y));
	    }

	    public boolean equals(LocalDate x, LocalDate y) {
	        return (x == null && y == null) || (x != null && x.isEqual(y));
	    }

	    public boolean greater(Double greater, Double less) {
	        return greater != null && less != null && greater > less;
	    }
	
}
