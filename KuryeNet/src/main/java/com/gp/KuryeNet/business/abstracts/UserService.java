package com.gp.KuryeNet.business.abstracts;

import com.gp.KuryeNet.core.entities.User;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import java.util.List;

public interface UserService {
	
	Result add(User user);

	Result delete(int userId);

	Result restore(int userId);

	DataResult<List<User>> getDeleted();

	DataResult<User> getByEmail(String email);
}
