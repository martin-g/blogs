package com.mycompany.parsley;

/**
 *
 */
public class ParsleyRequiredValidator<T> extends ParsleyValidationBehavior<T>
{
	public ParsleyRequiredValidator()
	{
		super();

		require(Boolean.TRUE);
	}
}
