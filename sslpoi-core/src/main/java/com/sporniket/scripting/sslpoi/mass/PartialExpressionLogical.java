/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import com.sporniket.scripting.sslpoi.core.LogicalOperator;

/**
 * Store a logical expression (<code>expression-left [not] logical-operator expression-right</code>).
 * @author dsporn
 *
 */
public class PartialExpressionLogical extends PartialExpression
{
	private final PartialExpression myLeftExpression;

	private final boolean myNot;

	private final LogicalOperator myOperator;

	private final PartialExpression myRightExpression;

	/**
	 * Create a fully defined logical expression.
	 * 
	 * @param not
	 *            <code>true</code> if the logical expression is <code>not operator</code>.
	 * @param operator
	 *            the logical operator.
	 * @param leftExpression
	 *            the left expression.
	 * @param rightExpression
	 *            the right expression.
	 */
	public PartialExpressionLogical(boolean not, LogicalOperator operator, PartialExpression leftExpression,
			PartialExpression rightExpression)
	{
		myNot = not;
		myOperator = operator;
		myLeftExpression = leftExpression;
		myRightExpression = rightExpression;
	}

	public PartialExpression getLeftExpression()
	{
		return myLeftExpression;
	}

	public LogicalOperator getOperator()
	{
		return myOperator;
	}

	public PartialExpression getRightExpression()
	{
		return myRightExpression;
	}

	public boolean isNot()
	{
		return myNot;
	}

}
