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
public class TokenIdentifier extends Token
{
	private final String myIdentifier;

	public TokenIdentifier(String value)
	{
		myIdentifier = value;
	}

	public String getIdentifier()
	{
		return myIdentifier;
	}
}
