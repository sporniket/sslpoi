/**
 * 
 */
package com.sporniket.scripting.sslpoi;

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

import com.sporniket.scripting.sslpoi.core.InitialisationMode;
import com.sporniket.scripting.sslpoi.mass.Statement;
import com.sporniket.scripting.sslpoi.mass.StatementDefineAs;
import com.sporniket.scripting.sslpoi.mass.TreeBuilder;
import com.sporniket.scripting.sslpoi.vess.AnalyzerLexical;
import com.sporniket.scripting.sslpoi.vess.AnalyzerSyntaxic;
import com.sporniket.scripting.sslpoi.vess.VessNode;
import com.sporniket.scripting.sslpoi.vess.VessNodeDefineAs;

/**
 * Test for "define ..." statements.
 * 
 * @author dsporn
 *
 */
public class DefineTest
{
	private AnalyzerSyntaxic myParser;

	@Before
	public void setupParserObjects()
	{
		final ComplexSymbolFactory _symbolFactory = new ComplexSymbolFactory();
		final AnalyzerLexical _lexer = new AnalyzerLexical(null);
		_lexer.setSymbolFactory(_symbolFactory);
		setParser(new AnalyzerSyntaxic(_lexer, _symbolFactory));
	}

	@Test
	public void testCorrectDefineAsNew() throws Exception
	{
		VessNodeDefineAs _define = (VessNodeDefineAs) TestUtils.parseVessSource("define toto as new bar", getParser());
		assertThat(_define.getInitialisationMode(), is(InitialisationMode.NEW));
		assertThat(_define.getIdentifier(), is("toto"));
		assertThat(_define.getClassName(), is("bar"));
		assertThat(_define.isArray(), is(false));

		_define = (VessNodeDefineAs) TestUtils.parseVessSource("define toto as new foo.bar", getParser());
		assertThat(_define.getClassName(), is("foo.bar"));
	}

	@Test
	public void testCorrectDefineAsNew_array() throws Exception
	{
		VessNodeDefineAs _define = (VessNodeDefineAs) TestUtils.parseVessSource("define toto as new bar[]", getParser());
		assertThat(_define.getInitialisationMode(), is(InitialisationMode.NEW));
		assertThat(_define.getIdentifier(), is("toto"));
		assertThat(_define.getClassName(), is("bar"));
		assertThat(_define.isArray(), is(true));
	}

	private AnalyzerSyntaxic getParser()
	{
		return myParser;
	}

	private void setParser(AnalyzerSyntaxic parser)
	{
		myParser = parser;
	}

}
