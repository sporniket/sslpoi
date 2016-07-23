/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.sporniket.scripting.sslpoi.core.NotImplementedYetException;
import com.sporniket.scripting.sslpoi.core.NotSupportedException;
import com.sporniket.scripting.sslpoi.core.SslpoiException;
import com.sporniket.scripting.sslpoi.vess.VessNodeAccessor;
import com.sporniket.scripting.sslpoi.vess.VessNodeExpressionLogical;
import com.sporniket.scripting.sslpoi.vess.VessNodeLiteralString;
import com.sporniket.scripting.sslpoi.vess.VessNodeValue;

/**
 * Convert a supported {@link VessNodeValue} into a {@link PartialExpression}.
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
public class PartialExpressionFromNodeValue
{
	/**
	 * Stores the converter methods.
	 * 
	 * Methods are public to simplify the reflection code to find and invoke those converters.
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
	 * <i>Sporny Script Language (Pun Obviously Intended) &#8211; core</i> is free software: you can redistribute it and/or modify
	 * it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of
	 * the License, or (at your option) any later version.
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
	private static final class Converter
	{

		/**
		 * @param value
		 *            value to convert.
		 * @return a {@link PartialExpressionAccessor}.
		 */
		@SuppressWarnings("unused")
		public static PartialExpression doConvert(VessNodeAccessor value)
		{
			final List<String> _accessor = Utils.accessorFromVessNodeAccessor(value);
			final PartialExpressionAccessor _expression = new PartialExpressionAccessor(_accessor);
			return _expression;
		}

		/**
		 * @param value
		 *            value to convert.
		 * @return a {@link PartialExpressionLiteralString}.
		 */
		@SuppressWarnings("unused")
		public static PartialExpression doConvert(VessNodeLiteralString value)
		{
			final String _literalValue = value.getValue();
			final PartialExpressionLiteralString _expression = new PartialExpressionLiteralString(_literalValue);
			return _expression;
		}

		/**
		 * @param value
		 *            value to convert.
		 * @return a {@link PartialExpressionLogical}.
		 * @throws SslpoiException
		 *             when there is a problem.
		 */
		@SuppressWarnings("unused")
		public static PartialExpression doConvert(VessNodeExpressionLogical value) throws SslpoiException
		{
			PartialExpression _left = PartialExpressionFromNodeValue.convert(value.getValue());
			PartialExpression _right = PartialExpressionFromNodeValue.convert(value.getExpected());
			return new PartialExpressionLogical(value.getOperator().isNot(), value.getOperator().getOperator(), _left, _right);
		}
	}

	/**
	 * Name of the polymorphic method to use.
	 */
	private static final String METHOD_NAME__CONVERT = "doConvert";

	/**
	 * List of supported {@link VessNodeValue} implementations, to distinguish between {@link NotImplementedYetException} and
	 * {@link NotSupportedException}.
	 */
	private static final String[] SET__SUPPORTED_NODE_CLASSES =
	{
			VessNodeLiteralString.class.getName(), VessNodeAccessor.class.getName()
	};

	private static final PartialExpressionFromNodeValue THE_INSTANCE = new PartialExpressionFromNodeValue();

	/**
	 * Convert the given node into a statement.
	 * 
	 * @param node
	 *            the node to convert.
	 * @return the statement.
	 * @throws SslpoiException
	 *             when there is a problem.
	 */
	public static final PartialExpression convert(VessNodeValue node) throws SslpoiException
	{
		return THE_INSTANCE.findAndInvokeConverterForNodeValue(node);
	}

	/**
	 * Supported nodes.
	 */
	private Set<String> mySupportedNodeValues = new TreeSet<String>();

	private PartialExpressionFromNodeValue()
	{
		for (String _supported : SET__SUPPORTED_NODE_CLASSES)
		{
			getSupportedNodeValues().add(_supported);
		}
	}

	/**
	 * Implements a polymorphic call at runtime for converting a node.
	 * 
	 * @param node
	 *            the node to convert.
	 * @return the statement.
	 * @throws SslpoiException
	 *             when there is a problem.
	 */
	private PartialExpression findAndInvokeConverterForNodeValue(VessNodeValue node) throws SslpoiException
	{
		PartialExpression _result = null;
		try
		{
			Method _converter = Converter.class.getMethod(METHOD_NAME__CONVERT, node.getClass());
			_result = (PartialExpression) _converter.invoke(null, node);
		}
		catch (InvocationTargetException _exception)
		{
			throw (SslpoiException) _exception.getCause();
		}
		catch (NoSuchMethodException _exception)
		{
			final String _nodeType = node.getClass().getName();
			if (getSupportedNodeValues().contains(_nodeType))
			{
				throw new NotImplementedYetException(_nodeType);
			}
			throw new NotSupportedException(_nodeType);
		}
		catch (SecurityException | IllegalAccessException | IllegalArgumentException _exception)
		{
			throw new NotSupportedException(_exception);
		}
		return _result;
	}

	private Set<String> getSupportedNodeValues()
	{
		return mySupportedNodeValues;
	}
}
