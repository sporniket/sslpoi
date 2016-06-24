/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.sporniket.scripting.sslpoi.core.SslpoiException;
import com.sporniket.scripting.sslpoi.vess.VessNodeLiteralString;
import com.sporniket.scripting.sslpoi.vess.VessNodeValue;

/**
 * Test {@link VessNodeValue} conversion into {@link PartialExpression}.
 * @author dsporn
 *
 */
public class PartialExpressionFromNodeValueTest
{
	@Test
	public void testFromVessNodeLiteralString() throws SslpoiException
	{
		VessNodeValue _tested = new VessNodeLiteralString().withValue("foo");
		PartialExpression _result = PartialExpressionFromNodeValue.convert(_tested);
		assertThat(_result, notNullValue());
		assertThat(_result.getClass().getName(), is(PartialExpressionLiteralString.class.getName()));
		PartialExpressionLiteralString _literalString = (PartialExpressionLiteralString) _result ;
		assertThat(_literalString.getValue(), is("foo"));
	}
}
