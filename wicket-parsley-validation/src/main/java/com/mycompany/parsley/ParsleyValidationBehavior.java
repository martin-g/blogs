package com.mycompany.parsley;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.validation.CompoundValidator;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;

/**
 * The base class that integrates Parsley.js with Wicket
 */
public class ParsleyValidationBehavior<T> extends Behavior implements IValidator<T>
{
	/**
	 * The form component being validated
	 */
	private FormComponent host;

	/**
	 * The validator to use for server-side validation
	 */
	private final IValidator<T> validator;

	/**
	 * A list of event names on which Parsley's will trigger
	 * validation for the form component
	 */
	private final List<String> triggerEvents;

	/**
	 * The type of Parsley validation (e.g. email, url, ...)
	 */
	private String type;

	/**
	 * A flag indicating whether the form component is required
	 *
	 * Three states:
	 * null - do not set data-require
	 * true|false - set the value
	 */
	private Boolean require;

	/**
	 * A constructor that uses a no-op server side validator
	 */
	public ParsleyValidationBehavior()
	{
		this(new CompoundValidator<T>());
	}

	/**
	 * Constructor.
	 *
	 * @param validator
	 *      The server-side validator
	 */
	public ParsleyValidationBehavior(IValidator<T> validator)
	{
		this.validator = Args.notNull(validator, "validator");

		this.triggerEvents = new ArrayList<String>();
	}

	/**
	 * Contributes parsley.js when this validator is used by any form component
	 *
	 * @param component
	 *      The form component that will be validated
	 * @param response
	 *      the response object where header contributions should be written
	 */
	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		response.render(JavaScriptHeaderItem.forReference(new ParsleyJsReference()));
	}

	/**
	 * Sets whether the form component is required
	 *
	 * @param require
	 * @return
	 */
	public ParsleyValidationBehavior<T> require(Boolean require)
	{
		this.require = require;
		return this;
	}

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		// mark the component as required at the server side
		if (require != null)
		{
			getHost().setRequired(require);
		}
	}

	/**
	 * Sets the type of Parsley validation.
	 *
	 * @param type
	 * @return
	 */
	public ParsleyValidationBehavior<T> type(String type)
	{
		this.type = type;
		return this;
	}

	/**
	 * Adds JavaScript events on which Parsley will trigger client-side validation
	 *
	 * @param events
	 * @return
	 */
	public ParsleyValidationBehavior<T> on(String... events)
	{
		if (events != null)
		{
			for (String event : events)
			{
				if (Strings.isEmpty(event) == false)
				{
					triggerEvents.add(event.toLowerCase(Locale.ENGLISH));
				}
			}
		}
		return this;
	}

	/**
	 * Removes JavaScript events on which Parsley should trigger client-side validation
	 *
	 * @param events
	 * @return
	 */
	public ParsleyValidationBehavior<T> off(String... events)
	{
		if (events != null)
		{
			for (String event : events)
			{
				if (Strings.isEmpty(event) == false)
				{
					triggerEvents.remove(event.toLowerCase(Locale.ENGLISH));
				}
			}
		}
		return this;
	}

	/**
	 * Writes the data-xyz attributes used by Parsley to know what kind
	 * of client side validation should be done for hosting form component
	 *
	 * @param component
	 *            the component that renders this tag currently
	 * @param tag
	 */
	@Override
	public void onComponentTag(Component component, ComponentTag tag)
	{
		super.onComponentTag(component, tag);

		if (triggerEvents.size() > 0)
		{
			String triggers = Strings.join(" ", triggerEvents);
			tag.put("data-trigger", triggers);
		}

		if (Strings.isEmpty(type) == false)
		{
			tag.put("data-type", type);
		}

		if (require != null)
		{
			tag.put("data-required", String.valueOf(require));
		}

		if (getHost().isRequired()) {

		}
	}

	/**
	 * Checks that the validator is used with a FormComponent
	 * @param component
	 */
	@Override
	public void bind(Component component)
	{
		super.bind(component);

		if (component instanceof FormComponent)
		{
			host = (FormComponent) component;
		}
		else
		{
			throw new IllegalArgumentException(String.format("%s doesn't support components of type '%s'",
					Classes.name(getClass()), Classes.name(component.getClass())));
		}
	}

	/**
	 * @return the form component which will be validated
	 */
	protected FormComponent getHost()
	{
		return host;
	}

	/**
	 * @return the server-side validator
	 */
	protected IValidator<T> getValidator()
	{
		return validator;
	}

	/**
	 * Validates the form component value.
	 *
	 * @param validatable
	 */
	@Override
	public void validate(IValidatable<T> validatable)
	{
		getValidator().validate(validatable);
	}
}
