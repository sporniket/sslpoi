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
		getParser().debug_parse();
		final List<Statement> _resultTree = getTreeBuilder().getResultTree();
		if (_resultTree.isEmpty())
		{
			throw new IllegalStateException("No statement found for '" + statement + "'");
		}
		return _resultTree.get(_resultTree.size() - 1);

	}

}
