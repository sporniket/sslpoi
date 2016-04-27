/**
 * 
 */
package com.sporniket.scripting.sslpoi.vess;

/**
 * VessNode containing a simple String, the kind of the string will be identified by subclasses.
 * 
 * @author dsporn
 *
 */
public abstract class VessNodeStringHolder extends VessNode
{
	/**
	 * The stored value.
	 */
	private String myValue;

	public String getValue()
	{
		return myValue;
	}

	public void setValue(String value)
	{
		myValue = value;
	}

}
