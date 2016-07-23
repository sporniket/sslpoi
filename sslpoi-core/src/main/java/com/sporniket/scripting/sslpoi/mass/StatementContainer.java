/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for statement containing a list of statements.
 * 
 * <p>
 * &copy; Copyright 2015-2016 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>Sporny Script Language (Pun Obviously Intended) &#8211; core</i>.
 * 
 * <p>
 * <i>Sporny Script Language (Pun Obviously Intended) &#8211; core</i> is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * <p>
 * <i>Sporny Script Language (Pun Obviously Intended) &#8211; core</i> is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>Sporny Script Language (Pun Obviously
 * Intended) &#8211; core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>.
 * 
 * <hr>
 * 
 * @author David SPORN
 * @version 0.1.0
 * @since 0.1.0
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
