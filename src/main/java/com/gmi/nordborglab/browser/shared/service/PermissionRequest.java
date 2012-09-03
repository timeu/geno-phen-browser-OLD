package com.gmi.nordborglab.browser.shared.service;

import com.gmi.nordborglab.browser.server.service.PermissionService;
import com.gmi.nordborglab.browser.server.service.SpringServiceLocator;
import com.gmi.nordborglab.browser.shared.proxy.CustomAclProxy;
import com.gmi.nordborglab.browser.shared.proxy.ExperimentProxy;
import com.gmi.nordborglab.browser.shared.proxy.SearchPermissionUserRoleProxy;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(value=PermissionService.class,locator=SpringServiceLocator.class)
public interface PermissionRequest extends RequestContext {

	public Request<CustomAclProxy> getPermissions(ExperimentProxy experiment);
	public Request<CustomAclProxy> updatePermissions(ExperimentProxy experiment,CustomAclProxy acl);
	
	public Request<SearchPermissionUserRoleProxy> searchUserAndRoles(String query);
	
}
