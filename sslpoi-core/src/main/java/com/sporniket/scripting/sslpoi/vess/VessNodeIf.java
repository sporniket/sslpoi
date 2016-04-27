/**
 * 
 */
package com.sporniket.scripting.ssl.vess;

/**
 * Node for "if ... else if ... else ...".
 * 
 * @author dsporn
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
