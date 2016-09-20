/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.sporniket.scripting.sslpoi.core.LogicalOperator;
import com.sporniket.scripting.sslpoi.core.NotSupportedException;
import com.sporniket.scripting.sslpoi.core.SslpoiException;
import com.sporniket.scripting.sslpoi.vess.VessNodeAccessor;
import com.sporniket.scripting.sslpoi.vess.VessNodeExpressionLogical;
import com.sporniket.scripting.sslpoi.vess.VessNodeLiteralString;
import com.sporniket.scripting.sslpoi.vess.VessNodeOperatorLogical;
import com.sporniket.scripting.sslpoi.vess.VessNodeValue;

/**
 * Test {@link VessNodeValue} conversion into {@link PartialExpression}.
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
public class PartialExpressionFromNodeValueTest
{
	/**
	 * Dummy class for testing conversion of a not supported class.
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
	 * <i>Sporny Script Language (Pun Obviously Intended) &#8211; core</i> is free software: you can redistribute it and/or modify
	 * it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of
	 * the License, or (at your option) any later version.
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
	private static class Foo implements VessNodeValue
	{

	}

	@Test
	public void testFromVessNodeAccessor() throws SslpoiException
	{
		VessNodeAccessor _tested = new VessNodeAccessor().withValue("bar");
		_tested.enqueue(new VessNodeAccessor().withValue("foo"));

		PartialExpression _result = PartialExpressionFromNodeValue.convert(_tested);

		// General tests
		assertThat(_result, notNullValue());
		assertThat(_result.getClass().getName(), is(PartialExpressionAccessor.class.getName()));

		// Specifics
		PartialExpressionAccessor _accessor = (PartialExpressionAccessor) _result;
		List<String> _accessStack = _accessor.getAccessStack();
		assertThat(_accessStack.isEmpty(), is(false));
		assertThat(_accessStack.size(), is(2));
		assertThat(_accessStack.get(0), is("bar"));
		assertThat(_accessStack.get(1), is("foo"));
	}

	@Test
	public void testFromVessNodeExpressionLogical() throws SslpoiException
	{
		VessNodeAccessor _left = new VessNodeAccessor().withValue("bar");
		_left.enqueue(new VessNodeAccessor().withValue("foo"));
		VessNodeValue _right = new VessNodeLiteralString().withValue("foo");
		VessNodeOperatorLogical _operator = new VessNodeOperatorLogical().withOperator(LogicalOperator.IS);
		VessNodeExpressionLogical _tested = new VessNodeExpressionLogical().withValue(_left).withExpected(_right)
				.withOperator(_operator);

		PartialExpression _result = PartialExpressionFromNodeValue.convert(_tested);

		// General tests
		assertThat(_result, notNullValue());
		assertThat(_result.getClass().getName(), is(PartialExpressionLogical.class.getName()));

		// Specifics
		PartialExpressionLogical _logical = (PartialExpressionLogical) _result;
		assertThat(_logical.getLeftExpression().getClass().getName(), is(PartialExpressionAccessor.class.getName()));
		assertThat(_logical.getRightExpression().getClass().getName(), is(PartialExpressionLiteralString.class.getName()));
		assertThat(_logical.getOperator(), is(LogicalOperator.IS));
		assertThat(_logical.isNot(), is(false));

		// Same test with a reversed operator
		_operator.withNot(true);

		_result = PartialExpressionFromNodeValue.convert(_tested);

		// General tests
		assertThat(_result, notNullValue());
		assertThat(_result.getClass().getName(), is(PartialExpressionLogical.class.getName()));

		// Specifics
		_logical = (PartialExpressionLogical) _result;
		assertThat(_logical.getLeftExpression().getClass().getName(), is(PartialExpressionAccessor.class.getName()));
		assertThat(_logical.getRightExpression().getClass().getName(), is(PartialExpressionLiteralString.class.getName()));
		assertThat(_logical.getOperator(), is(LogicalOperator.IS));
		assertThat(_logical.isNot(), is(true));
	}

	@Test
	public void testFromVessNodeLiteralString() throws SslpoiException
	{
		VessNodeValue _tested = new VessNodeLiteralString().withValue("foo");

		PartialExpression _result = PartialExpressionFromNodeValue.convert(_tested);

		// General tests
		assertThat(_result, notNullValue());
		assertThat(_result.getClass().getName(), is(PartialExpressionLiteralString.class.getName()));

		// Specifics
		PartialExpressionLiteralString _literalString = (PartialExpressionLiteralString) _result;
		assertThat(_literalString.getValue(), is("foo"));
	}

	/**
	 * Test converting a not supported class.
	 * 
	 * @throws SslpoiException
	 */
	@Test(expected = NotSupportedException.class)
	public void testNotSupported() throws SslpoiException
	{
		PartialExpression _result = PartialExpressionFromNodeValue.convert(new Foo());
	}
}
