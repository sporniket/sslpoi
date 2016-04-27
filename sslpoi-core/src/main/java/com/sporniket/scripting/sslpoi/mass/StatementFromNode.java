/**
 * 
 */
package com.sporniket.scripting.ssl.mass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.TreeSet;

import com.sporniket.scripting.ssl.core.NotImplementedYetException;
import com.sporniket.scripting.ssl.core.NotSupportedException;
import com.sporniket.scripting.ssl.core.SslpoiException;
import com.sporniket.scripting.ssl.vess.VessNode;
import com.sporniket.scripting.ssl.vess.VessNodeCall;
import com.sporniket.scripting.ssl.vess.VessNodeDefineAs;
import com.sporniket.scripting.ssl.vess.VessNodeIf;
import com.sporniket.scripting.ssl.vess.VessNodeOn;

/**
 * Convert {@link VessNode} into {@link Statement}.
 * 
 * @author david
 *
 */
public class StatementFromNode
{
	/**
	 * Name of the polymorphic method to use.
	 */
	private static final String METHOD_NAME__CONVERT = "doConvert";

	/**
	 * List of supported {@link VessNode} subclasses, to distinguish between {@link NotImplementedYetException} and
	 * {@link NotSupportedException}.
	 */
	private static final String[] SET__SUPPORTED_NODE_CLASSES =
	{
			VessNodeCall.class.getName(),
			VessNodeDefineAs.class.getName(),
			VessNodeIf.class.getName(),
			VessNodeOn.class.getName()
	};

	private static final StatementFromNode THE_INSTANCE = new StatementFromNode();

	/**
	 * Convert the given node into a statement.
	 * 
	 * @param node
	 *            the node to convert.
	 * @return the statement.
	 * @throws SslpoiException
	 *             when there is a problem.
	 */
	public static final Statement convert(VessNode node) throws SslpoiException
	{
		return THE_INSTANCE.findAndInvokeConverterForNode(node);
	}

	public static StatementDefineAs doConvert(VessNodeDefineAs node)
	{
		return new StatementDefineAs(node.getIdentifier(), node.getInitialisationMode(), node.getClassName(), node.isArray());
	}

	/**
	 * Supported nodes.
	 */
	private Set<String> mySupportedNodes = new TreeSet<String>();

	private StatementFromNode()
	{
		for (String _supported : SET__SUPPORTED_NODE_CLASSES)
		{
			getSupportedNodes().add(_supported);
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
	private Statement findAndInvokeConverterForNode(VessNode node) throws SslpoiException
	{
		Statement _result = null;
		try
		{
			Method _converter = getClass().getMethod(METHOD_NAME__CONVERT, node.getClass());
			_result = (Statement) _converter.invoke(null, node);
		}
		catch (InvocationTargetException _exception)
		{
			throw (SslpoiException) _exception.getCause();
		}
		catch (NoSuchMethodException _exception)
		{
			final String _nodeType = node.getClass().getName();
			if (getSupportedNodes().contains(_nodeType))
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

	public Set<String> getSupportedNodes()
	{
		return mySupportedNodes;
	}
}
