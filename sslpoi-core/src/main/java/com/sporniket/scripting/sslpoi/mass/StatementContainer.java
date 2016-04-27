/**
 * 
 */
package com.sporniket.scripting.ssl.mass;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for statement containing a list of statements.
 * 
 * @author dsporn
 *
 */
public abstract class StatementContainer extends Statement
{
	private List<Statement> myStatements;

	/**
	 * Get the statements list.
	 * 
	 * @return the list of statement, never <code>null</code>.
	 */
	public List<Statement> getStatements()
	{
		if (null == myStatements)
		{
			myStatements = new ArrayList<Statement>(0);
		}
		return myStatements;
	}

	/**
	 * Set the statements list.
	 * 
	 * @param statements
	 */
	public void setStatements(List<Statement> statements)
	{
		ArrayList<Statement> _new = new ArrayList<Statement>(statements);
		myStatements = _new;
	}
}
