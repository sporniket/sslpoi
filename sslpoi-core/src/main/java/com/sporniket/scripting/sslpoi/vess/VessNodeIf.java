/**
 * 
 */
package com.sporniket.scripting.sslpoi.vess;

/**
 * Node for "if ... else if ... else ...".
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
public class VessNodeIf extends VessNode
{
	/**
	 * sibling <code>else if</code> and <code>else</code> are linked to the first <code>if</code>.
	 */
	private VessNodeIf myAlternative;

	/**
	 * sublist of statements.
	 */
	private VessNode myStatements;

	/**
	 * <code>null</code> for the <code>else</code> section.
	 */
	private VessNodeExpressionLogical myTest;

	public VessNodeIf getAlternative()
	{
		return myAlternative;
	}

	public VessNode getStatements()
	{
		return myStatements;
	}

	public VessNodeExpressionLogical getTest()
	{
		return myTest;
	}

	public boolean isLastAlternative()
	{
		return (null == getAlternative());
	}

	public void setAlternative(VessNodeIf alternative)
	{
		myAlternative = alternative;
	}

	public void setStatements(VessNode statements)
	{
		myStatements = statements;
	}

	public void setTest(VessNodeExpressionLogical test)
	{
		myTest = test;
	}

	/**
	 * Append the next alternative (if/else if/else)
	 * 
	 * @param alternative
	 *            the alternative.
	 * @return this node.
	 */
	public VessNodeIf withAlternative(VessNodeIf alternative)
	{
		if (isLastAlternative())
		{
			setAlternative(alternative);
		}
		else
		{
			getAlternative().withAlternative(alternative);
		}
		return this;
	}

	/**
	 * Append the given statement to the statement list.
	 * 
	 * @param statement
	 *            the statement to add.
	 * @return this node.
	 */
	public VessNodeIf withStatement(VessNode statement)
	{
		if (null == getStatements())
		{
			setStatements(statement);
		}
		else
		{
			getStatements().withLastNode(statement);
		}
		return this;
	}

	public VessNodeIf withTest(VessNodeExpressionLogical test)
	{
		setTest(test);
		return this;
	}
}
