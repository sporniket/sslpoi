/**
 * 
 */
package com.sporniket.scripting.sslpoi.vess;

/**
 * Node for storing a literal String.
 * 
 * @author dsporn
 *
 */
public class VessNodeLiteralString extends VessNodeStringHolder implements VessNodeValue
{
	public VessNodeLiteralString withValue(String value)
	{
		setValue(value);
		return this;
	}
}
