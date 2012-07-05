package com.gmi.nordborglab.browser.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface MainResources extends ClientBundle{
	
	
	public interface MainStyle extends CssResource {
		String transplant_logo_footer();
		String eu_logo_footer();
		String fp7_logo_footer();
		String gmi_logo_footer();
		String arrow_down();
		String avatar();
		String logout();
		String profile();
		String button();
		String button_grey();
		String button_blue();
		String cursor();
	}

	@Source("images/transplant_logo_small.png")
	ImageResource transplant_logo_footer();
	
	@Source("images/fp7_logo_small.png")
	ImageResource fp7_logo_footer();
	
	
	@Source("images/eu_logo_small.png")
	ImageResource eu_logo_footer();
	
	@Source("images/gmi_logo_small.png")
	ImageResource gmi_logo_footer();
	
	@Source("images/arrow_down.png")
	ImageResource arrow_down();
	
	@Source("images/avatar.png")
	ImageResource avatar();
	
	@Source("images/logout.png")
	ImageResource logout();
	
	@Source("images/profile.png")
	ImageResource profile();
	
	@Source("style.css")
	MainStyle style();
}
