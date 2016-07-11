/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.sporniket.scripting.sslpoi.vess.VessNodeAccessor;
import com.sporniket.scripting.sslpoi.vess.VessNodeArgumentMapping;
import com.sporniket.scripting.sslpoi.vess.VessNodeCall;
import com.sporniket.scripting.sslpoi.vess.VessNodeLiteralString;

/**
 * Test the conversion from {@link VessNodeCall} to {@link StatementCall}.
 * 
 * @author david
 *
 */
public class StatementFromNodeCallTest
{

	private static final String METHOD_NAME = "bar";

	private static final String IDENTIFIER = "toto";

	@Test
	public void testConversion__directCall() throws Exception
	{
		// mapping to litteral strings
		VessNodeArgumentMapping _source1 = new VessNodeArgumentMapping().withName("foo1").withValue(
				new VessNodeLiteralString().withValue("bar1"));
		VessNodeArgumentMapping _source2 = new VessNodeArgumentMapping().withName("foo2").withValue(
				new VessNodeLiteralString().withValue("bar2"));
		_source1.enqueue(_source2);

		VessNodeAccessor _accessor = new VessNodeAccessor().withValue(IDENTIFIER);
		VessNodeCall _node = new VessNodeCall().withCall(_accessor).withMapping(_source1);

		StatementCall _result = (StatementCall) StatementFromNode.convertSingleNode(_node);
		List<String> _methodAccessor = _result.getMethodAccessor();
		assertThat(_methodAccessor, not(nullValue()));
		assertThat(_methodAccessor.isEmpty(), is(false));
		assertThat(_methodAccessor.get(0), is(IDENTIFIER));
		Map<String, PartialExpression> _argumentMapping = _result.getArgumentMapping();
		assertThat(_argumentMapping, not(nullValue()));
		assertThat(_argumentMapping.isEmpty(), is(false));
		assertThat(_argumentMapping.containsKey("foo1"), is(true));
		PartialExpression _partialExpression = _argumentMapping.get("foo1");
		assertThat(_partialExpression.getClass().getName(), is(PartialExpressionLiteralString.class.getName()));
		assertThat(((PartialExpressionLiteralString)_partialExpression).getValue(),is("bar1"));
		assertThat(_argumentMapping.containsKey("foo2"), is(true));
		_partialExpression = _argumentMapping.get("foo2");
		assertThat(_partialExpression.getClass().getName(), is(PartialExpressionLiteralString.class.getName()));
		assertThat(((PartialExpressionLiteralString)_partialExpression).getValue(),is("bar2"));
	}

	@Test
	public void testConversion__indirectCall() throws Exception
	{
		// mapping to litteral strings
		VessNodeArgumentMapping _source1 = new VessNodeArgumentMapping().withName("foo1").withValue(
				new VessNodeLiteralString().withValue("bar1"));
		VessNodeArgumentMapping _source2 = new VessNodeArgumentMapping().withName("foo2").withValue(
				new VessNodeLiteralString().withValue("bar2"));
		_source1.enqueue(_source2);

		VessNodeAccessor _accessor = new VessNodeAccessor().withValue(IDENTIFIER);
		_accessor.enqueue(new VessNodeAccessor().withValue(METHOD_NAME));
		VessNodeCall _node = new VessNodeCall().withCall(_accessor).withMapping(_source1);

		StatementCall _result = (StatementCall) StatementFromNode.convertSingleNode(_node);
		List<String> _methodAccessor = _result.getMethodAccessor();
		assertThat(_methodAccessor, not(nullValue()));
		assertThat(_methodAccessor.isEmpty(), is(false));
		assertThat(_methodAccessor.size(), is(2));
		assertThat(_methodAccessor.get(0), is(IDENTIFIER));
		assertThat(_methodAccessor.get(1), is(METHOD_NAME));
		Map<String, PartialExpression> _argumentMapping = _result.getArgumentMapping();
		assertThat(_argumentMapping, not(nullValue()));
		assertThat(_argumentMapping.isEmpty(), is(false));
		assertThat(_argumentMapping.containsKey("foo1"), is(true));
		PartialExpression _partialExpression = _argumentMapping.get("foo1");
		assertThat(_partialExpression.getClass().getName(), is(PartialExpressionLiteralString.class.getName()));
		assertThat(((PartialExpressionLiteralString)_partialExpression).getValue(),is("bar1"));
		assertThat(_argumentMapping.containsKey("foo2"), is(true));
		_partialExpression = _argumentMapping.get("foo2");
		assertThat(_partialExpression.getClass().getName(), is(PartialExpressionLiteralString.class.getName()));
		assertThat(((PartialExpressionLiteralString)_partialExpression).getValue(),is("bar2"));
	}

}
