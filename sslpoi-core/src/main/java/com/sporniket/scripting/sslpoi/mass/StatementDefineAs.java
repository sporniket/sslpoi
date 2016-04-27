/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import com.sporniket.scripting.sslpoi.core.InitialisationMode;

/**
 * Node for <code>define (identifier) as (new|null|undefined) (classname) </code> statements.
 * 
 * @author dsporn
 *
 */
public class StatementDefineAs extends Statement
{
	/**
	 * <code>true</code> if this is an array.
	 */
	private boolean myArray;

	private final String myClassName;

	private final String myIdentifier;

	private final InitialisationMode myInitialisationMode;

	public StatementDefineAs(String identifier, InitialisationMode initialisationMode, String className, boolean isArray)
	{
		myIdentifier = identifier;
		myInitialisationMode = initialisationMode;
		myClassName = className;
		myArray = isArray;
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
}
