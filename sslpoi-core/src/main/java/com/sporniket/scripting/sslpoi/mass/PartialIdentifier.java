/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

/**
 * Definition of an identifier : name and type (and whether it is an array or not).
 * 
 * @author dsporn
 *
 */
public class PartialIdentifier extends Partial
{
	/**
	 * <code>true</code> if this is an array.
	 */
	private final boolean myArray;

	/**
	 * A type identifier ("class"), e.g. "com.sporniket.UsefullData".
	 */
	private final String myClassName;

	private final String myIdentifier;

	public PartialIdentifier(String identifier, String className, boolean array)
	{
		myIdentifier = identifier;
		myClassName = className;
		myArray = array;
	}

	public String getClassName()
	{
		return myClassName;
	}

	public String getIdentifier()
	{
		return myIdentifier;
	}

	public boolean isArray()
	{
		return myArray;
	}

}
