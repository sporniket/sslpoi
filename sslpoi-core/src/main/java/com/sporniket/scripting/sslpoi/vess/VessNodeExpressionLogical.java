/**
 * 
 */
package com.sporniket.scripting.sslpoi.vess;

import com.sporniket.scripting.sslpoi.core.LogicalOperator;

/**
 * Node for a logical expression (<code>[value] is ... [expected]</code>).
 * 
 * @author dsporn
 *
 */
public class VessNodeExpressionLogical extends VessNode
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
