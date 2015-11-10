/**
 * 
 */
package com.sporniket.scripting.ssl;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.Symbol;

import org.junit.Before;
import org.junit.Test;

import com.sporniket.scripting.ssl.core.InitialisationMode;
import com.sporniket.scripting.ssl.mass.Statement;
import com.sporniket.scripting.ssl.mass.StatementDefineAs;
import com.sporniket.scripting.ssl.mass.TreeBuilder;
import com.sporniket.scripting.ssl.vess.AnalyzerLexical;
import com.sporniket.scripting.ssl.vess.AnalyzerSyntaxic;
import com.sporniket.scripting.ssl.vess.VessNode;
import com.sporniket.scripting.ssl.vess.VessNodeDefineAs;

/**
 * Test the parsing.
 * 
 * @author dsporn
 *
 */
public class ParsingTests
{

	private static final String[] SOURCE__MULTIPLE_STATEMENTS =
	{
			"define toto as new bar", "define titi as new foo.bar", ""
	};

	private static final String[] SOURCE__MULTIPLE_STATEMENTS__NO_FINAL_END_LINE =
	{
			"define toto as new bar", "define titi as new foo.bar"
	};

	private static final String[] SOURCE__MULTIPLE_STATEMENTS__WITH_EMPTY_LINE =
	{
			"  ", "define toto as new bar", "\t", "define titi as new foo.bar", ""
	};

	private AnalyzerSyntaxic myParser;

	@Before
	public void setupParserObjects()
	{
		final ComplexSymbolFactory _symbolFactory = new ComplexSymbolFactory();
		final AnalyzerLexical _lexer = new AnalyzerLexical(null);
		_lexer.setSymbolFactory(_symbolFactory);
		setParser(new AnalyzerSyntaxic(_lexer, _symbolFactory));
	}

	private AnalyzerSyntaxic getParser()
	{
		return myParser;
	}

	private void setParser(AnalyzerSyntaxic parser)
	{
		myParser = parser;
	}

	@Test
	public void testMultipleStatements() throws Exception
	{
		testMultipleDefineAs(SOURCE__MULTIPLE_STATEMENTS);
	}

	@Test
	public void testMultipleStatementsNoFinalEndLine() throws Exception
	{
		testMultipleDefineAs(SOURCE__MULTIPLE_STATEMENTS__NO_FINAL_END_LINE);
	}

	@Test
	public void testMultipleStatementsWithEmptyLines() throws Exception
	{
		testMultipleDefineAs(SOURCE__MULTIPLE_STATEMENTS__WITH_EMPTY_LINE);
	}

	/**
	 * @param source
	 * @throws Exception
	 */
	private void testMultipleDefineAs(final String[] source) throws Exception
	{
		VessNodeDefineAs _define;

		VessNode _node = (VessNode) TestUtils.parseVessSource(TestUtils.makeSource(source), getParser());
		while (_node != null && !(_node instanceof VessNodeDefineAs))
		{
			_node = _node.getNext();
		}
		assertThat(_node, not(nullValue()));

		_define = (VessNodeDefineAs) _node;
		assertThat(_define.getInitialisationMode(), is(InitialisationMode.NEW));
		assertThat(_define.getIdentifier(), is("toto"));
		assertThat(_define.getClassName(), is("bar"));
		assertThat(_define.isLastNode(), is(false));

		do
		{
			_node = _node.getNext();
		}
		while (_node != null && !(_node instanceof VessNodeDefineAs));
		assertThat(_node, not(nullValue()));

		_define = (VessNodeDefineAs) _node;
		assertThat(_define.getInitialisationMode(), is(InitialisationMode.NEW));
		assertThat(_define.getIdentifier(), is("titi"));
		assertThat(_define.getClassName(), is("foo.bar"));
		assertThat(_define.isLastNode(), is(true));
	}

}
