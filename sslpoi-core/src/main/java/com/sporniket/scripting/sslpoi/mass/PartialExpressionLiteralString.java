/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

/**
 * Store a literal String.
 * 
 * @author david
 *
 */
public final class PartialExpressionLiteralString extends PartialExpression
{
	private final String myValue;

	public PartialExpressionLiteralString(String value)
	{
		super();
		myValue = value;
	}

	public String getValue()
	{
		return myValue;
	}

}
