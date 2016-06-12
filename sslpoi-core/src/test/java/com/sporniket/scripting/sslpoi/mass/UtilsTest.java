/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.sporniket.scripting.sslpoi.vess.VessNodeAccessor;
import com.sporniket.scripting.sslpoi.vess.VessNodeArgumentMapping;
import com.sporniket.scripting.sslpoi.vess.VessNodeLiteralString;

/**
 * Test utilities.
 * 
 * @author david
 *
 */
public class UtilsTest
{
	/**
	 * Test converting a direct accessor (no <code>from...</code> in the expression).
	 */
	@Test
	public void testAccessorConvertion_direct()
	{
		VessNodeAccessor _source = new VessNodeAccessor().withValue("foo");
		List<String> _accessor = Utils.accessorFromVessNodeAccessor(_source);
		assertThat(_accessor, notNullValue());
		assertThat(_accessor.isEmpty(), is(false));
		assertThat(_accessor.size(), is(1));
		assertThat(_accessor.get(0), is("foo"));
	}

	/**
	 * Test converting an accessor with one level of indirection (<code>foo from bar</code>).
	 */
	@Test
	public void testAccessorConvertion_indirect1()
	{
		VessNodeAccessor _source = new VessNodeAccessor().withValue("foo");
		VessNodeAccessor _sourceFromBar = new VessNodeAccessor().withValue("bar");
		_sourceFromBar.enqueue(_source);
		List<String> _accessor = Utils.accessorFromVessNodeAccessor(_sourceFromBar);
		assertThat(_accessor, notNullValue());
		assertThat(_accessor.isEmpty(), is(false));
		assertThat(_accessor.size(), is(2));
		assertThat(_accessor.get(0), is("bar"));
		assertThat(_accessor.get(1), is("foo"));
	}

	/**
	 * Test converting an accessor with two level of indirection (<code>foo from bar from ter</code>).
	 */
	@Test
	public void testAccessorConvertion_indirect2()
	{
		VessNodeAccessor _source = new VessNodeAccessor().withValue("foo");
		VessNodeAccessor _sourceFromBar = new VessNodeAccessor().withValue("bar");
		_sourceFromBar.enqueue(_source);
		VessNodeAccessor _sourceFromBarFromTer = new VessNodeAccessor().withValue("ter");
		_sourceFromBarFromTer.enqueue(_sourceFromBar);
		List<String> _accessor = Utils.accessorFromVessNodeAccessor(_sourceFromBarFromTer);
		assertThat(_accessor, notNullValue());
		assertThat(_accessor.isEmpty(), is(false));
		assertThat(_accessor.size(), is(3));
		assertThat(_accessor.get(0), is("ter"));
		assertThat(_accessor.get(1), is("bar"));
		assertThat(_accessor.get(2), is("foo"));
	}

	/**
	 * Test the basic conversion of an argument mapping.
	 */
	@Test
	public void testArgumentMappingConvertion()
	{
		// mapping to litteral strings
		VessNodeArgumentMapping _source1 = new VessNodeArgumentMapping().withName("foo1")
				.withValue(new VessNodeLiteralString().withValue("bar1"));
		VessNodeArgumentMapping _source2 = new VessNodeArgumentMapping().withName("foo2")
				.withValue(new VessNodeLiteralString().withValue("bar2"));

		// mapping to accessor
		VessNodeAccessor _value = new VessNodeAccessor().withValue("foo");
		VessNodeAccessor _valueFromBar = new VessNodeAccessor().withValue("bar");
		_valueFromBar.enqueue(_value);
		VessNodeArgumentMapping _source3 = new VessNodeArgumentMapping().withName("foo3").withValue(_valueFromBar);
		_source1.enqueue(_source2);
		_source1.enqueue(_source3);

		Map<String, PartialExpression> _mapping = Utils.argumentMappingFromVessNodeArgumentMapping(_source1);

		// testing the conversion result : general
		assertThat(_mapping, notNullValue());
		assertThat(_mapping.isEmpty(), is(false));
		assertThat(_mapping.size(), is(3));

		// testing the conversion result : item by item
		String _name;
		PartialExpression _expression;

		_name = "foo1";
		assertThat(_mapping.containsKey(_name), is(true));
		_expression = _mapping.get(_name);
		assertThat(_expression.getClass().getName(), is(PartialExpressionLiteralString.class.getName()));
		assertThat(((PartialExpressionLiteralString) _expression).getValue(), is("bar1"));

		_name = "foo2";
		assertThat(_mapping.containsKey(_name), is(true));
		_expression = _mapping.get(_name);
		assertThat(_expression.getClass().getName(), is(PartialExpressionLiteralString.class.getName()));
		assertThat(((PartialExpressionLiteralString) _expression).getValue(), is("bar2"));

		_name = "foo3";
		assertThat(_mapping.containsKey(_name), is(true));
		_expression = _mapping.get(_name);
		assertThat(_expression.getClass().getName(), is(PartialExpressionAccessor.class.getName()));
		final List<String> _accessStack = ((PartialExpressionAccessor) _expression).getAccessStack();
		assertThat(_accessStack, notNullValue());
		assertThat(_accessStack.isEmpty(), is(false));
		assertThat(_accessStack.size(), is(2));
		assertThat(_accessStack.get(0), is("bar"));
		assertThat(_accessStack.get(1), is("foo"));

	}
}
