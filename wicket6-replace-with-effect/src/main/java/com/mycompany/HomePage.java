package com.mycompany;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		final Label toReplace = new Label("toReplace", "Initial content");
		toReplace.setOutputMarkupId(true);
		add(toReplace);

		AjaxLink<Void> link = new AjaxLink<Void>("link")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				toReplace.setDefaultModelObject("New value");
				Effects.replace(target, toReplace);
			}
		};
		add(link);
	}

	private static class Effects {

		private static void replace(AjaxRequestTarget target, Component component) {
			component.add(new DisplayNoneBehavior());

			target.prependJavaScript("notify|jQuery('#"+component.getMarkupId()+"').slideUp(1000, notify);");
			target.add(component);
			target.appendJavaScript("jQuery('#"+component.getMarkupId()+"').slideDown(1000);");
		}
	}

	private static class DisplayNoneBehavior extends AttributeModifier {

		private DisplayNoneBehavior()
		{
			super("style", Model.of("display: none"));
		}

		@Override
		public boolean isTemporary(Component component)
		{
			return true;
		}
	}
}
