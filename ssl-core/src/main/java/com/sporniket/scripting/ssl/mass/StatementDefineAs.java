/**
 * 
 */
package com.sporniket.scripting.ssl.mass;

import com.sporniket.scripting.ssl.core.InitialisationMode;

/**
 * Node for <code>define (identifier) as (new|null|undefined) (classname) </code> statements.
 * 
 * @author dsporn
 *
 */
public class StatementDefineAs extends Statement
{
	private final String myClassName;

	private final String myIdentifier;

	private final InitialisationMode myInitialisationMode;

	public StatementDefineAs(String identifier, InitialisationMode initialisationMode, String className)
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

}
