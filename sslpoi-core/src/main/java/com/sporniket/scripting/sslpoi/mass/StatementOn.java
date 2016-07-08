/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import java.util.List;

/**
 * Statement <code>on eventName [with argumentMapping] ...</code>.
 * 
 * @author dsporn
 *
 */
public class StatementOn extends StatementContainer
{
	private final List<PartialIdentifier> myIdentifierMapping;

	private final String myEventName;

	public StatementOn(String eventName, List<PartialIdentifier> argumentMapping)
	{
		myEventName = eventName;
		myIdentifierMapping = argumentMapping;
	}

	public List<PartialIdentifier> getIdentifierMapping()
	{
		return myIdentifierMapping;
	}

	public String getEventName()
	{
		return myEventName;
	}

}
