/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sporniket.scripting.sslpoi.core.SslpoiException;
import com.sporniket.scripting.sslpoi.vess.VessNode;
import com.sporniket.scripting.sslpoi.vess.VessNodeAccessor;
import com.sporniket.scripting.sslpoi.vess.VessNodeArgumentMapping;
import com.sporniket.scripting.sslpoi.vess.VessNodeIdentifierMapping;
import com.sporniket.scripting.sslpoi.vess.VessNodeLiteralString;
import com.sporniket.scripting.sslpoi.vess.VessNodeValue;

/**
 * Utilities.
 * 
 * @author david
 *
 */
final class Utils
{
	static List<String> accessorFromVessNodeAccessor(VessNodeAccessor source)
	{
		LinkedList<String> _buffer = new LinkedList<String>();
		for (VessNodeAccessor _current = source; _current != null;)
		{
			_buffer.add(_current.getValue());
			_current = (VessNodeAccessor) _current.getNext();
		}
		final ArrayList<String> _accessStack = new ArrayList<String>(_buffer);
		return _accessStack;
	}

	static Map<String, PartialExpression> argumentMappingFromVessNodeArgumentMapping(VessNodeArgumentMapping source) throws SslpoiException
	{
		Map<String, PartialExpression> _buffer = new HashMap<String, PartialExpression>();
		for (VessNodeArgumentMapping _current = source; _current != null;)
		{
			final VessNodeValue _value = (VessNodeValue) _current.getValue();
			PartialExpression _expression = PartialExpressionFromNodeValue.convert(_value);
			_buffer.put(_current.getName(), _expression);
			_current = (VessNodeArgumentMapping) _current.getNext();
		}
		return new HashMap<String, PartialExpression>(_buffer);
	}
}
