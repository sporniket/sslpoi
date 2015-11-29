/**
 * 
 */
package com.sporniket.scripting.ssl;

import java.io.Reader;
import java.io.StringReader;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

import com.sporniket.scripting.ssl.vess.AnalyzerLexical;
import com.sporniket.scripting.ssl.vess.AnalyzerSyntaxic;
import com.sporniket.scripting.ssl.vess.VessNode;
import com.sporniket.scripting.ssl.vess.VessNodeAccessor;
import com.sporniket.scripting.ssl.vess.VessNodeCall;
import com.sporniket.scripting.ssl.vess.VessNodeDefineAs;

/**
 * Utilities for tests.
 * 
 * @author dsporn
 *
 */
public class TestUtils
{

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
			System.out.print("(" + _defineAs.getIdentifier() + ", " + _defineAs.getInitialisationMode() + ", "
					+ _defineAs.getClassName() + ")");
		}
		else if (node instanceof VessNodeCall)
		{
			VessNodeCall _call = (VessNodeCall) node;
			debugNode__partial__accessor(_call.getCall());
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
			final String _childPrefix = prefix + "    ";
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

}
