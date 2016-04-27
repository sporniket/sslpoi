/**
 * 
 */
package com.sporniket.scripting.ssl.vess;

/**
 * Keyword Token.
 * 
 * @author dsporn
 *
 */
public class TokenKeyword extends Token
{
	private final Keywords myKeyword;

	public TokenKeyword(Keywords keyword)
	{
		myKeyword = keyword;
	}

	public Keywords getKeyword()
	{
		return myKeyword;
	}

}
