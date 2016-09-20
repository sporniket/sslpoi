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
import com.sporniket.scripting.sslpoi.vess.VessNodeIdentifierMapping;
import com.sporniket.scripting.sslpoi.vess.VessNodeOn;

/**
 * Test for "on ..." statements.
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
