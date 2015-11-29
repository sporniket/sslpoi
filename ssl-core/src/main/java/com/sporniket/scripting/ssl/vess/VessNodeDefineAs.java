/**
 * 
 */
package com.sporniket.scripting.ssl.vess;

import com.sporniket.scripting.ssl.core.InitialisationMode;

/**
 * Node for <code>define (identifier) as (new|null|undefined) (classname) </code> statements.
 * 
 * @author dsporn
 *
 */
public class VessNodeDefineAs extends VessNode
{
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

}
