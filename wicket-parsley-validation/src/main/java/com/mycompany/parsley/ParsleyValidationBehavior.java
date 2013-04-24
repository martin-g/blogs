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
 *
 */
public class ParsleyValidationBehavior<T> extends Behavior implements IValidator<T>
{
	private FormComponent host;

	private final IValidator<T> validator;

	private final List<String> triggerEvents;

	private String type;

	/**
	 * Three states:
	 * null - do not set data-require
	 * true|false - set the value
	 */
	private Boolean require;

	public ParsleyValidationBehavior()
	{
		this(new CompoundValidator<T>());
	}

	public ParsleyValidationBehavior(IValidator<T> validator)
	{
		this.validator = Args.notNull(validator, "validator");

		this.triggerEvents = new ArrayList<String>();
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		response.render(JavaScriptHeaderItem.forReference(new ParsleyJsReference()));
	}

	public ParsleyValidationBehavior<T> require(Boolean require)
	{
		this.require = require;
		return this;
	}

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		if (require != null)
		{
			getHost().setRequired(require);
		}
	}

	public ParsleyValidationBehavior<T> type(String type)
	{
		this.type = type;
		return this;
	}

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

	protected FormComponent getHost()
	{
		return host;
	}

	protected IValidator<T> getValidator()
	{
		return validator;
	}

	@Override
	public void validate(IValidatable<T> validatable)
	{
		getValidator().validate(validatable);
	}
}
