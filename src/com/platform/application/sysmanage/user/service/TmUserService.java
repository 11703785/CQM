package com.platform.application.sysmanage.user.service;

import com.platform.application.sysmanage.user.UserDto;
import com.platform.application.sysmanage.user.bean.TmUser;

public interface TmUserService {

	public UserDto persist(TmUser tmUser);

	public UserDto findById(String userId, boolean cascade);
}
