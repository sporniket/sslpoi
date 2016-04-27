/**
 * 
 */
package com.sporniket.scripting.sslpoi.vess;

/**
 * Literal String token.
 * 
 * @author dsporn
 *
 */
public class TokenLiteralString extends Token
{
	private final String myValue;

	public TokenLiteralString(String value)
	{
		myValue = value;
	}

	public String getValue()
	{
		return myValue;
	}
}
