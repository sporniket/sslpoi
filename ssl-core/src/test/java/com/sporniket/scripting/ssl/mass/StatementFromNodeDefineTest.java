/**
 * 
 */
package com.sporniket.scripting.ssl.mass;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.sporniket.scripting.ssl.core.InitialisationMode;
import com.sporniket.scripting.ssl.vess.VessNodeDefineAs;

/**
 * Test the conversion from {@link VessNodeDefineAs} to {@link StatementDefineAs}.
 * 
 * @author david
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
			false,
			true
	};

	@Test
	public void testConversion() throws Exception
	{
		for (InitialisationMode _mode : InitialisationMode.values())
		{
			for (boolean _isArray : ARRAY_FLAGS)
			{
				VessNodeDefineAs _node = new VessNodeDefineAs(IDENTIFIER, _mode, CLASS_NAME).withArray(_isArray);
				StatementDefineAs _result = (StatementDefineAs) StatementFromNode.convert(_node);
				assertThat(_result.getIdentifier(), is(IDENTIFIER));
				assertThat(_result.getClassName(), is(CLASS_NAME));
				assertThat(_result.getInitialisationMode(), is(_mode));
				assertThat(_result.isArray(), is(_isArray));
			}
		}
	}

}
