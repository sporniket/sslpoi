/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import org.junit.Test;

import com.sporniket.scripting.sslpoi.core.SslpoiException;
import com.sporniket.scripting.sslpoi.vess.VessNodeAccessor;
import com.sporniket.scripting.sslpoi.vess.VessNodeCall;
import com.sporniket.scripting.sslpoi.vess.VessNodeOn;

/**
 * Test the convertion from {@link VessNodeOn}.
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
		
		Statement _result = StatementFromNode.convert(_source);
		
	}
}
