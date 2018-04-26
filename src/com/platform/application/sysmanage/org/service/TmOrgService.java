package com.platform.application.sysmanage.org.service;

import com.platform.application.sysmanage.org.OrgDto;

public interface TmOrgService {

	public OrgDto findById(final String orgCode, final boolean cascade);
}
