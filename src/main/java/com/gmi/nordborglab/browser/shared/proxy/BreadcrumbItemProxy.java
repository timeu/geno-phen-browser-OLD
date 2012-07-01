package com.gmi.nordborglab.browser.shared.proxy;

import com.gmi.nordborglab.browser.server.domain.BreadcrumbItem;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(value=BreadcrumbItem.class)
public interface BreadcrumbItemProxy extends ValueProxy {

	String getText();

	String getType();

	Long getId();

}
