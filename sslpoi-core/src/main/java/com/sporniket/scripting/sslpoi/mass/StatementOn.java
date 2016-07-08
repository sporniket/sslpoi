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
	private final List<PartialIdentifier> myArgumentMapping;

	private final String myEventName;

	public StatementOn(String eventName, List<PartialIdentifier> argumentMapping)
	{
		myEventName = eventName;
		myArgumentMapping = argumentMapping;
	}

	public List<PartialIdentifier> getArgumentMapping()
	{
		return myArgumentMapping;
	}

	public String getEventName()
	{
		return myEventName;
	}

}
