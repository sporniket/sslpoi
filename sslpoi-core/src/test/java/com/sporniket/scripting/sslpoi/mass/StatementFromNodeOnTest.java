/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.sporniket.scripting.sslpoi.core.SslpoiException;
import com.sporniket.scripting.sslpoi.vess.VessNodeAccessor;
import com.sporniket.scripting.sslpoi.vess.VessNodeCall;
import com.sporniket.scripting.sslpoi.vess.VessNodeOn;

/**
 * Test the convertion from {@link VessNodeOn} to {@link StatementOn}.
 * @author dsporn
 *
 */
public class StatementFromNodeOnTest
{
	@Test
	public void testConversion() throws SslpoiException
	{
		VessNodeOn _source = new VessNodeOn().withEventName("fooEvent");
		_source.enqueue(new VessNodeCall().withCall(new VessNodeAccessor().withValue("doSomething")));
		
		StatementOn _result = (StatementOn) StatementFromNode.convert(_source);
		assertThat(_result.getEventName(), is("fooEvent"));
		assertThat(_result.getArgumentMapping().isEmpty(), is(true));
		assertThat(_result.getStatements().isEmpty(), is(false));
		assertThat(_result.getStatements().size(), is(1));

	}
}
