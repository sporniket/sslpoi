/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import java.util.ArrayList;
import java.util.List;

/**
 * Statement <code>if ... [else if ...] [else if ...]...[else ...]</code>.
 * 
 * @author dsporn
 *
 */
public class StatementIf extends StatementContainer
{
	private final List<StatementAlternative> myAlternatives;

	public StatementIf(List<StatementAlternative> alternatives)
	{
		myAlternatives = new ArrayList<StatementAlternative>(alternatives);
	}

	public List<StatementAlternative> getAlternatives()
	{
		return myAlternatives;
	}

}
