/**
 * 
 */
package com.sporniket.scripting.sslpoi;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java_cup.runtime.ComplexSymbolFactory;

import org.junit.Before;
import org.junit.Test;

import com.sporniket.scripting.sslpoi.mass.Statement;
import com.sporniket.scripting.sslpoi.mass.StatementAlternative;
import com.sporniket.scripting.sslpoi.mass.StatementDefineAs;
import com.sporniket.scripting.sslpoi.mass.StatementFromNode;
import com.sporniket.scripting.sslpoi.mass.StatementIf;
import com.sporniket.scripting.sslpoi.mass.StatementOn;
import com.sporniket.scripting.sslpoi.vess.AnalyzerLexical;
import com.sporniket.scripting.sslpoi.vess.AnalyzerSyntaxic;
import com.sporniket.scripting.sslpoi.vess.VessNode;

/**
 * Test the convertion from a script to a list of {@link Statement}
 * 
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

	private static final String[] SOURCE__V0_01 =
	{
			"define callback1 as new com.foo.Callback",
			"define callback2 as new com.foo.Callback",
			"define callback3 as new com.foo.Callback",
			"",
			"on newSingleLineProperty with a java.lang.String named name, a java.lang.String named value",
			"    if name is like \"special1\\\\..+\"",
			"        call process from callback1 using name as propertyName, value as propertyValue",
			"    else if name is like \"special2\\\\..+\"",
			"        call process from callback2 using name as propertyName, value as propertyValue",
			"    else",
			"        call process from callback3 using name as propertyName, value as propertyValue",
			"    endif",
			"endon",
	};

	@Test
	public void test__v0_01() throws Exception
	{
		String _source = TestUtils.makeSource(SOURCE__V0_01);
		VessNode _parsed = TestUtils.parseVessSource(_source, getParser());
		List<Statement> _compiled = new ArrayList<Statement>(StatementFromNode.convertNodeList(_parsed));

		// ok, now is the most tedious part : testing each statement one by one
		Statement _current;
		Iterator<Statement> _iterator = _compiled.iterator();

		// define callback 1
		_current = _iterator.next();
		assertThat(_current.getClass().getName(), is(StatementDefineAs.class.getName()));

		// define callback 2
		_current = _iterator.next();
		assertThat(_current.getClass().getName(), is(StatementDefineAs.class.getName()));

		// define callback 3
		_current = _iterator.next();
		assertThat(_current.getClass().getName(), is(StatementDefineAs.class.getName()));

		// on newSingleLineProperty
		_current = _iterator.next();
		assertThat(_current.getClass().getName(), is(StatementOn.class.getName()));
		{
			StatementOn _statementOn = (StatementOn) _current;

			List<Statement> _statements = _statementOn.getStatements();
			assertThat(_statements.isEmpty(), not(nullValue()));
			assertThat(_statements.size(), is(1));
			Iterator<Statement> _subIterator = _statements.iterator();

			// on newSingleLineProperty - if
			_current = _subIterator.next();
			assertThat(_current.getClass().getName(), is(StatementIf.class.getName()));
			{
				StatementIf _statementIf = (StatementIf) _current;

				List<StatementAlternative> _alternatives = _statementIf.getAlternatives();
				assertThat(_alternatives.isEmpty(), not(nullValue()));
				assertThat(_alternatives.size(), is(3));
				Iterator<StatementAlternative> _subSubIterator = _alternatives.iterator();
				StatementAlternative _currentAlternative;

				// on newSingleLineProperty - if - first if
				_currentAlternative = _subSubIterator.next();
				{
					List<Statement> _altStatements = _currentAlternative.getStatements() ;
					assertThat(_altStatements.isEmpty(), not(nullValue()));
					assertThat(_altStatements.size(), is(1));
					Iterator<Statement> _subSubSubIterator = _altStatements.iterator() ;

					// on newSingleLineProperty - if - first if - call
					_current = _subSubSubIterator.next() ;
				}

				// on newSingleLineProperty - if - else if
				_currentAlternative = _subSubIterator.next();
				{
					List<Statement> _altStatements = _currentAlternative.getStatements() ;
					assertThat(_altStatements.isEmpty(), not(nullValue()));
					assertThat(_altStatements.size(), is(1));
					Iterator<Statement> _subSubSubIterator = _altStatements.iterator() ;

					// on newSingleLineProperty - if - else if - call
					_current = _subSubSubIterator.next() ;
				}

				// on newSingleLineProperty - if - else
				_currentAlternative = _subSubIterator.next();
				{
					List<Statement> _altStatements = _currentAlternative.getStatements() ;
					assertThat(_altStatements.isEmpty(), not(nullValue()));
					assertThat(_altStatements.size(), is(1));
					Iterator<Statement> _subSubSubIterator = _altStatements.iterator() ;

					// on newSingleLineProperty - if - else - call
					_current = _subSubSubIterator.next() ;
				}
			}
		}

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
