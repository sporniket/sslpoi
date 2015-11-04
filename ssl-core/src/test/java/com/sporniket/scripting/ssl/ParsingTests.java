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
	
	private static final String[] SOURCE__MULTIPLE_STATEMENTS = {"define toto as new bar", "define titi as new foo.bar"} ;
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
		VessNodeDefineAs _define = (VessNodeDefineAs) TestUtils.parseVessSource(TestUtils.makeSource(SOURCE__MULTIPLE_STATEMENTS), getParser());
		assertThat(_define.getInitialisationMode(), is(InitialisationMode.NEW));
		assertThat(_define.getIdentifier(), is("toto"));
		assertThat(_define.getClassName(), is("bar"));
		assertThat(_define.isLastNode(), is(false));

		_define = (VessNodeDefineAs) _define.getNext();
		assertThat(_define.getInitialisationMode(), is(InitialisationMode.NEW));
		assertThat(_define.getIdentifier(), is("titi"));
		assertThat(_define.getClassName(), is("foo.bar"));
		assertThat(_define.isLastNode(), is(true));
	}

}
