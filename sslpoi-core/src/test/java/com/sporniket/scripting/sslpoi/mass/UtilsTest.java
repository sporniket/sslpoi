/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.sporniket.scripting.sslpoi.core.InitialisationMode;
import com.sporniket.scripting.sslpoi.vess.VessNodeAccessor;
import com.sporniket.scripting.sslpoi.vess.VessNodeDefineAs;

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
}
