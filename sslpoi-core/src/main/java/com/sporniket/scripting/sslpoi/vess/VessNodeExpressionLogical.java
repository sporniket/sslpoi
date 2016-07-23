/**
 * 
 */
package com.sporniket.scripting.sslpoi.vess;


/**
 * Node for a logical expression (<code>[value] is ... [expected]</code>).
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
public class VessNodeExpressionLogical extends VessNode implements VessNodeValue
{
	/**
	 * Right hand expression.
	 */
	private VessNodeValue myExpected;

	/**
	 * Operator.
	 */
	private VessNodeOperatorLogical myOperator;

	/**
	 * Left hand expression.
	 */
	private VessNodeValue myValue;

	public VessNodeValue getExpected()
	{
		return myExpected;
	}

	public VessNodeOperatorLogical getOperator()
	{
		return myOperator;
	}

	public VessNodeValue getValue()
	{
		return myValue;
	}

	public void setExpected(VessNodeValue expected)
	{
		myExpected = expected;
	}

	public void setOperator(VessNodeOperatorLogical operator)
	{
		myOperator = operator;
	}

	public void setValue(VessNodeValue value)
	{
		myValue = value;
	}

	public VessNodeExpressionLogical withExpected(VessNodeValue expected)
	{
		setExpected(expected);
		return this;
	}

	public VessNodeExpressionLogical withOperator(VessNodeOperatorLogical operator)
	{
		setOperator(operator);
		return this;
	}

	public VessNodeExpressionLogical withValue(VessNodeValue value)
	{
		setValue(value);
		return this;
	}

}
