/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.sporniket.scripting.sslpoi.core.SslpoiException;
import com.sporniket.scripting.sslpoi.vess.VessNode;
import com.sporniket.scripting.sslpoi.vess.VessNodeAccessor;
import com.sporniket.scripting.sslpoi.vess.VessNodeCall;
import com.sporniket.scripting.sslpoi.vess.VessNodeIdentifierMapping;
import com.sporniket.scripting.sslpoi.vess.VessNodeOn;

/**
 * Test the convertion from {@link VessNodeOn} to {@link StatementOn}.
 * 
 * @author dsporn
 *
 */
public class StatementFromNodeOnTest
{
	@Test
	public void testConversion() throws SslpoiException
	{
		VessNodeOn _source = new VessNodeOn().withEventName("fooEvent").withStatements(
				new VessNodeCall().withCall(new VessNodeAccessor().withValue("doSomething")).withLastNode(
						new VessNodeCall().withCall(new VessNodeAccessor().withValue("doSomethingElse"))));

		StatementOn _result = (StatementOn) StatementFromNode.convertSingleNode(_source);
		assertThat(_result.getEventName(), is("fooEvent"));
		assertThat(_result.getIdentifierMapping().isEmpty(), is(true));
		assertThat(_result.getStatements().isEmpty(), is(false));
		assertThat(_result.getStatements().size(), is(2));

		VessNodeIdentifierMapping _mapping = new VessNodeIdentifierMapping().withIdentifier("foo").withClassName("bar");
		_mapping.withLastNode(new VessNodeIdentifierMapping().withIdentifier("foo2").withClassName("bar.bar").withArray(true));
		_source.withMapping(_mapping);

		_result = (StatementOn) StatementFromNode.convertSingleNode(_source);
		assertThat(_result.getEventName(), is("fooEvent"));
		assertThat(_result.getIdentifierMapping().isEmpty(), is(false));
		assertThat(_result.getIdentifierMapping().size(), is(2));
		assertThat(_result.getStatements().isEmpty(), is(false));
		assertThat(_result.getStatements().size(), is(2));
	}
}
