package com.mycompany.parsley;

import org.apache.wicket.validation.validator.UrlValidator;

/**
 *
 */
public class ParsleyUrlValidator extends ParsleyValidationBehavior<String>
{
	public ParsleyUrlValidator()
	{
		super(new UrlValidator());

		require(true);
		type("urlstrict");
	}
}
