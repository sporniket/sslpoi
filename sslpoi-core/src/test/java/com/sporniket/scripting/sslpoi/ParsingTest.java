/**
 * 
 */
package com.sporniket.scripting.sslpoi;

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

import com.sporniket.scripting.sslpoi.core.InitialisationMode;
import com.sporniket.scripting.sslpoi.mass.Statement;
import com.sporniket.scripting.sslpoi.mass.StatementDefineAs;
import com.sporniket.scripting.sslpoi.mass.TreeBuilder;
import com.sporniket.scripting.sslpoi.vess.AnalyzerLexical;
import com.sporniket.scripting.sslpoi.vess.AnalyzerSyntaxic;
import com.sporniket.scripting.sslpoi.vess.VessNode;
import com.sporniket.scripting.sslpoi.vess.VessNodeDefineAs;

/**
 * Test the parsing.
 * 
 * <p>
 * &copy; Copyright 2015-2016 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>Sporny Script Language (Pun Obviously Intended) &#8211; core</i>.
 * 
 * <p>
 * <i>Sporny Script Language (Pun Obviously Intended) &#8211; core</i> is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * <p>
 * <i>Sporny Script Language (Pun Obviously Intended) &#8211; core</i> is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>Sporny Script Language (Pun Obviously
 * Intended) &#8211; core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>.
 * 
 * <hr>
 * 
 * @author David SPORN
 * @version 0.1.0
 * @since 0.1.0
 * 
 */
public class ParsingTest
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

	private AnalyzerSyntaxic getParser()
	{
		return myParser;
	}

	private void setParser(AnalyzerSyntaxic parser)
	{
		myParser = parser;
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
