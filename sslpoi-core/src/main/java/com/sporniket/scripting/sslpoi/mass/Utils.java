/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.sporniket.scripting.sslpoi.vess.VessNode;
import com.sporniket.scripting.sslpoi.vess.VessNodeAccessor;
import com.sporniket.scripting.sslpoi.vess.VessNodeArgumentMapping;
import com.sporniket.scripting.sslpoi.vess.VessNodeLiteralString;

/**
 * Utilities.
 * 
 * @author david
 *
 */
final class Utils
{

	static ArrayList<String> accessorFromVessNodeAccessor(VessNodeAccessor source)
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

	static Map<String, PartialExpression> argumentMappingFromVessNodeArgumentMapping(VessNodeArgumentMapping source)
	{
		Map<String, PartialExpression> _buffer = new HashMap<String, PartialExpression>();
		for (VessNodeArgumentMapping _current = source; _current != null;)
		{
			final VessNode _value = _current.getValue();
			if (_value instanceof VessNodeLiteralString)
			{
				final String _literalValue = ((VessNodeLiteralString) _current.getValue()).getValue();
				final PartialExpressionLiteralString _expression = new PartialExpressionLiteralString(_literalValue);
				_buffer.put(_current.getName(), _expression);
			}
			else if (_value instanceof VessNodeAccessor)
			{
				final ArrayList<String> _accessor = accessorFromVessNodeAccessor((VessNodeAccessor) source.getValue());
				final PartialExpressionAccessor _expression = new PartialExpressionAccessor(_accessor);
				_buffer.put(_current.getName(), _expression);
			}
			_current = (VessNodeArgumentMapping) _current.getNext();
		}
		return new HashMap<String, PartialExpression>(_buffer);
	}

}
