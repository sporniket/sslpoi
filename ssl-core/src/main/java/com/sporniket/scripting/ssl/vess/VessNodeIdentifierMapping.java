/**
 * 
 */
package com.sporniket.scripting.ssl.vess;

/**
 * Store the identifiers mapping ('on ...', functions definition, ...)
 * @author dsporn
 *
 */
public class VessNodeIdentifierMapping extends VessNode
{
	/**
	 * <code>true</code> if this is an array.
	 */
	private boolean myArray;

	/**
	 * Type.
	 */
	private String myClassName;

	/**
	 * Mapped Identifier.
	 */
	private String myIdentifier;

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

	public void setArray(boolean array)
	{
		myArray = array;
	}

	public void setClassName(String className)
	{
		myClassName = className;
	}

	public void setIdentifier(String identifier)
	{
		myIdentifier = identifier;
	}

	public VessNodeIdentifierMapping withArray(boolean array)
	{
		setArray(array);
		return this;
	}

	public VessNodeIdentifierMapping withClassName(String className)
	{
		setClassName(className);
		return this;
	}

	public VessNodeIdentifierMapping withIdentifier(String identifier)
	{
		setIdentifier(identifier);
		return this;
	}

}
