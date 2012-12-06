package com.wicketinaction;


public class AddContentPage extends BasePage {
	private static final long serialVersionUID = 1L;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new AddContentAjaxPanel("addContentPanel"));
	}
}
