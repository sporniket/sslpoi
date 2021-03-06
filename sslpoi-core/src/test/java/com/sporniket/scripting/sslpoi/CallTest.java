/**
 * 
 */
package com.sporniket.scripting.sslpoi;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import java_cup.runtime.ComplexSymbolFactory;

import org.junit.Before;
import org.junit.Test;

import com.sporniket.scripting.sslpoi.vess.AnalyzerLexical;
import com.sporniket.scripting.sslpoi.vess.AnalyzerSyntaxic;
import com.sporniket.scripting.sslpoi.vess.VessNodeAccessor;
import com.sporniket.scripting.sslpoi.vess.VessNodeArgumentMapping;
import com.sporniket.scripting.sslpoi.vess.VessNodeCall;

/**
 * Test for "call ..." statements.
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
public class CallTest
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
	public void testCorrectCallNoArgs__direct() throws Exception
	{
		VessNodeCall _define = (VessNodeCall) TestUtils.parseVessSource("call foo", getParser());
		VessNodeAccessor _call = _define.getCall();
		assertThat(_call.getValue(), is("foo"));
		assertThat(_call.isLastNode(), is(true));
		assertThat(_define.getMapping(), nullValue());
	}

	@Test
	public void testCorrectCallNoArgs__indirectOneLevel() throws Exception
	{
		VessNodeCall _define = (VessNodeCall) TestUtils.parseVessSource("call foo from bar", getParser());
		VessNodeAccessor _call = _define.getCall();
		assertThat(_call.getValue(), is("bar"));
		assertThat(_call.isLastNode(), is(false));
		_call = (VessNodeAccessor) _call.getNext();
		assertThat(_call.getValue(), is("foo"));
		assertThat(_call.isLastNode(), is(true));
		assertThat(_define.getMapping(), nullValue());
	}

	@Test
	public void testCorrectCallNoArgs__indirectTwoLevels() throws Exception
	{
		VessNodeCall _define = (VessNodeCall) TestUtils.parseVessSource("call foo from bar from blurg", getParser());
		VessNodeAccessor _call = _define.getCall();
		assertThat(_call.getValue(), is("blurg"));
		assertThat(_call.isLastNode(), is(false));
		_call = (VessNodeAccessor) _call.getNext();
		assertThat(_call.getValue(), is("bar"));
		assertThat(_call.isLastNode(), is(false));
		_call = (VessNodeAccessor) _call.getNext();
		assertThat(_call.getValue(), is("foo"));
		assertThat(_call.isLastNode(), is(true));
		assertThat(_define.getMapping(), nullValue());
	}

	@Test
	public void testCorrectCallWithArgs__oneArgument__directValue() throws Exception
	{
		VessNodeCall _define = (VessNodeCall) TestUtils.parseVessSource("call foo using bar", getParser());
		VessNodeAccessor _call = _define.getCall();
		assertThat(_call.getValue(), is("foo"));
		assertThat(_call.isLastNode(), is(true));
		assertThat(_define.getMapping(), not(nullValue()));

		VessNodeArgumentMapping _mapping = _define.getMapping();
		assertThat(_mapping.isLastNode(), is(true));
		assertThat(_mapping.getValue().getClass().getSimpleName(), is("VessNodeAccessor"));
		VessNodeAccessor _mappingValueAccess = (VessNodeAccessor) _mapping.getValue();
		assertThat(_mappingValueAccess.getValue(), is("bar"));
		assertThat(_mappingValueAccess.isLastNode(), is(true));
	}

	@Test
	public void testCorrectCallWithArgs__oneArgument__indirectValue() throws Exception
	{
		VessNodeCall _define = (VessNodeCall) TestUtils.parseVessSource("call foo using bar from wizz ", getParser());
		VessNodeAccessor _call = _define.getCall();
		assertThat(_call.getValue(), is("foo"));
		assertThat(_call.isLastNode(), is(true));
		assertThat(_define.getMapping(), not(nullValue()));

		VessNodeArgumentMapping _mapping = _define.getMapping();
		assertThat(_mapping.isLastNode(), is(true));
		assertThat(_mapping.getValue().getClass().getSimpleName(), is("VessNodeAccessor"));
		VessNodeAccessor _mappingValueAccess = (VessNodeAccessor) _mapping.getValue();
		assertThat(_mappingValueAccess.getValue(), is("wizz"));
		assertThat(_mappingValueAccess.isLastNode(), is(false));
		_mappingValueAccess = (VessNodeAccessor) _mappingValueAccess.getNext();
		assertThat(_mappingValueAccess.getValue(), is("bar"));
		assertThat(_mappingValueAccess.isLastNode(), is(true));
	}

	@Test
	public void testCorrectCallWithArgs__threeArguments__directValue() throws Exception
	{
		VessNodeCall _define = (VessNodeCall) TestUtils.parseVessSource(
				"call foo using bar , bear , boar ", getParser());
		VessNodeAccessor _call = _define.getCall();
		assertThat(_call.getValue(), is("foo"));
		assertThat(_call.isLastNode(), is(true));
		assertThat(_define.getMapping(), not(nullValue()));

		VessNodeArgumentMapping _mapping = _define.getMapping();
		assertThat(_mapping.isLastNode(), is(false));
		assertThat(_mapping.getValue().getClass().getSimpleName(), is("VessNodeAccessor"));
		VessNodeAccessor _mappingValueAccess = (VessNodeAccessor) _mapping.getValue();
		assertThat(_mappingValueAccess.getValue(), is("bar"));
		assertThat(_mappingValueAccess.isLastNode(), is(true));

		_mapping = (VessNodeArgumentMapping) _mapping.getNext();
		assertThat(_mapping.isLastNode(), is(false));
		assertThat(_mapping.getValue().getClass().getSimpleName(), is("VessNodeAccessor"));
		_mappingValueAccess = (VessNodeAccessor) _mapping.getValue();
		assertThat(_mappingValueAccess.getValue(), is("bear"));
		assertThat(_mappingValueAccess.isLastNode(), is(true));

		_mapping = (VessNodeArgumentMapping) _mapping.getNext();
		assertThat(_mapping.isLastNode(), is(true));
		assertThat(_mapping.getValue().getClass().getSimpleName(), is("VessNodeAccessor"));
		_mappingValueAccess = (VessNodeAccessor) _mapping.getValue();
		assertThat(_mappingValueAccess.getValue(), is("boar"));
		assertThat(_mappingValueAccess.isLastNode(), is(true));
	}

	@Test
	public void testCorrectCallWithArgs__twoArguments__directValue() throws Exception
	{
		VessNodeCall _define = (VessNodeCall) TestUtils.parseVessSource("call foo using bar, bear ", getParser());
		VessNodeAccessor _call = _define.getCall();
		assertThat(_call.getValue(), is("foo"));
		assertThat(_call.isLastNode(), is(true));
		assertThat(_define.getMapping(), not(nullValue()));

		VessNodeArgumentMapping _mapping = _define.getMapping();
		assertThat(_mapping.isLastNode(), is(false));
		assertThat(_mapping.getValue().getClass().getSimpleName(), is("VessNodeAccessor"));
		VessNodeAccessor _mappingValueAccess = (VessNodeAccessor) _mapping.getValue();
		assertThat(_mappingValueAccess.getValue(), is("bar"));
		assertThat(_mappingValueAccess.isLastNode(), is(true));

		_mapping = (VessNodeArgumentMapping) _mapping.getNext();
		assertThat(_mapping.isLastNode(), is(true));
		assertThat(_mapping.getValue().getClass().getSimpleName(), is("VessNodeAccessor"));
		_mappingValueAccess = (VessNodeAccessor) _mapping.getValue();
		assertThat(_mappingValueAccess.getValue(), is("bear"));
		assertThat(_mappingValueAccess.isLastNode(), is(true));
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
