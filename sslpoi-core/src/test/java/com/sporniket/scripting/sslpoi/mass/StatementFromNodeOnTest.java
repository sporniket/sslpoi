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
 * @version 0.1.0
 * @since 0.1.0
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
