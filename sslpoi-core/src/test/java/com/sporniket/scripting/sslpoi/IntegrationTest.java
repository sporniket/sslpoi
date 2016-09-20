/**
 * 
 */
package com.sporniket.scripting.sslpoi;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java_cup.runtime.ComplexSymbolFactory;

import org.junit.Before;
import org.junit.Test;

import com.sporniket.scripting.sslpoi.core.InitialisationMode;
import com.sporniket.scripting.sslpoi.core.LogicalOperator;
import com.sporniket.scripting.sslpoi.mass.PartialExpression;
import com.sporniket.scripting.sslpoi.mass.PartialExpressionAccessor;
import com.sporniket.scripting.sslpoi.mass.PartialExpressionLiteralString;
import com.sporniket.scripting.sslpoi.mass.PartialExpressionLogical;
import com.sporniket.scripting.sslpoi.mass.PartialIdentifier;
import com.sporniket.scripting.sslpoi.mass.Statement;
import com.sporniket.scripting.sslpoi.mass.StatementAlternative;
import com.sporniket.scripting.sslpoi.mass.StatementCall;
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
 * @version 0.2.0
 * @since 0.1.0
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
			"        call process from callback1 using name, value",
			"    else if name is like \"special2\\\\..+\"",
			"        call process from callback2 using name, value",
			"    else",
			"        call process from callback3 using name, value",
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
		{
			StatementDefineAs _define = (StatementDefineAs) _current;
			assertThat(_define.getIdentifier().getIdentifier(), is("callback1"));
			assertThat(_define.getIdentifier().getClassName(), is("com.foo.Callback"));
			assertThat(_define.getIdentifier().isArray(), is(false));
			assertThat(_define.getInitialisationMode(), is(InitialisationMode.NEW));
		}

		// define callback 2
		_current = _iterator.next();
		assertThat(_current.getClass().getName(), is(StatementDefineAs.class.getName()));
		{
			StatementDefineAs _define = (StatementDefineAs) _current;
			assertThat(_define.getIdentifier().getIdentifier(), is("callback2"));
			assertThat(_define.getIdentifier().getClassName(), is("com.foo.Callback"));
			assertThat(_define.getIdentifier().isArray(), is(false));
			assertThat(_define.getInitialisationMode(), is(InitialisationMode.NEW));
		}

		// define callback 3
		_current = _iterator.next();
		assertThat(_current.getClass().getName(), is(StatementDefineAs.class.getName()));
		{
			StatementDefineAs _define = (StatementDefineAs) _current;
			assertThat(_define.getIdentifier().getIdentifier(), is("callback3"));
			assertThat(_define.getIdentifier().getClassName(), is("com.foo.Callback"));
			assertThat(_define.getIdentifier().isArray(), is(false));
			assertThat(_define.getInitialisationMode(), is(InitialisationMode.NEW));
		}

		// on newSingleLineProperty
		_current = _iterator.next();
		assertThat(_current.getClass().getName(), is(StatementOn.class.getName()));
		{
			StatementOn _statementOn = (StatementOn) _current;
			assertThat(_statementOn.getEventName(), is("newSingleLineProperty"));

			// test identifiers
			List<PartialIdentifier> _identifierMapping = _statementOn.getIdentifierMapping();
			assertThat(_identifierMapping.isEmpty(), is(false));
			assertThat(_identifierMapping.size(), is(2));

			PartialIdentifier _identifier;
			Iterator<PartialIdentifier> _identifierIterator = _identifierMapping.iterator();
			_identifier = _identifierIterator.next();
			assertThat(_identifier.getIdentifier(), is("name"));
			assertThat(_identifier.getClassName(), is("java.lang.String"));
			assertThat(_identifier.isArray(), is(false));
			_identifier = _identifierIterator.next();
			assertThat(_identifier.getIdentifier(), is("value"));
			assertThat(_identifier.getClassName(), is("java.lang.String"));
			assertThat(_identifier.isArray(), is(false));

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
					PartialExpressionLogical _test = _currentAlternative.getTest();
					assertThat(_test, is(not(nullValue())));
					assertThat(_test.getOperator(), is(LogicalOperator.IS_LIKE));
					assertThat(_test.getLeftExpression().getClass().getName(), is(PartialExpressionAccessor.class.getName()));
					{
						PartialExpressionAccessor _accessor = (PartialExpressionAccessor) _test.getLeftExpression();
						assertThat(_accessor.getAccessStack().size(), is(1));
						assertThat(_accessor.getAccessStack().get(0), is("name"));
					}
					assertThat(_test.getRightExpression().getClass().getName(), is(PartialExpressionLiteralString.class.getName()));
					{
						PartialExpressionLiteralString _literal = (PartialExpressionLiteralString) _test.getRightExpression();
						assertThat(_literal.getValue(), is("special1\\..+"));
					}

					List<Statement> _altStatements = _currentAlternative.getStatements();
					assertThat(_altStatements.isEmpty(), not(nullValue()));
					assertThat(_altStatements.size(), is(1));
					Iterator<Statement> _subSubSubIterator = _altStatements.iterator();

					// on newSingleLineProperty - if - first if - call
					_current = _subSubSubIterator.next();
					assertThat(_current.getClass().getName(), is(StatementCall.class.getName()));
					{
						StatementCall _call = (StatementCall) _current;
						List<String> _methodAccessor = _call.getMethodAccessor();
						assertThat(_methodAccessor.size(), is(2));
						assertThat(_methodAccessor.get(0), is("callback1"));
						assertThat(_methodAccessor.get(1), is("process"));

						PartialExpression _partialExpression;
						List<PartialExpression> _argumentMapping = _call.getArgumentMapping();
						assertThat(_argumentMapping.size(), is(2));

						_partialExpression = _argumentMapping.get(0);
						assertThat(_partialExpression.getClass().getName(), is(PartialExpressionAccessor.class.getName()));
						{
							PartialExpressionAccessor _accessor = (PartialExpressionAccessor) _partialExpression;
							assertThat(_accessor.getAccessStack().size(), is(1));
							assertThat(_accessor.getAccessStack().get(0), is("name"));
						}

						_partialExpression = _argumentMapping.get(1);
						assertThat(_partialExpression.getClass().getName(), is(PartialExpressionAccessor.class.getName()));
						{
							PartialExpressionAccessor _accessor = (PartialExpressionAccessor) _partialExpression;
							assertThat(_accessor.getAccessStack().size(), is(1));
							assertThat(_accessor.getAccessStack().get(0), is("value"));
						}
					}
				}

				// on newSingleLineProperty - if - else if
				_currentAlternative = _subSubIterator.next();
				{
					PartialExpressionLogical _test = _currentAlternative.getTest();
					assertThat(_test, is(not(nullValue())));
					assertThat(_test.getOperator(), is(LogicalOperator.IS_LIKE));
					assertThat(_test.getLeftExpression().getClass().getName(), is(PartialExpressionAccessor.class.getName()));
					{
						PartialExpressionAccessor _accessor = (PartialExpressionAccessor) _test.getLeftExpression();
						assertThat(_accessor.getAccessStack().size(), is(1));
						assertThat(_accessor.getAccessStack().get(0), is("name"));
					}
					assertThat(_test.getRightExpression().getClass().getName(), is(PartialExpressionLiteralString.class.getName()));
					{
						PartialExpressionLiteralString _literal = (PartialExpressionLiteralString) _test.getRightExpression();
						assertThat(_literal.getValue(), is("special2\\..+"));
					}

					List<Statement> _altStatements = _currentAlternative.getStatements();
					assertThat(_altStatements.isEmpty(), not(nullValue()));
					assertThat(_altStatements.size(), is(1));
					Iterator<Statement> _subSubSubIterator = _altStatements.iterator();

					// on newSingleLineProperty - if - else if - call
					_current = _subSubSubIterator.next();
					assertThat(_current.getClass().getName(), is(StatementCall.class.getName()));
					{
						StatementCall _call = (StatementCall) _current;
						List<String> _methodAccessor = _call.getMethodAccessor();
						assertThat(_methodAccessor.size(), is(2));
						assertThat(_methodAccessor.get(0), is("callback2"));
						assertThat(_methodAccessor.get(1), is("process"));

						PartialExpression _partialExpression;
						List<PartialExpression> _argumentMapping = _call.getArgumentMapping();
						assertThat(_argumentMapping.size(), is(2));

						_partialExpression = _argumentMapping.get(0);
						assertThat(_partialExpression.getClass().getName(), is(PartialExpressionAccessor.class.getName()));
						{
							PartialExpressionAccessor _accessor = (PartialExpressionAccessor) _partialExpression;
							assertThat(_accessor.getAccessStack().size(), is(1));
							assertThat(_accessor.getAccessStack().get(0), is("name"));
						}

						_partialExpression = _argumentMapping.get(1);
						assertThat(_partialExpression.getClass().getName(), is(PartialExpressionAccessor.class.getName()));
						{
							PartialExpressionAccessor _accessor = (PartialExpressionAccessor) _partialExpression;
							assertThat(_accessor.getAccessStack().size(), is(1));
							assertThat(_accessor.getAccessStack().get(0), is("value"));
						}
					}
				}

				// on newSingleLineProperty - if - else
				_currentAlternative = _subSubIterator.next();
				{
					PartialExpressionLogical _test = _currentAlternative.getTest();
					assertThat(_test, is(nullValue()));

					List<Statement> _altStatements = _currentAlternative.getStatements();
					assertThat(_altStatements.isEmpty(), is(not(nullValue())));
					assertThat(_altStatements.size(), is(1));
					Iterator<Statement> _subSubSubIterator = _altStatements.iterator();

					// on newSingleLineProperty - if - else - call
					_current = _subSubSubIterator.next();
					assertThat(_current.getClass().getName(), is(StatementCall.class.getName()));
					{
						StatementCall _call = (StatementCall) _current;
						List<String> _methodAccessor = _call.getMethodAccessor();
						assertThat(_methodAccessor.size(), is(2));
						assertThat(_methodAccessor.get(0), is("callback3"));
						assertThat(_methodAccessor.get(1), is("process"));

						PartialExpression _partialExpression;
						List<PartialExpression> _argumentMapping = _call.getArgumentMapping();
						assertThat(_argumentMapping.size(), is(2));

						_partialExpression = _argumentMapping.get(0);
						assertThat(_partialExpression.getClass().getName(), is(PartialExpressionAccessor.class.getName()));
						{
							PartialExpressionAccessor _accessor = (PartialExpressionAccessor) _partialExpression;
							assertThat(_accessor.getAccessStack().size(), is(1));
							assertThat(_accessor.getAccessStack().get(0), is("name"));
						}

						_partialExpression = _argumentMapping.get(1);
						assertThat(_partialExpression.getClass().getName(), is(PartialExpressionAccessor.class.getName()));
						{
							PartialExpressionAccessor _accessor = (PartialExpressionAccessor) _partialExpression;
							assertThat(_accessor.getAccessStack().size(), is(1));
							assertThat(_accessor.getAccessStack().get(0), is("value"));
						}
					}
				}
			}
		}
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
