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
import com.sporniket.scripting.sslpoi.vess.VessNode;
import com.sporniket.scripting.sslpoi.vess.VessNodeCall;
import com.sporniket.scripting.sslpoi.vess.VessNodeIf;

/**
 * Test for "if ..." statements.
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

	@Test
	public void testCorrectIf__withAlternatives_3_noElse() throws Exception
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
