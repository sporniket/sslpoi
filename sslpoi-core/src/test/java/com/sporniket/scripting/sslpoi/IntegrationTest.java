/**
 * 
 */
package com.sporniket.scripting.sslpoi;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.sporniket.scripting.sslpoi.mass.Statement;

/**
 * Test the convertion from a script to a list of {@link Statement}
 * @author dsporn
 *
 */
public class IntegrationTest
{
	private static final String[] SOURCE__V0_01 = {
		"define callback1 as new com.foo.Callback",
		"define callback2 as new com.foo.Callback",
		"define callback3 as new com.foo.Callback",
		"",
		"on newSingleLineProperty with a java.lang.String named name, a java.lang.String named value",
		"    if name is like \"special1\\..+\"",
		"        call process from callback1 using name as propertyName, value as propertyValue",
		"    elseif name is like \"special2\\..+\"",
		"        call process from callback2 using name as propertyName, value as propertyValue",
		"    else",
		"        call process from callback3 using name as propertyName, value as propertyValue",
		"    endif",
		"endon",
	} ;
	@Test
	public void test__v0_01()
	{
		fail("not implemented yet");
	}
}
