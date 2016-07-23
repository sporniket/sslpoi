/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.sporniket.scripting.sslpoi.core.InitialisationMode;
import com.sporniket.scripting.sslpoi.vess.VessNodeDefineAs;

/**
 * Test the conversion from {@link VessNodeDefineAs} to {@link StatementDefineAs}.
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
public class StatementFromNodeDefineTest
{

	private static final String CLASS_NAME = "bar";

	private static final String IDENTIFIER = "toto";

	/**
	 * list of testable values for the array flag in VessNode.
	 */
	private static final boolean[] ARRAY_FLAGS =
	{
			false, true
	};

	@Test
	public void testConversion() throws Exception
	{
		for (InitialisationMode _mode : InitialisationMode.values())
		{
			for (boolean _isArray : ARRAY_FLAGS)
			{
				VessNodeDefineAs _node = new VessNodeDefineAs(IDENTIFIER, _mode, CLASS_NAME).withArray(_isArray);
				StatementDefineAs _result = (StatementDefineAs) StatementFromNode.convertSingleNode(_node);
				assertThat(_result.getIdentifier().getIdentifier(), is(IDENTIFIER));
				assertThat(_result.getIdentifier().getClassName(), is(CLASS_NAME));
				assertThat(_result.getInitialisationMode(), is(_mode));
				assertThat(_result.getIdentifier().isArray(), is(_isArray));
			}
		}
	}

}
