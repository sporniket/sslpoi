/**
 * 
 */
package com.sporniket.scripting.ssl.vess;

/**
 * Node for storing an item of an access stack (object->sub-object->...->method/attribute)
 * 
 * @author dsporn
 *
 */
public class VessNodeAccessor extends VessNodeStringHolder implements VessNodeValue
{
	public VessNodeAccessor withValue(String value)
	{
		setValue(value);
		return this;
	}
}
