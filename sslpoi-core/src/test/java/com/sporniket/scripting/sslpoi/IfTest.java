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
import com.sporniket.scripting.sslpoi.core.LogicalOperator;
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
import com.sporniket.scripting.sslpoi.vess.VessNodeExpressionLogical;
import com.sporniket.scripting.sslpoi.vess.VessNodeIf;
import com.sporniket.scripting.sslpoi.vess.VessNodeOperatorLogical;

/**
 * Test for "if ..." statements.
 * 
 * @author dsporn
 *
 */
public class IfTest
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
	public void testCorrectIf__embeddedIf_0() throws Exception
	{
		String[] _sourceRaw =
		{
				"if foo is bar",
				"    if plop is plup",
				"        call action1",
				"        call action2",
				"    endif",
				"else if foo is blup",
				"    call action3",
				"    call action4",
				"else",
				"    call action5",
				"    call action6",
				"endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);

		// if
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		VessNode _action = _if.getStatements();
		assertThat(_action.getClass().getSimpleName(), is(VessNodeIf.class.getSimpleName()));
		VessNodeIf _ifEmbedded = (VessNodeIf) _action;
		testActionListAsListOfCalls(_ifEmbedded, new String[]
		{
				"action1", "action2"
		});
		assertThat(_ifEmbedded.getTest(), not(nullValue()));
		assertThat(_ifEmbedded.getAlternative(), nullValue());
		assertThat(_if.getTest(), not(nullValue()));
		assertThat(_if.getAlternative(), not(nullValue()));

		// else if
		_if = _if.getAlternative();
		testActionListAsListOfCalls(_if, new String[]
		{
				"action3", "action4"
		});
		assertThat(_if.getTest(), not(nullValue()));
		assertThat(_if.getAlternative(), not(nullValue()));

		// else
		_if = _if.getAlternative();
		testActionListAsListOfCalls(_if, new String[]
		{
				"action5", "action6"
		});
		assertThat(_if.getTest(), nullValue());
		assertThat(_if.getAlternative(), nullValue());
	}

	@Test
	public void testCorrectIf__embeddedIf_1() throws Exception
	{
		String[] _sourceRaw =
		{
				"if foo is bar",
				"    call action1",
				"    call action2",
				"else if foo is blup",
				"    if plop is plup",
				"        call action3",
				"        call action4",
				"    endif",
				"else",
				"    call action5",
				"    call action6",
				"endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);

		// if
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		testActionListAsListOfCalls(_if, new String[]
		{
				"action1", "action2"
		});
		assertThat(_if.getTest(), not(nullValue()));
		assertThat(_if.getAlternative(), not(nullValue()));

		// else if
		_if = _if.getAlternative();
		VessNode _action = _if.getStatements();
		assertThat(_action.getClass().getSimpleName(), is(VessNodeIf.class.getSimpleName()));
		VessNodeIf _ifEmbedded = (VessNodeIf) _action;
		testActionListAsListOfCalls(_ifEmbedded, new String[]
		{
				"action3", "action4"
		});
		assertThat(_ifEmbedded.getTest(), not(nullValue()));
		assertThat(_ifEmbedded.getAlternative(), nullValue());
		assertThat(_if.getTest(), not(nullValue()));
		assertThat(_if.getAlternative(), not(nullValue()));

		// else
		_if = _if.getAlternative();
		testActionListAsListOfCalls(_if, new String[]
		{
				"action5", "action6"
		});
		assertThat(_if.getTest(), nullValue());
		assertThat(_if.getAlternative(), nullValue());
	}

	@Test
	public void testCorrectIf__embeddedIf_2() throws Exception
	{
		String[] _sourceRaw =
		{
				"if foo is bar",
				"    call action1",
				"    call action2",
				"else if foo is blup",
				"    call action3",
				"    call action4",
				"else",
				"    if plop is plup",
				"        call action5",
				"        call action6",
				"    endif",
				"endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);

		// if
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		testActionListAsListOfCalls(_if, new String[]
		{
				"action1", "action2"
		});
		assertThat(_if.getTest(), not(nullValue()));
		assertThat(_if.getAlternative(), not(nullValue()));

		// else if
		_if = _if.getAlternative();
		testActionListAsListOfCalls(_if, new String[]
		{
				"action3", "action4"
		});
		assertThat(_if.getTest(), not(nullValue()));
		assertThat(_if.getAlternative(), not(nullValue()));

		// else
		_if = _if.getAlternative();
		VessNode _action = _if.getStatements();
		assertThat(_action.getClass().getSimpleName(), is(VessNodeIf.class.getSimpleName()));
		VessNodeIf _ifEmbedded = (VessNodeIf) _action;
		testActionListAsListOfCalls(_ifEmbedded, new String[]
		{
				"action5", "action6"
		});
		assertThat(_ifEmbedded.getTest(), not(nullValue()));
		assertThat(_ifEmbedded.getAlternative(), nullValue());
		assertThat(_if.getTest(), nullValue());
		assertThat(_if.getAlternative(), nullValue());
	}

	@Test
	public void testCorrectIf__noAlternative() throws Exception
	{
		String[] _sourceRaw =
		{
				"if foo is bar", "    call action1", "    call action2", "endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);

		// if
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		testActionListAsListOfCalls(_if, new String[]
		{
				"action1", "action2"
		});
		assertThat(_if.getTest(), not(nullValue()));
		assertThat(_if.getAlternative(), nullValue());
	}

	@Test
	public void testCorrectIf__withAlternatives_1() throws Exception
	{
		String[] _sourceRaw =
		{
				"if foo is bar", "    call action1", "    call action2", "else", "    call action3", "    call action4", "endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);

		// if
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		testActionListAsListOfCalls(_if, new String[]
		{
				"action1", "action2"
		});
		assertThat(_if.getTest(), not(nullValue()));
		assertThat(_if.getAlternative(), not(nullValue()));

		// else
		_if = _if.getAlternative();
		testActionListAsListOfCalls(_if, new String[]
		{
				"action3", "action4"
		});
		assertThat(_if.getTest(), nullValue());
		assertThat(_if.getAlternative(), nullValue());
	}

	@Test
	public void testCorrectIf__withAlternatives_2() throws Exception
	{
		String[] _sourceRaw =
		{
				"if foo is bar",
				"    call action1",
				"    call action2",
				"else if foo is blup",
				"    call action3",
				"    call action4",
				"else",
				"    call action5",
				"    call action6",
				"endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);

		// if
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		testActionListAsListOfCalls(_if, new String[]
		{
				"action1", "action2"
		});
		assertThat(_if.getTest(), not(nullValue()));
		assertThat(_if.getAlternative(), not(nullValue()));

		// else if
		_if = _if.getAlternative();
		testActionListAsListOfCalls(_if, new String[]
		{
				"action3", "action4"
		});
		assertThat(_if.getTest(), not(nullValue()));
		assertThat(_if.getAlternative(), not(nullValue()));

		// else
		_if = _if.getAlternative();
		testActionListAsListOfCalls(_if, new String[]
		{
				"action5", "action6"
		});
		assertThat(_if.getTest(), nullValue());
		assertThat(_if.getAlternative(), nullValue());
	}

	@Test
	public void testCorrectIf__withAlternatives_3() throws Exception
	{
		String[] _sourceRaw =
		{
				"if foo is bar",
				"    call action1",
				"    call action2",
				"else if foo is blup",
				"    call action3",
				"    call action4",
				"else if foo is blop",
				"    call action5",
				"    call action6",
				"else",
				"    call action7",
				"    call action8",
				"endif"
		};
		String _source = TestUtils.makeSource(_sourceRaw);

		// if
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		testActionListAsListOfCalls(_if, new String[]
		{
				"action1", "action2"
		});
		assertThat(_if.getTest(), not(nullValue()));
		assertThat(_if.getAlternative(), not(nullValue()));

		// else if
		_if = _if.getAlternative();
		testActionListAsListOfCalls(_if, new String[]
		{
				"action3", "action4"
		});
		assertThat(_if.getTest(), not(nullValue()));
		assertThat(_if.getAlternative(), not(nullValue()));

		// else if
		_if = _if.getAlternative();
		testActionListAsListOfCalls(_if, new String[]
		{
				"action5", "action6"
		});
		assertThat(_if.getTest(), not(nullValue()));
		assertThat(_if.getAlternative(), not(nullValue()));

		// else
		_if = _if.getAlternative();
		testActionListAsListOfCalls(_if, new String[]
		{
				"action7", "action8"
		});
		assertThat(_if.getTest(), nullValue());
		assertThat(_if.getAlternative(), nullValue());
	}

	private AnalyzerSyntaxic getParser()
	{
		return myParser;
	}

	private void setParser(AnalyzerSyntaxic parser)
	{
		myParser = parser;
	}

	private void testActionListAsListOfCalls(VessNodeIf nodeIf, String[] actionCallNames)
	{
		VessNode _child = nodeIf.getStatements();
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
