/**
 * 
 */
package com.sporniket.scripting.sslpoi;

import java.io.Reader;
import java.io.StringReader;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

import com.sporniket.scripting.sslpoi.vess.AnalyzerLexical;
import com.sporniket.scripting.sslpoi.vess.AnalyzerSyntaxic;
import com.sporniket.scripting.sslpoi.vess.VessNode;
import com.sporniket.scripting.sslpoi.vess.VessNodeAccessor;
import com.sporniket.scripting.sslpoi.vess.VessNodeCall;
import com.sporniket.scripting.sslpoi.vess.VessNodeDefineAs;
import com.sporniket.scripting.sslpoi.vess.VessNodeExpressionLogical;
import com.sporniket.scripting.sslpoi.vess.VessNodeIdentifierMapping;
import com.sporniket.scripting.sslpoi.vess.VessNodeIf;
import com.sporniket.scripting.sslpoi.vess.VessNodeLiteralString;
import com.sporniket.scripting.sslpoi.vess.VessNodeOn;

/**
 * Utilities for tests.
 * 
 * @author dsporn
 *
 */
public class TestUtils
{

	private static final String INDENTATION = "    ";

	/**
	 * @param node
	 *            node to lump.
	 * @param prefix
	 *            prefix to use.
	 */
	public static void debugNode(VessNode node, final String prefix)
	{
		// main line
		System.out.print(prefix + "+<" + node.getClass().getSimpleName() + "> ");
		if (node instanceof VessNodeDefineAs)
		{
			VessNodeDefineAs _defineAs = (VessNodeDefineAs) node;
			System.out.print("(" + _defineAs.getIdentifier() + " " + _defineAs.getInitialisationMode() + " "
					+ _defineAs.getClassName() + ")");
		}
		else if (node instanceof VessNodeCall)
		{
			VessNodeCall _call = (VessNodeCall) node;
			debugNode__partial__accessor(_call.getCall());
		}
		else if (node instanceof VessNodeOn)
		{
			VessNodeOn _on = (VessNodeOn) node;
			System.out.print("«" + _on.getEventName() + "» (");
			for (VessNodeIdentifierMapping _mapping = ((VessNodeOn) node).getMapping(); _mapping != null;)
			{
				System.out.print(_mapping.getIdentifier() + " as " + _mapping.getClassName());
				VessNode _next = _mapping.getNext();
				if (null != _next)
				{
					System.out.print(", ");
					_mapping = (VessNodeIdentifierMapping) _next;
				}
				else
				{
					_mapping = null;
				}

			}
			System.out.print(")");
			if (_on.getStatements() != null)
			{
				System.out.println();
				String _childPrefix = prefix + INDENTATION;
				for (VessNode _child = _on.getStatements(); _child != null;)
				{
					debugNode(_child, _childPrefix);
					_child = _child.getNext();
				}
			}
		}
		else if (node instanceof VessNodeIf)
		{
			System.out.println();
			String _childPrefix = prefix + INDENTATION;
			debugNodeIf((VessNodeIf) node, _childPrefix);
		}
		System.out.println();
		// children nodes (to do)
		// next node
		if (!node.isLastNode())
		{
			debugNode(node.getNext(), prefix);
		}
	}

	public static void debugSymbol(ComplexSymbol node, String prefix)
	{
		System.out.println(prefix + "+[" + node.getName() + "]");
		final Object _value = node.value;
		if (null != _value)
		{
			final String _childPrefix = prefix + INDENTATION;
			if (_value instanceof ComplexSymbol)
			{
				ComplexSymbol _child = (ComplexSymbol) _value;
				debugSymbol(_child, _childPrefix);
			}
			else if (_value instanceof VessNode)
			{
				VessNode _node = (VessNode) _value;
				debugNode(_node, _childPrefix);
			}
		}
	}

	public static void debugSymbol(String source, ComplexSymbol root)
	{
		System.out.println("//=== ABSTRACT SYNTAX TREE SOURCE ===//");
		System.out.println(source);
		System.out.println("//=== ABSTRACT SYNTAX TREE RESULT ===//");
		debugSymbol(root, "");
		System.out.println("//=== END OF SYNTAX TREE RESULT   ===//");
	}

	/**
	 * @param statements
	 *            a list of statement.
	 * @return an String that concatenate all the statements, that are separated with a <code>\n</code>.
	 */
	public static final String makeSource(String[] statements)
	{
		StringBuilder _result = new StringBuilder();
		for (String _statement : statements)
		{
			if (0 != _result.length())
			{
				_result.append("\n");
			}
			_result.append(_statement);
		}
		return _result.toString();
	}

	public static VessNode parseVessSource(String source, AnalyzerSyntaxic parser) throws Exception
	{
		Reader statementReader = new StringReader(source);
		((AnalyzerLexical) parser.getScanner()).yyreset(statementReader);
		final ComplexSymbol _symbol = (ComplexSymbol) parser.debug_parse();
		debugSymbol(source, _symbol);
		Object _value = _symbol.value;
		if (_value instanceof VessNode)
		{
			VessNode _node = (VessNode) _value;
			return _node;
		}
		throw new IllegalStateException("No node found for '" + source + "'");
	}

	private static void debugNode__partial(VessNode node)
	{
		if (node instanceof VessNodeAccessor)
		{
			debugNode__partial__accessor((VessNodeAccessor) node);
		}
		else if (node instanceof VessNodeLiteralString)
		{
			debugNode__partial__literalString((VessNodeLiteralString) node);
		}
	}

	/**
	 * @param node
	 *            node to dump.
	 */
	private static void debugNode__partial__accessor(VessNodeAccessor root)
	{
		boolean _first = true;
		for (VessNodeAccessor _node = root; _node != null;)
		{
			if (_first)
			{
				System.out.print(_node.getValue());
				_first = false;
			}
			else
			{
				System.out.print("->" + _node.getValue());
			}
			_node = (VessNodeAccessor) _node.getNext();
		}
	}

	/**
	 * @param node
	 *            node to dump.
	 */
	private static void debugNode__partial__literalString(VessNodeLiteralString literal)
	{
		System.out.print("\"" + literal.getValue() + "\"");
	}

	private static void debugNodeIf(VessNodeIf node, String prefix)
	{
		String _childPrefix = prefix + INDENTATION;
		VessNodeExpressionLogical _test = node.getTest();
		if (null != _test)
		{
			System.out.print(prefix + "(");
			debugNode__partial((VessNode) _test.getValue());
			System.out.print(" ");
			if (_test.getOperator().isNot())
			{
				System.out.print("!");
			}
			System.out.print(_test.getOperator().getOperator() + " ");
			debugNode__partial((VessNode) _test.getExpected());
			System.out.print(")");
		}
		else
		{
			System.out.print(prefix + "else");
		}
		if (null != node.getStatements())
		{
			System.out.println();
			debugNode(node.getStatements(), _childPrefix);
		}
		if (null != node.getAlternative())
		{
			System.out.println();
			debugNodeIf(node.getAlternative(), prefix);
		}
	}

}
