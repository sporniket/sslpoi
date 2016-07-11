/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.sporniket.scripting.sslpoi.core.NotImplementedYetException;
import com.sporniket.scripting.sslpoi.core.NotSupportedException;
import com.sporniket.scripting.sslpoi.core.SslpoiException;
import com.sporniket.scripting.sslpoi.vess.VessNode;
import com.sporniket.scripting.sslpoi.vess.VessNodeCall;
import com.sporniket.scripting.sslpoi.vess.VessNodeDefineAs;
import com.sporniket.scripting.sslpoi.vess.VessNodeIf;
import com.sporniket.scripting.sslpoi.vess.VessNodeOn;

/**
 * Convert {@link VessNode} into {@link Statement}.
 * 
 * @author david
 *
 */
public class StatementFromNode
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

		@SuppressWarnings("unused")
		public static StatementCall doConvert(VessNodeCall node, ConvertionContext context) throws SslpoiException
		{
			return new StatementCall(Utils.accessorFromVessNodeAccessor(node.getCall()),
					Utils.argumentMappingFromVessNodeArgumentMapping(node.getMapping()));
		}

		@SuppressWarnings("unused")
		public static StatementDefineAs doConvert(VessNodeDefineAs node, ConvertionContext context)
		{
			return new StatementDefineAs(node.getIdentifier(), node.getInitialisationMode(), node.getClassName(), node.isArray());
		}

		@SuppressWarnings("unused")
		public static StatementIf doConvert(VessNodeIf node, ConvertionContext context) throws SslpoiException
		{
			List<StatementAlternative> _alternatives = new LinkedList<StatementAlternative>();
			for (VessNodeIf _alternativeNode = node; _alternativeNode != null;)
			{
				StatementAlternative _alternative;
				if (null == _alternativeNode.getTest())
				{
					// the 'else' part is the last node and have no test.
					if (_alternatives.isEmpty())
					{
						throw new SslpoiException("There must be at least one alternative before 'else'");
					}
					if (!_alternativeNode.isLastAlternative())
					{
						throw new SslpoiException("The 'else' part must be the last alternative");
					}

					_alternative = new StatementAlternative(null);
				}
				else
				{
					PartialExpressionLogical _test = (PartialExpressionLogical) PartialExpressionFromNodeValue
							.convert(_alternativeNode.getTest());
					_alternative = new StatementAlternative(_test);
				}

				_alternative.setStatements(StatementFromNode.convertNodeList(_alternativeNode.getStatements(), context));

				_alternatives.add(_alternative);

				_alternativeNode = _alternativeNode.getAlternative();
			}

			return new StatementIf(_alternatives);
		}

		@SuppressWarnings("unused")
		public static StatementOn doConvert(VessNodeOn node, ConvertionContext context) throws SslpoiException
		{
			List<PartialIdentifier> _identifierMapping = Utils.identifierFromVessNodeIdentifierMapping(node.getMapping());

			StatementOn _result = new StatementOn(node.getEventName(), _identifierMapping);
			_result.setStatements(StatementFromNode.convertNodeList(node.getStatements(), context));
			return _result;
		}
	}

	/**
	 * Context during convertion to give information about a failure.
	 * 
	 * @author dsporn
	 *
	 */
	private static final class ConvertionContext
	{
		/**
		 * Node being converted.
		 */
		private VessNode myCurrentNode;

		/**
		 * Index of the node being converted.
		 */
		private int myNodeIndex;

		/**
		 * Parent context, either null, or the parent context in a sub group, e.g. inside an 'if' action list.
		 */
		private final ConvertionContext myParent;

		public ConvertionContext(ConvertionContext parent)
		{
			myParent = parent;
		}

		/**
		 * @param buffer
		 */
		public void appendToString(StringBuilder buffer)
		{
			if (null != getParent())
			{
				buffer.append("->");
			}
			buffer.append("[").append(getNodeIndex()).append("]").append(getCurrentNode().getClass().getName());
		}

		public VessNode getCurrentNode()
		{
			return myCurrentNode;
		}

		public int getNodeIndex()
		{
			return myNodeIndex;
		}

		public ConvertionContext getParent()
		{
			return myParent;
		}

		public void setCurrentNode(VessNode currentNode)
		{
			myCurrentNode = currentNode;
		}

		public void setNodeIndex(int nodeIndex)
		{
			myNodeIndex = nodeIndex;
		}

		@Override
		public String toString()
		{
			StringBuilder _buffer = new StringBuilder();
			appendToString(_buffer);
			return super.toString();
		}

	}

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
			VessNodeCall.class.getName(), VessNodeDefineAs.class.getName(), VessNodeIf.class.getName(), VessNodeOn.class.getName()
	};

	private static final StatementFromNode THE_INSTANCE = new StatementFromNode();

	public static final List<Statement> convertNodeList(VessNode firstNode) throws SslpoiException
	{
		return convertNodeList(firstNode, null);
	}

	public static final List<Statement> convertNodeList(VessNode firstNode, ConvertionContext parentContext) throws SslpoiException
	{
		LinkedList<Statement> _result = new LinkedList<Statement>();
		ConvertionContext _context = new ConvertionContext(parentContext);
		for (VessNode _current = firstNode; _current != null;)
		{
			// setup context
			_context.setCurrentNode(_current);

			_result.add(THE_INSTANCE.findAndInvokeConverterForNode(_current, _context));

			// next
			_current = _current.getNext();
			_context.setNodeIndex(_context.getNodeIndex() + 1);
		}

		return _result;
	}

	/**
	 * Convert the given node into a statement.
	 * 
	 * @param node
	 *            the node to convert.
	 * @return the statement.
	 * @throws SslpoiException
	 *             when there is a problem.
	 */
	public static final Statement convertSingleNode(VessNode node) throws SslpoiException
	{
		return THE_INSTANCE.findAndInvokeConverterForNode(node, null);
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
	private Statement findAndInvokeConverterForNode(VessNode node, ConvertionContext context) throws SslpoiException
	{
		Statement _result = null;
		try
		{
			Class<? extends ConvertionContext> _contextClass = (null != context) ? context.getClass() : ConvertionContext.class;
			Class<?>[] _parameters =
			{
					node.getClass(), _contextClass
			};
			Method _converter = Converter.class.getMethod(METHOD_NAME__CONVERT, _parameters);
			_result = (Statement) _converter.invoke(null, node, context);
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

	private Set<String> getSupportedNodes()
	{
		return mySupportedNodes;
	}
}
