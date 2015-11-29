/**
 * 
 */
package com.sporniket.scripting.ssl.vess;

/**
 * Call a method with or without parameters.
 * 
 * @author dsporn
 *
 */
public class VessNodeCall extends VessNode
{
	private VessNodeAccessor myCall;

	private VessNodeArgumentMapping myMapping;

	public VessNodeAccessor getCall()
	{
		return myCall;
	}

	public VessNodeArgumentMapping getMapping()
	{
		return myMapping;
	}

	public void setCall(VessNodeAccessor call)
	{
		myCall = call;
	}

	public void setMapping(VessNodeArgumentMapping mapping)
	{
		myMapping = mapping;
	}

	public VessNodeCall withCall(VessNodeAccessor call)
	{
		setCall(call);
		return this;
	}

	public VessNodeCall withMapping(VessNodeArgumentMapping mapping)
	{
		setMapping(mapping);
		return this;
	}
}
