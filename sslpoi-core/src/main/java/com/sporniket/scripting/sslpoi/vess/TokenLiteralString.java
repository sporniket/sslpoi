/**
 * 
 */
package com.sporniket.scripting.ssl.vess;

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
