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

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;

import com.sporniket.scripting.sslpoi.core.InitialisationMode;
import com.sporniket.scripting.sslpoi.mass.Statement;
import com.sporniket.scripting.sslpoi.mass.StatementDefineAs;
import com.sporniket.scripting.sslpoi.mass.TreeBuilder;
import com.sporniket.scripting.sslpoi.vess.AnalyzerLexical;
import com.sporniket.scripting.sslpoi.vess.AnalyzerSyntaxic;
import com.sporniket.scripting.sslpoi.vess.VessNode;
import com.sporniket.scripting.sslpoi.vess.VessNodeAccessor;
import com.sporniket.scripting.sslpoi.vess.VessNodeArgumentMapping;
import com.sporniket.scripting.sslpoi.vess.VessNodeCall;
import com.sporniket.scripting.sslpoi.vess.VessNodeDefineAs;

/**
 * Test for "call ..." statements.
 * 
 * @author dsporn
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
		VessNodeCall _define = (VessNodeCall) TestUtils.parseVessSource("call foo using bar as blurg", getParser());
		VessNodeAccessor _call = _define.getCall();
		assertThat(_call.getValue(), is("foo"));
		assertThat(_call.isLastNode(), is(true));
		assertThat(_define.getMapping(), not(nullValue()));

		VessNodeArgumentMapping _mapping = _define.getMapping();
		assertThat(_mapping.getName(), is("blurg"));
		assertThat(_mapping.isLastNode(), is(true));
		assertThat(_mapping.getValue().getClass().getSimpleName(), is("VessNodeAccessor"));
		VessNodeAccessor _mappingValueAccess = (VessNodeAccessor) _mapping.getValue();
		assertThat(_mappingValueAccess.getValue(), is("bar"));
		assertThat(_mappingValueAccess.isLastNode(), is(true));
	}

	@Test
	public void testCorrectCallWithArgs__oneArgument__indirectValue() throws Exception
	{
		VessNodeCall _define = (VessNodeCall) TestUtils.parseVessSource("call foo using bar from wizz as blurg", getParser());
		VessNodeAccessor _call = _define.getCall();
		assertThat(_call.getValue(), is("foo"));
		assertThat(_call.isLastNode(), is(true));
		assertThat(_define.getMapping(), not(nullValue()));

		VessNodeArgumentMapping _mapping = _define.getMapping();
		assertThat(_mapping.getName(), is("blurg"));
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
				"call foo using bar as blurg, bear as blarg, boar as blorg", getParser());
		VessNodeAccessor _call = _define.getCall();
		assertThat(_call.getValue(), is("foo"));
		assertThat(_call.isLastNode(), is(true));
		assertThat(_define.getMapping(), not(nullValue()));

		VessNodeArgumentMapping _mapping = _define.getMapping();
		assertThat(_mapping.getName(), is("blurg"));
		assertThat(_mapping.isLastNode(), is(false));
		assertThat(_mapping.getValue().getClass().getSimpleName(), is("VessNodeAccessor"));
		VessNodeAccessor _mappingValueAccess = (VessNodeAccessor) _mapping.getValue();
		assertThat(_mappingValueAccess.getValue(), is("bar"));
		assertThat(_mappingValueAccess.isLastNode(), is(true));

		_mapping = (VessNodeArgumentMapping) _mapping.getNext();
		assertThat(_mapping.getName(), is("blarg"));
		assertThat(_mapping.isLastNode(), is(false));
		assertThat(_mapping.getValue().getClass().getSimpleName(), is("VessNodeAccessor"));
		_mappingValueAccess = (VessNodeAccessor) _mapping.getValue();
		assertThat(_mappingValueAccess.getValue(), is("bear"));
		assertThat(_mappingValueAccess.isLastNode(), is(true));

		_mapping = (VessNodeArgumentMapping) _mapping.getNext();
		assertThat(_mapping.getName(), is("blorg"));
		assertThat(_mapping.isLastNode(), is(true));
		assertThat(_mapping.getValue().getClass().getSimpleName(), is("VessNodeAccessor"));
		_mappingValueAccess = (VessNodeAccessor) _mapping.getValue();
		assertThat(_mappingValueAccess.getValue(), is("boar"));
		assertThat(_mappingValueAccess.isLastNode(), is(true));
	}

	@Test
	public void testCorrectCallWithArgs__twoArguments__directValue() throws Exception
	{
		VessNodeCall _define = (VessNodeCall) TestUtils.parseVessSource("call foo using bar as blurg, bear as blarg", getParser());
		VessNodeAccessor _call = _define.getCall();
		assertThat(_call.getValue(), is("foo"));
		assertThat(_call.isLastNode(), is(true));
		assertThat(_define.getMapping(), not(nullValue()));

		VessNodeArgumentMapping _mapping = _define.getMapping();
		assertThat(_mapping.getName(), is("blurg"));
		assertThat(_mapping.isLastNode(), is(false));
		assertThat(_mapping.getValue().getClass().getSimpleName(), is("VessNodeAccessor"));
		VessNodeAccessor _mappingValueAccess = (VessNodeAccessor) _mapping.getValue();
		assertThat(_mappingValueAccess.getValue(), is("bar"));
		assertThat(_mappingValueAccess.isLastNode(), is(true));

		_mapping = (VessNodeArgumentMapping) _mapping.getNext();
		assertThat(_mapping.getName(), is("blarg"));
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
