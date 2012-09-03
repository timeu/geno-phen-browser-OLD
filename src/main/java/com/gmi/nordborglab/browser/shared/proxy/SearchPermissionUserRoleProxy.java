package com.gmi.nordborglab.browser.shared.proxy;

import java.util.List;

import com.gmi.nordborglab.browser.server.domain.acl.SearchPermissionUserRole;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(value=SearchPermissionUserRole.class)
public interface SearchPermissionUserRoleProxy extends ValueProxy {

	public List<PermissionPrincipalProxy> getPrincipals();
}
