/**
 * 
 */
package com.sporniket.scripting.ssl;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.Symbol;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;

import com.sporniket.scripting.ssl.core.InitialisationMode;
import com.sporniket.scripting.ssl.core.LogicalOperator;
import com.sporniket.scripting.ssl.mass.Statement;
import com.sporniket.scripting.ssl.mass.StatementDefineAs;
import com.sporniket.scripting.ssl.mass.TreeBuilder;
import com.sporniket.scripting.ssl.vess.AnalyzerLexical;
import com.sporniket.scripting.ssl.vess.AnalyzerSyntaxic;
import com.sporniket.scripting.ssl.vess.VessNode;
import com.sporniket.scripting.ssl.vess.VessNodeAccessor;
import com.sporniket.scripting.ssl.vess.VessNodeArgumentMapping;
import com.sporniket.scripting.ssl.vess.VessNodeCall;
import com.sporniket.scripting.ssl.vess.VessNodeDefineAs;
import com.sporniket.scripting.ssl.vess.VessNodeExpressionLogical;
import com.sporniket.scripting.ssl.vess.VessNodeIf;
import com.sporniket.scripting.ssl.vess.VessNodeOperatorLogical;

/**
 * Test for "if ..." statements.
 * 
 * @author dsporn
 *
 */
public class IfTests
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

	@Test
	public void testCorrectIf__simple() throws Exception
	{
		String[] _sourceRaw = {"if foo is bar", "call action1", "call action2","endif"} ;
		String _source = TestUtils.makeSource(_sourceRaw);
		VessNodeIf _if = (VessNodeIf) TestUtils.parseVessSource(_source, getParser());
		VessNodeExpressionLogical _logic = _if.getTest() ;
		VessNode _action = _if.getStatements() ;
		assertThat(_logic.getValue().getClass().getSimpleName(),is(VessNodeAccessor.class.getSimpleName()));
		assertThat(_logic.getExpected().getClass().getSimpleName(),is(VessNodeAccessor.class.getSimpleName()));
		VessNodeAccessor _value = (VessNodeAccessor) _logic.getValue();
		VessNodeAccessor _expected = (VessNodeAccessor) _logic.getExpected();
		VessNodeOperatorLogical _op = _logic.getOperator();
		assertThat(_op.isNot(),is(false));
		assertThat(_op.getOperator(),is(LogicalOperator.IS));
		assertThat(_value.getValue(),is("foo"));
		assertThat(_expected.getValue(),is("bar"));
		assertThat(_action.getClass().getSimpleName(), is(VessNodeCall.class.getSimpleName()));
		assertThat(_action.isLastNode(), is(false));
		assertThat(_if.getNext(), nullValue());
	}

	private AnalyzerSyntaxic getParser()
	{
		return myParser;
	}

	private void setParser(AnalyzerSyntaxic parser)
	{
		myParser = parser;
	}

}
