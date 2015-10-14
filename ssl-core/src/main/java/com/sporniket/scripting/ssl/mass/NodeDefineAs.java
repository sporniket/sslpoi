/**
 * 
 */
package com.sporniket.scripting.ssl.mass;

/**
 * Node for <code>define (identifier) as (new|null|undefined) (classname) </code> statements.
 * 
 * @author dsporn
 *
 */
public class NodeDefineAs extends Node
{
	/**
	 * Specify what is the initial value.
	 * @author dsporn
	 *
	 */
	public static enum InitialisationMode
	{
		NEW,
		NULL,
		UNDEFINED;
	}
	
	private final String myClassName ;
	private final String myIdentifier ;
	private final InitialisationMode myInitialisationMode ;


	public NodeDefineAs(String identifier, InitialisationMode initialisationMode, String className)
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
