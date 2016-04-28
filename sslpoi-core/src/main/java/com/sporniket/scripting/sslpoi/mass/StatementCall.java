/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import java.util.List;
import java.util.Map;

/**
 * Statement <code>call method [from ...] [using expression as name [,...] ]</code>.
 * @author david
 *
 */
public class StatementCall extends Statement
{
	private final Map<String, PartialExpression> myArgumentMapping ;
	
	private final List<String> myMethodAccessor ;

	public StatementCall(List<String> methodAccessor, Map<String, PartialExpression> argumentMapping)
	{
		super();
		myMethodAccessor = methodAccessor;
		myArgumentMapping = argumentMapping;
	}

	public Map<String, PartialExpression> getArgumentMapping()
	{
		return myArgumentMapping;
	}

	public List<String> getMethodAccessor()
	{
		return myMethodAccessor;
	}

}
