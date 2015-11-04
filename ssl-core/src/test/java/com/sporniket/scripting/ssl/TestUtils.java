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
	 * @param prefix
	 */
	public static void debugNode(VessNode node, final String prefix)
	{
		System.out.println(prefix + "+<" + node.getClass().getSimpleName() + ">");
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
			// if (0 == _result.length())
			// {
			// _result.append("\n");
			// }
			_result.append(_statement).append("\n");
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

}
