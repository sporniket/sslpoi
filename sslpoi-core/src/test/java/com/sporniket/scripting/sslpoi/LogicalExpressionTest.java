/**
 * 
 */
package com.sporniket.scripting.sslpoi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java_cup.runtime.ComplexSymbolFactory;

import org.junit.Before;
import org.junit.Test;

import com.sporniket.scripting.sslpoi.core.LogicalOperator;
import com.sporniket.scripting.sslpoi.vess.AnalyzerLexical;
import com.sporniket.scripting.sslpoi.vess.AnalyzerSyntaxic;
import com.sporniket.scripting.sslpoi.vess.VessNodeAccessor;
import com.sporniket.scripting.sslpoi.vess.VessNodeExpressionLogical;
import com.sporniket.scripting.sslpoi.vess.VessNodeIf;
import com.sporniket.scripting.sslpoi.vess.VessNodeLiteralString;
import com.sporniket.scripting.sslpoi.vess.VessNodeOperatorLogical;

/**
 * Test logical expressions in statements.
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
 * @version 0.2.0
 * @since 0.1.0
 * 
 */
public class LogicalExpressionTest
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
	public void testCorrectLogic__is() throws Exception
	{
		String[] _sourceRaw =
		{
				"if foo is bar", "    call action", "endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		VessNodeExpressionLogical _logic = _if.getTest();
		assertThat(_logic.getValue().getClass().getSimpleName(), is(VessNodeAccessor.class.getSimpleName()));
		assertThat(_logic.getExpected().getClass().getSimpleName(), is(VessNodeAccessor.class.getSimpleName()));
		VessNodeAccessor _value = (VessNodeAccessor) _logic.getValue();
		VessNodeAccessor _expected = (VessNodeAccessor) _logic.getExpected();
		VessNodeOperatorLogical _op = _logic.getOperator();
		assertThat(_op.isNot(), is(false));
		assertThat(_op.getOperator(), is(LogicalOperator.IS));
		assertThat(_value.getValue(), is("foo"));
		assertThat(_expected.getValue(), is("bar"));
	}

	@Test
	public void testCorrectLogic__isLike() throws Exception
	{
		String[] _sourceRaw =
		{
				"if foo is like bar", "    call action", "endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		VessNodeExpressionLogical _logic = _if.getTest();
		assertThat(_logic.getValue().getClass().getSimpleName(), is(VessNodeAccessor.class.getSimpleName()));
		assertThat(_logic.getExpected().getClass().getSimpleName(), is(VessNodeAccessor.class.getSimpleName()));
		VessNodeAccessor _value = (VessNodeAccessor) _logic.getValue();
		VessNodeAccessor _expected = (VessNodeAccessor) _logic.getExpected();
		VessNodeOperatorLogical _op = _logic.getOperator();
		assertThat(_op.isNot(), is(false));
		assertThat(_op.getOperator(), is(LogicalOperator.IS_LIKE));
		assertThat(_value.getValue(), is("foo"));
		assertThat(_expected.getValue(), is("bar"));
	}

	@Test
	public void testCorrectLogic__isNot() throws Exception
	{
		String[] _sourceRaw =
		{
				"if foo is not bar", "    call action", "endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		VessNodeExpressionLogical _logic = _if.getTest();
		assertThat(_logic.getValue().getClass().getSimpleName(), is(VessNodeAccessor.class.getSimpleName()));
		assertThat(_logic.getExpected().getClass().getSimpleName(), is(VessNodeAccessor.class.getSimpleName()));
		VessNodeAccessor _value = (VessNodeAccessor) _logic.getValue();
		VessNodeAccessor _expected = (VessNodeAccessor) _logic.getExpected();
		VessNodeOperatorLogical _op = _logic.getOperator();
		assertThat(_op.isNot(), is(true));
		assertThat(_op.getOperator(), is(LogicalOperator.IS));
		assertThat(_value.getValue(), is("foo"));
		assertThat(_expected.getValue(), is("bar"));
	}

	@Test
	public void testCorrectLogic__isNotLike() throws Exception
	{
		String[] _sourceRaw =
		{
				"if foo is not like bar", "    call action", "endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		VessNodeExpressionLogical _logic = _if.getTest();
		assertThat(_logic.getValue().getClass().getSimpleName(), is(VessNodeAccessor.class.getSimpleName()));
		assertThat(_logic.getExpected().getClass().getSimpleName(), is(VessNodeAccessor.class.getSimpleName()));
		VessNodeAccessor _value = (VessNodeAccessor) _logic.getValue();
		VessNodeAccessor _expected = (VessNodeAccessor) _logic.getExpected();
		VessNodeOperatorLogical _op = _logic.getOperator();
		assertThat(_op.isNot(), is(true));
		assertThat(_op.getOperator(), is(LogicalOperator.IS_LIKE));
		assertThat(_value.getValue(), is("foo"));
		assertThat(_expected.getValue(), is("bar"));
	}

	@Test
	public void testCorrectLogic__literalString__simple() throws Exception
	{
		String[] _sourceRaw =
		{
				"if foo is \"bar\"", "    call action", "endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		VessNodeExpressionLogical _logic = _if.getTest();
		assertThat(_logic.getValue().getClass().getSimpleName(), is(VessNodeAccessor.class.getSimpleName()));
		assertThat(_logic.getExpected().getClass().getSimpleName(), is(VessNodeLiteralString.class.getSimpleName()));
		VessNodeAccessor _value = (VessNodeAccessor) _logic.getValue();
		VessNodeLiteralString _expected = (VessNodeLiteralString) _logic.getExpected();
		VessNodeOperatorLogical _op = _logic.getOperator();
		assertThat(_op.isNot(), is(false));
		assertThat(_op.getOperator(), is(LogicalOperator.IS));
		assertThat(_value.getValue(), is("foo"));
		assertThat(_expected.getValue(), is("bar"));
	}

	@Test
	public void testCorrectLogic__literalString__simpleLike() throws Exception
	{
		String[] _sourceRaw =
		{
				"if foo is like \"bar\"", "    call action", "endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		VessNodeExpressionLogical _logic = _if.getTest();
		assertThat(_logic.getValue().getClass().getSimpleName(), is(VessNodeAccessor.class.getSimpleName()));
		assertThat(_logic.getExpected().getClass().getSimpleName(), is(VessNodeLiteralString.class.getSimpleName()));
		VessNodeAccessor _value = (VessNodeAccessor) _logic.getValue();
		VessNodeLiteralString _expected = (VessNodeLiteralString) _logic.getExpected();
		VessNodeOperatorLogical _op = _logic.getOperator();
		assertThat(_op.isNot(), is(false));
		assertThat(_op.getOperator(), is(LogicalOperator.IS_LIKE));
		assertThat(_value.getValue(), is("foo"));
		assertThat(_expected.getValue(), is("bar"));
	}

	@Test
	public void testCorrectLogic__literalString__simple__bothSides() throws Exception
	{
		String[] _sourceRaw =
		{
				"if \"foo\" is \"bar\"", "    call action", "endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		VessNodeExpressionLogical _logic = _if.getTest();
		assertThat(_logic.getValue().getClass().getSimpleName(), is(VessNodeLiteralString.class.getSimpleName()));
		assertThat(_logic.getExpected().getClass().getSimpleName(), is(VessNodeLiteralString.class.getSimpleName()));
		VessNodeLiteralString _value = (VessNodeLiteralString) _logic.getValue();
		VessNodeLiteralString _expected = (VessNodeLiteralString) _logic.getExpected();
		VessNodeOperatorLogical _op = _logic.getOperator();
		assertThat(_op.isNot(), is(false));
		assertThat(_op.getOperator(), is(LogicalOperator.IS));
		assertThat(_value.getValue(), is("foo"));
		assertThat(_expected.getValue(), is("bar"));
	}

	@Test
	public void testCorrectLogic__literalString__specialChar__backSlash() throws Exception
	{
		String[] _sourceRaw =
		{
				"if foo is \"bar\\\\\"", "    call action", "endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		VessNodeExpressionLogical _logic = _if.getTest();
		assertThat(_logic.getValue().getClass().getSimpleName(), is(VessNodeAccessor.class.getSimpleName()));
		assertThat(_logic.getExpected().getClass().getSimpleName(), is(VessNodeLiteralString.class.getSimpleName()));
		VessNodeAccessor _value = (VessNodeAccessor) _logic.getValue();
		VessNodeLiteralString _expected = (VessNodeLiteralString) _logic.getExpected();
		VessNodeOperatorLogical _op = _logic.getOperator();
		assertThat(_op.isNot(), is(false));
		assertThat(_op.getOperator(), is(LogicalOperator.IS));
		assertThat(_value.getValue(), is("foo"));
		assertThat(_expected.getValue(), is("bar\\"));
	}

	@Test
	public void testCorrectLogic__literalString__specialChar__doubleQuote() throws Exception
	{
		String[] _sourceRaw =
		{
				"if foo is \"bar\\\"\"", "    call action", "endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		VessNodeExpressionLogical _logic = _if.getTest();
		assertThat(_logic.getValue().getClass().getSimpleName(), is(VessNodeAccessor.class.getSimpleName()));
		assertThat(_logic.getExpected().getClass().getSimpleName(), is(VessNodeLiteralString.class.getSimpleName()));
		VessNodeAccessor _value = (VessNodeAccessor) _logic.getValue();
		VessNodeLiteralString _expected = (VessNodeLiteralString) _logic.getExpected();
		VessNodeOperatorLogical _op = _logic.getOperator();
		assertThat(_op.isNot(), is(false));
		assertThat(_op.getOperator(), is(LogicalOperator.IS));
		assertThat(_value.getValue(), is("foo"));
		assertThat(_expected.getValue(), is("bar\""));
	}

	@Test
	public void testCorrectLogic__literalString__specialChar__lineFeed() throws Exception
	{
		String[] _sourceRaw =
		{
				"if foo is \"bar\\r\"", "    call action", "endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		VessNodeExpressionLogical _logic = _if.getTest();
		assertThat(_logic.getValue().getClass().getSimpleName(), is(VessNodeAccessor.class.getSimpleName()));
		assertThat(_logic.getExpected().getClass().getSimpleName(), is(VessNodeLiteralString.class.getSimpleName()));
		VessNodeAccessor _value = (VessNodeAccessor) _logic.getValue();
		VessNodeLiteralString _expected = (VessNodeLiteralString) _logic.getExpected();
		VessNodeOperatorLogical _op = _logic.getOperator();
		assertThat(_op.isNot(), is(false));
		assertThat(_op.getOperator(), is(LogicalOperator.IS));
		assertThat(_value.getValue(), is("foo"));
		assertThat(_expected.getValue(), is("bar\r"));
	}

	@Test
	public void testCorrectLogic__literalString__specialChar__newLine() throws Exception
	{
		String[] _sourceRaw =
		{
				"if foo is \"bar\\n\"", "    call action", "endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		VessNodeExpressionLogical _logic = _if.getTest();
		assertThat(_logic.getValue().getClass().getSimpleName(), is(VessNodeAccessor.class.getSimpleName()));
		assertThat(_logic.getExpected().getClass().getSimpleName(), is(VessNodeLiteralString.class.getSimpleName()));
		VessNodeAccessor _value = (VessNodeAccessor) _logic.getValue();
		VessNodeLiteralString _expected = (VessNodeLiteralString) _logic.getExpected();
		VessNodeOperatorLogical _op = _logic.getOperator();
		assertThat(_op.isNot(), is(false));
		assertThat(_op.getOperator(), is(LogicalOperator.IS));
		assertThat(_value.getValue(), is("foo"));
		assertThat(_expected.getValue(), is("bar\n"));
	}

	@Test
	public void testCorrectLogic__literalString__specialChar__tab() throws Exception
	{
		String[] _sourceRaw =
		{
				"if foo is \"bar\\t\"", "    call action", "endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		VessNodeExpressionLogical _logic = _if.getTest();
		assertThat(_logic.getValue().getClass().getSimpleName(), is(VessNodeAccessor.class.getSimpleName()));
		assertThat(_logic.getExpected().getClass().getSimpleName(), is(VessNodeLiteralString.class.getSimpleName()));
		VessNodeAccessor _value = (VessNodeAccessor) _logic.getValue();
		VessNodeLiteralString _expected = (VessNodeLiteralString) _logic.getExpected();
		VessNodeOperatorLogical _op = _logic.getOperator();
		assertThat(_op.isNot(), is(false));
		assertThat(_op.getOperator(), is(LogicalOperator.IS));
		assertThat(_value.getValue(), is("foo"));
		assertThat(_expected.getValue(), is("bar\t"));
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
