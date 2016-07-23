/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import com.sporniket.scripting.sslpoi.core.LogicalOperator;

/**
 * Store a logical expression : <code>expression-left [not] logical-operator expression-right</code>.
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
