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
import com.sporniket.scripting.sslpoi.vess.VessNodeAccessor;
import com.sporniket.scripting.sslpoi.vess.VessNodeArgumentMapping;
import com.sporniket.scripting.sslpoi.vess.VessNodeIdentifierMapping;
import com.sporniket.scripting.sslpoi.vess.VessNodeValue;

/**
 * Utilities.
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

	static Map<String, PartialExpression> argumentMappingFromVessNodeArgumentMapping(VessNodeArgumentMapping source)
			throws SslpoiException
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

	static List<PartialIdentifier> identifierFromVessNodeIdentifierMapping(VessNodeIdentifierMapping mapping)
	{
		LinkedList<PartialIdentifier> _buffer = new LinkedList<PartialIdentifier>();
		for (VessNodeIdentifierMapping _current = mapping; _current != null;)
		{
			final PartialIdentifier _identifier = new PartialIdentifier(_current.getIdentifier(), _current.getClassName(),
					_current.isArray());
			_buffer.add(_identifier);
			_current = (VessNodeIdentifierMapping) _current.getNext();
		}
		return new ArrayList<PartialIdentifier>(_buffer);
	}

}
