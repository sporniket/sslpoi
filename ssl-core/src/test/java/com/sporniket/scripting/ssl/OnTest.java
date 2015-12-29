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

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;

import com.sporniket.scripting.ssl.core.InitialisationMode;
import com.sporniket.scripting.ssl.core.LogicalOperator;
import com.sporniket.scripting.ssl.mass.Statement;
import com.sporniket.scripting.ssl.mass.StatementDefineAs;
import com.sporniket.scripting.ssl.mass.TreeBuilder;
import com.sporniket.scripting.ssl.vess.AnalyzerLexical;
import com.sporniket.scripting.ssl.vess.AnalyzerSyntaxic;
import com.sporniket.scripting.ssl.vess.VessNode;
import com.sporniket.scripting.ssl.vess.VessNodeAccessor;
import com.sporniket.scripting.ssl.vess.VessNodeArgumentMapping;
import com.sporniket.scripting.ssl.vess.VessNodeCall;
import com.sporniket.scripting.ssl.vess.VessNodeDefineAs;
import com.sporniket.scripting.ssl.vess.VessNodeExpressionLogical;
import com.sporniket.scripting.ssl.vess.VessNodeIdentifierMapping;
import com.sporniket.scripting.ssl.vess.VessNodeIf;
import com.sporniket.scripting.ssl.vess.VessNodeOn;
import com.sporniket.scripting.ssl.vess.VessNodeOperatorLogical;

/**
 * Test for "on ..." statements.
 * 
 * @author dsporn
 *
 */
public class OnTest
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
	public void testCorrectOn__noMapping() throws Exception
	{
		String[] _sourceRaw =
		{
				"on fooEvent", "    call action1", "    call action2", "endon"
		};
		String _source = TestUtils.makeSource(_sourceRaw);

		// on
		VessNodeOn _on = (VessNodeOn) TestUtils.parseVessSource(_source, getParser());
		testActionListAsListOfCalls(_on, new String[]
		{
				"action1", "action2"
		});
		assertThat(_on.getMapping(), nullValue());
	}

	@Test
	public void testCorrectIf__withMapping() throws Exception
	{
		String[] _sourceRaw =
		{
				"on fooEvent", "with a String named foo, a String[] named bar", "    call action1", "    call action2", "endon"
		};
		String _source = TestUtils.makeSource(_sourceRaw);

		// on
		VessNodeOn _on = (VessNodeOn) TestUtils.parseVessSource(_source, getParser());
		testActionListAsListOfCalls(_on, new String[]
		{
				"action1", "action2"
		});
		assertThat(_on.getMapping(), not(nullValue()));

		VessNodeIdentifierMapping _arg = _on.getMapping();
		assertThat(_arg.getClassName(), is("String"));
		assertThat(_arg.isArray(), is(false));
		assertThat(_arg.getIdentifier(), is("foo"));
		assertThat(_arg.isLastNode(), is(false));

		_arg = (VessNodeIdentifierMapping) _arg.getNext();
		assertThat(_arg.getClassName(), is("String"));
		assertThat(_arg.isArray(), is(true));
		assertThat(_arg.getIdentifier(), is("bar"));
		assertThat(_arg.isLastNode(), is(true));
	}

	private AnalyzerSyntaxic getParser()
	{
		return myParser;
	}

	private void setParser(AnalyzerSyntaxic parser)
	{
		myParser = parser;
	}

	private void testActionListAsListOfCalls(VessNodeOn nodeOn, String[] actionCallNames)
	{
		VessNode _child = nodeOn.getStatements();
		for (int _i = 0; _i < actionCallNames.length; _i++)
		{
			String _callName = actionCallNames[_i];
			assertThat(_child.getClass().getSimpleName(), is(VessNodeCall.class.getSimpleName()));

			VessNodeCall _call = (VessNodeCall) _child;
			assertThat(_call.getCall().getValue(), is(_callName));

			boolean _shouldBeLastNode = (actionCallNames.length - 1) == _i;
			assertThat(_child.isLastNode(), is(_shouldBeLastNode));
			if (!_shouldBeLastNode)
			{
				_child = _child.getNext();
			}
		}
	}
}
