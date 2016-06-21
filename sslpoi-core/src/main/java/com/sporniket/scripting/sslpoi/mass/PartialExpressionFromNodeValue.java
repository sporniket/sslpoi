/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import com.sporniket.scripting.sslpoi.core.NotImplementedYetException;
import com.sporniket.scripting.sslpoi.core.NotSupportedException;
import com.sporniket.scripting.sslpoi.core.SslpoiException;
import com.sporniket.scripting.sslpoi.vess.VessNodeAccessor;
import com.sporniket.scripting.sslpoi.vess.VessNodeLiteralString;
import com.sporniket.scripting.sslpoi.vess.VessNodeValue;

/**
 * Convert a supported {@link VessNodeValue} into a {@link PartialExpression}.
 * 
 * @author dsporn
 *
 */
public class PartialExpressionFromNodeValue
{
	/**
	 * Stores the converter methods.
	 * 
	 * Methods are public to simplify the reflection code to find and invoke those converters.
	 * 
	 * @author david
	 *
	 */
	private static final class Converter
	{

		/**
		 * @param value
		 *            value to convert.
		 * @return a {@link PartialExpressionAccessor}
		 */
		@SuppressWarnings("unused")
		public static PartialExpression doConvert(VessNodeAccessor value)
		{
			final ArrayList<String> _accessor = Utils.accessorFromVessNodeAccessor(value);
			final PartialExpressionAccessor _expression = new PartialExpressionAccessor(_accessor);
			return _expression;
		}

		/**
		 * @param value
		 *            value to convert.
		 * @return a {@link PartialExpressionLiteralString}
		 */
		@SuppressWarnings("unused")
		public static PartialExpression doConvert(VessNodeLiteralString value)
		{
			final String _literalValue = value.getValue();
			final PartialExpressionLiteralString _expression = new PartialExpressionLiteralString(_literalValue);
			return _expression;
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
