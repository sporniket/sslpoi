/**
 * 
 */
package com.sporniket.scripting.sslpoi.vess;

import com.sporniket.scripting.sslpoi.core.InitialisationMode;

/**
 * Node for <code>define (identifier) as (new|null|undefined) (classname) </code> statements.
 * 
 * @author dsporn
 *
 */
public class VessNodeDefineAs extends VessNode
{
	/**
	 * <code>true</code> if this is an array.
	 */
	private boolean myArray;

	private final String myClassName;

	private final String myIdentifier;

	private final InitialisationMode myInitialisationMode;

	public VessNodeDefineAs(String identifier, InitialisationMode initialisationMode, String className)
	{
		myIdentifier = identifier;
		myInitialisationMode = initialisationMode;
		myClassName = className;
	}

	public String getClassName()
	{
		return myClassName;
	}

	public String getIdentifier()
	{
		return myIdentifier;
	}

	public InitialisationMode getInitialisationMode()
	{
		return myInitialisationMode;
	}

	public boolean isArray()
	{
		return myArray;
	}

	public void setArray(boolean array)
	{
		myArray = array;
	}

	public VessNodeDefineAs withArray(boolean array)
	{
		setArray(array);
		return this;
	}

}
