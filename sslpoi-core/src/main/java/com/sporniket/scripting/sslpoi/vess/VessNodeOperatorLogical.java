/**
 * 
 */
package com.sporniket.scripting.ssl.vess;

import com.sporniket.scripting.ssl.core.LogicalOperator;

/**
 * Node for a logical operator : a {@link LogicalOperator} that can be reversed ("not").
 * 
 * @author dsporn
 *
 */
public class VessNodeOperatorLogical extends VessNode
{
	/**
	 * <code>true</code> if the operator must be reversed.
	 */
	private boolean myNot;

	/**
	 * the operator.
	 */
	private LogicalOperator myOperator;

	public LogicalOperator getOperator()
	{
		return myOperator;
	}

	public boolean isNot()
	{
		return myNot;
	}

	public void setNot(boolean not)
	{
		myNot = not;
	}

	public void setOperator(LogicalOperator operator)
	{
		myOperator = operator;
	}

	public VessNodeOperatorLogical withNot(boolean not)
	{
		setNot(not);
		return this;
	}

	public VessNodeOperatorLogical withOperator(LogicalOperator operator)
	{
		setOperator(operator);
		return this;
	}
}
