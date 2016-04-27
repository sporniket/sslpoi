/**
 * 
 */
package com.sporniket.scripting.sslpoi.vess;

/**
 * @author dsporn
 *
 */
public class VessNodeOn extends VessNode
{
	/**
	 * Identifier for the event name.
	 */
	private String myEventName;

	/**
	 * identifier mapping.
	 */
	private VessNodeIdentifierMapping myMapping;

	/**
	 * sublist of statements.
	 */
	private VessNode myStatements;

	public String getEventName()
	{
		return myEventName;
	}

	public VessNodeIdentifierMapping getMapping()
	{
		return myMapping;
	}

	public VessNode getStatements()
	{
		return myStatements;
	}

	public void setEventName(String eventName)
	{
		myEventName = eventName;
	}

	public void setMapping(VessNodeIdentifierMapping mapping)
	{
		myMapping = mapping;
	}

	public void setStatements(VessNode statements)
	{
		myStatements = statements;
	}

	public VessNodeOn withEventName(String eventName)
	{
		setEventName(eventName);
		return this ;
	}

	public VessNodeOn withMapping(VessNodeIdentifierMapping mapping)
	{
		setMapping(mapping);
		return this;
	}

	public VessNodeOn withStatements(VessNode statements)
	{
		setStatements(statements);
		return this;
	}

}
