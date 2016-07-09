/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

/**
 * Model for a logical expression and statements if the test is true.
 * 
 * @author dsporn
 *
 */
public class StatementAlternative extends StatementContainer
{
	private final PartialExpressionLogical myTest;

	public StatementAlternative(PartialExpressionLogical test)
	{
		myTest = test;
	}

	public PartialExpressionLogical getTest()
	{
		return myTest;
	}
}
