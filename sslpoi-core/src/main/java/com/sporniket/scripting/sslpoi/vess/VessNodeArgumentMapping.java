/**
 * 
 */
package com.sporniket.scripting.ssl.vess;

/**
 * Maps one argument name to a value (literal/accessor)
 * 
 * @author dsporn
 *
 */
public class VessNodeArgumentMapping extends VessNode
{
	/**
	 * Mapping name.
	 */
	private String myName;

	/**
	 * Mapped value.
	 */
	private VessNode myValue;

	public String getName()
	{
		return myName;
	}

	public VessNode getValue()
	{
		return myValue;
	}

	public void setName(String name)
	{
		myName = name;
	}

	public void setValue(VessNode value)
	{
		myValue = value;
	}

	public VessNodeArgumentMapping withName(String name)
	{
		setName(name);
		return this;
	}

	public VessNodeArgumentMapping withValue(VessNode value)
	{
		setValue(value);
		return this;
	}
}
