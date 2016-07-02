/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import com.sporniket.scripting.sslpoi.core.InitialisationMode;

/**
 * Statement <code>define (identifier) as (new|null|undefined) (classname) </code> statements.
 * 
 * @author dsporn
 *
 */
public class StatementDefineAs extends Statement
{
	private final PartialIdentifier myIdentifier;

	private final InitialisationMode myInitialisationMode;

	public StatementDefineAs(String identifier, InitialisationMode initialisationMode, String className, boolean isArray)
	{
		myIdentifier = new PartialIdentifier(identifier, className, isArray);
		myInitialisationMode = initialisationMode;
	}

	public PartialIdentifier getIdentifier()
	{
		return myIdentifier;
	}

	public InitialisationMode getInitialisationMode()
	{
		return myInitialisationMode;
	}
}
