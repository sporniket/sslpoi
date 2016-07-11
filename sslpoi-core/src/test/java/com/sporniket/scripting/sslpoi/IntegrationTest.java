/**
 * 
 */
package com.sporniket.scripting.sslpoi;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import java_cup.runtime.ComplexSymbolFactory;

import org.junit.Before;
import org.junit.Test;

import com.sporniket.scripting.sslpoi.mass.Statement;
import com.sporniket.scripting.sslpoi.mass.StatementFromNode;
import com.sporniket.scripting.sslpoi.vess.AnalyzerLexical;
import com.sporniket.scripting.sslpoi.vess.AnalyzerSyntaxic;
import com.sporniket.scripting.sslpoi.vess.VessNode;

/**
 * Test the convertion from a script to a list of {@link Statement}
 * @author dsporn
 *
 */
public class IntegrationTest
{
	private AnalyzerSyntaxic myParser;

	@Before
	public void setupParserObjects()
	{
		final ComplexSymbolFactory _symbolFactory = new ComplexSymbolFactory();
		final AnalyzerLexical _lexer = new AnalyzerLexical(null);
		_lexer.setSymbolFactory(_symbolFactory);
		setParser(new AnalyzerSyntaxic(_lexer, _symbolFactory));
	}

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
	public void test__v0_01() throws Exception
	{
		String _source = TestUtils.makeSource(SOURCE__V0_01);
		VessNode _parsed = TestUtils.parseVessSource(_source, getParser());
		List<Statement> _compiled = new ArrayList<Statement>(StatementFromNode.convertNodeList(_parsed));
		
		
		fail("not implemented yet");
	}
	public AnalyzerSyntaxic getParser()
	{
		return myParser;
	}
	public void setParser(AnalyzerSyntaxic parser)
	{
		myParser = parser;
	}
}
