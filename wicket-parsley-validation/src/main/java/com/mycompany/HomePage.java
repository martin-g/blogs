package com.mycompany;

import com.mycompany.parsley.ParsleyEmailValidator;
import com.mycompany.parsley.ParsleyUrlValidator;
import com.mycompany.parsley.ParsleyValidationBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		final Form form = new Form("form");
		form.setOutputMarkupId(true);
		add(form);

		TextField<String> fullName = new TextField<String>("fullname", Model.of(""));
		ParsleyValidationBehavior<String> behavior = new ParsleyValidationBehavior<String>();
		fullName.add(behavior);
		behavior.require(Boolean.TRUE);
		form.add(fullName);

		TextField<String> email = new TextField<String>("email", Model.of(""));
		email.add(new ParsleyEmailValidator().on("change", "cut"));
		form.add(email);

		TextField<String> website = new TextField<String>("website", Model.of(""));
		website.add(new ParsleyUrlValidator().on("change"));
		form.add(website);

		TextArea<String> message = new TextArea<String>("message", Model.of(""));
		form.add(message);

		AjaxButton submit = new AjaxButton("sbt", form)
		{
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				super.onSubmit(target, form);
				System.err.println("Submit!");
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				super.onError(target, form);
				System.err.println("Error");
			}

			@Override
			protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
			{
				super.updateAjaxAttributes(attributes);

				AjaxCallListener listener = new AjaxCallListener();
				listener.onPrecondition(String.format("return $('#%s').parsley( 'validate' );", form.getMarkupId()));
				attributes.getAjaxCallListeners().add(listener);
			}
		};
		form.add(submit);
    }
}
