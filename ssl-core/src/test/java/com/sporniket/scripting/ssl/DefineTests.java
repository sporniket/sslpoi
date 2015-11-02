/**
 * 
 */
package com.sporniket.scripting.ssl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.Symbol;

import org.junit.Before;
import org.junit.Test;

import com.sporniket.scripting.ssl.mass.Statement;
import com.sporniket.scripting.ssl.mass.StatementDefineAs;
import com.sporniket.scripting.ssl.mass.TreeBuilder;
import com.sporniket.scripting.ssl.vess.AnalyzerLexical;
import com.sporniket.scripting.ssl.vess.AnalyzerSyntaxic;

/**
 * Test for "define ..." statements.
 * 
 * @author dsporn
 *
 */
public class DefineTests
{
	private AnalyzerSyntaxic myParser;

	private TreeBuilder myTreeBuilder;

	@Before
	public void setupParserObjects()
	{
		setTreeBuilder(new TreeBuilder());
		final ComplexSymbolFactory _symbolFactory = new ComplexSymbolFactory();
		final AnalyzerLexical _lexer = new AnalyzerLexical(null);
		_lexer.setSymbolFactory(_symbolFactory);
		setParser(new AnalyzerSyntaxic(_lexer, _symbolFactory));
		getParser().setTreeBuilder(getTreeBuilder());
	}

	private AnalyzerSyntaxic getParser()
	{
		return myParser;
	}

	private void setParser(AnalyzerSyntaxic parser)
	{
		myParser = parser;
	}

	private TreeBuilder getTreeBuilder()
	{
		return myTreeBuilder;
	}

	private void setTreeBuilder(TreeBuilder treeBuilder)
	{
		myTreeBuilder = treeBuilder;
	}

	@Test
	public void testCorrectDefineAsNew() throws Exception
	{
		StatementDefineAs _define = (StatementDefineAs) parseSingleStatement("define toto as new bar");
		assertThat(_define.getInitialisationMode(), is(StatementDefineAs.InitialisationMode.NEW));
		assertThat(_define.getIdentifier(), is("toto"));
		assertThat(_define.getClassName(), is("bar"));

		_define = (StatementDefineAs) parseSingleStatement("define toto as new foo.bar");
		assertThat(_define.getClassName(), is("foo.bar"));
	}

	private Statement parseSingleStatement(String statement) throws Exception
	{
		Reader statementReader = new StringReader(statement);
		((AnalyzerLexical) getParser().getScanner()).yyreset(statementReader);
		final ComplexSymbol _symbol = (ComplexSymbol) getParser().debug_parse();
		debugSymbol(statement, _symbol);
		Object _value = _symbol.value;
		if (_value instanceof Statement)
		{
			Statement _statement = (Statement) _value;
			return _statement ;
		}
		throw new IllegalStateException("No statement found for '" + statement + "'");
	}
	
	private void debugSymbol(String source, ComplexSymbol root)
	{
		System.out.println("//=== ABSTRACT SYNTAX TREE SOURCE ===//");
		System.out.println(source);
		System.out.println("//=== ABSTRACT SYNTAX TREE RESULT ===//");
		debugSymbol(root, "");
		System.out.println("//=== END OF SYNTAX TREE RESULT   ===//");
	}
	
	private void debugSymbol(ComplexSymbol node, String prefix)
	{
		System.out.println(prefix+"+["+node.getName()+"]");
		final Object _value = node.value;
		if (null != _value)
		{
			final String _childPrefix = prefix+"    ";
			if (_value instanceof ComplexSymbol)
			{
				ComplexSymbol _child = (ComplexSymbol) _value;
				debugSymbol(_child, _childPrefix);
			}
			else if (_value instanceof Statement)
			{
				Statement _statement = (Statement) _value;
				System.out.println(_childPrefix+"+<"+_statement.getClass().getSimpleName()+">");
			}
		}
	}

}
