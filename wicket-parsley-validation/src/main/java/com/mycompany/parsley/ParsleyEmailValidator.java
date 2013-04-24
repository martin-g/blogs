package com.mycompany.parsley;

import org.apache.wicket.validation.validator.EmailAddressValidator;

/**
 *
 */
public class ParsleyEmailValidator extends ParsleyValidationBehavior<String>
{
	public ParsleyEmailValidator()
	{
		super(EmailAddressValidator.getInstance());

		require(true);
		type("email");
	}
}
