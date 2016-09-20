package com.sporniket.scripting.sslpoi.mass;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.sporniket.scripting.sslpoi.core.LogicalOperator;
import com.sporniket.scripting.sslpoi.core.SslpoiException;
import com.sporniket.scripting.sslpoi.vess.VessNodeAccessor;
import com.sporniket.scripting.sslpoi.vess.VessNodeCall;
import com.sporniket.scripting.sslpoi.vess.VessNodeExpressionLogical;
import com.sporniket.scripting.sslpoi.vess.VessNodeIf;
import com.sporniket.scripting.sslpoi.vess.VessNodeLiteralString;
import com.sporniket.scripting.sslpoi.vess.VessNodeOperatorLogical;

/**
 * Test the convertion from {@link VessNodeIf} to {@link StatementIf}.
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
public class StatementFromNodeIfTest
{
	/**
	 * Test 'if ... else... elseif ...' construction.
	 * 
	 * @throws SslpoiException
	 *             when there is a problem.
	 */
	@Test(expected = SslpoiException.class)
	public void testConvertion__malformed() throws SslpoiException
	{
		VessNodeIf _if = new VessNodeIf().withTest(createTest()).withStatement(createCall("doSomething"));
		VessNodeIf _elseif = new VessNodeIf().withTest(createTest()).withStatement(createCall("doSomethingElseIf"));
		VessNodeIf _else = new VessNodeIf().withStatement(createCall("doSomethingElse"));
		_if.withAlternative(_else);
		_else.withAlternative(_elseif);

		StatementFromNode.convertSingleNode(_if);
		fail("Conversion of malformed VessNodeIf chain MUST fail");
	}

	/**
	 * Test full 'if ... else if... else' expression.
	 * 
	 * @throws SslpoiException
	 *             when there is a problem.
	 */
	@Test
	public void testConvertion__multiple() throws SslpoiException
	{
		StatementAlternative _alternative;
		StatementCall _call;

		VessNodeIf _if = new VessNodeIf().withTest(createTest()).withStatement(createCall("doSomething"));
		VessNodeIf _elseif = new VessNodeIf().withTest(createTest()).withStatement(createCall("doSomethingElseIf"));
		VessNodeIf _else = new VessNodeIf().withStatement(createCall("doSomethingElse"));
		_if.withAlternative(_elseif);
		_elseif.withAlternative(_else);

		StatementIf _result = (StatementIf) StatementFromNode.convertSingleNode(_if);
		assertThat(_result.getAlternatives().isEmpty(), is(false));
		assertThat(_result.getAlternatives().size(), is(3));

		// check 'if...' section
		_alternative = _result.getAlternatives().get(0);
		assertThat(_alternative.getTest(), not(nullValue()));
		assertThat(_alternative.getStatements().isEmpty(), is(false));
		assertThat(_alternative.getStatements().size(), is(1));

		_call = (StatementCall) _alternative.getStatements().get(0);
		assertThat(_call.getMethodAccessor().size(), is(1));
		assertThat(_call.getMethodAccessor().get(0), is("doSomething"));

		// check 'else if...' section
		_alternative = _result.getAlternatives().get(1);
		assertThat(_alternative.getTest(), not(nullValue()));
		assertThat(_alternative.getStatements().isEmpty(), is(false));
		assertThat(_alternative.getStatements().size(), is(1));

		_call = (StatementCall) _alternative.getStatements().get(0);
		assertThat(_call.getMethodAccessor().size(), is(1));
		assertThat(_call.getMethodAccessor().get(0), is("doSomethingElseIf"));

		// check 'else ...' section
		_alternative = _result.getAlternatives().get(2);
		assertThat(_alternative.getTest(), nullValue());
		assertThat(_alternative.getStatements().isEmpty(), is(false));
		assertThat(_alternative.getStatements().size(), is(1));

		_call = (StatementCall) _alternative.getStatements().get(0);
		assertThat(_call.getMethodAccessor().size(), is(1));
		assertThat(_call.getMethodAccessor().get(0), is("doSomethingElse"));
	}

	/**
	 * Test simple 'if ...' expression.
	 * 
	 * @throws SslpoiException
	 *             when there is a problem.
	 */
	@Test
	public void testConvertion__simple() throws SslpoiException
	{
		VessNodeIf _if = new VessNodeIf().withTest(createTest()).withStatement(createCall("doSomething"));

		StatementIf _result = (StatementIf) StatementFromNode.convertSingleNode(_if);
		assertThat(_result.getAlternatives().isEmpty(), is(false));
		assertThat(_result.getAlternatives().size(), is(1));

		StatementAlternative _alternative = _result.getAlternatives().get(0);
		assertThat(_alternative.getTest(), not(nullValue()));
		assertThat(_alternative.getStatements().isEmpty(), is(false));
		assertThat(_alternative.getStatements().size(), is(1));

		StatementCall _call = (StatementCall) _alternative.getStatements().get(0);
		assertThat(_call.getMethodAccessor().size(), is(1));
		assertThat(_call.getMethodAccessor().get(0), is("doSomething"));
	}

	/**
	 * @param callName
	 * 
	 * @return
	 */
	private VessNodeCall createCall(String callName)
	{
		return new VessNodeCall().withCall(new VessNodeAccessor().withValue(callName));
	}

	/**
	 * @return
	 */
	private VessNodeExpressionLogical createTest()
	{
		VessNodeLiteralString _literalFoo = new VessNodeLiteralString().withValue("foo");
		VessNodeLiteralString _literalBar = new VessNodeLiteralString().withValue("bar");
		VessNodeExpressionLogical _test = new VessNodeExpressionLogical().withValue(_literalFoo)
				.withOperator(new VessNodeOperatorLogical().withOperator(LogicalOperator.IS).withNot(true))
				.withExpected(_literalBar);
		return _test;
	}
}
