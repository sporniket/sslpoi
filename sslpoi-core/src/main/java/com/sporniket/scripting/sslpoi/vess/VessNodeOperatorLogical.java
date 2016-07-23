/**
 * 
 */
package com.sporniket.scripting.sslpoi.vess;

import com.sporniket.scripting.sslpoi.core.LogicalOperator;

/**
 * Node for a logical operator : a {@link LogicalOperator} that can be reversed ("not").
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
