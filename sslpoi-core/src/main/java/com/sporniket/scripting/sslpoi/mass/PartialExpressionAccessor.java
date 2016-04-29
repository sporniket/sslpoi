/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import java.util.List;

/**
 * Store an expression that is an accessor (<code>objet1->...->target</code>).
 * @author david
 *
 */
public class PartialExpressionAccessor extends PartialExpression
{
	
	private final List<String> myAccessStack ;

	public PartialExpressionAccessor(List<String> accessStack)
	{
		super();
		myAccessStack = accessStack;
	}

	public List<String> getAccessStack()
	{
		return myAccessStack;
	}

}
