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
 * 
 */

/**
 * Test the convertion from {@link VessNodeIf} to {@link StatementIf}.
 * 
 * @author dsporn
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
	 *            th
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
