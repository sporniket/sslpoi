/**
 * 
 */
package com.sporniket.scripting.ssl.mass;

import java.util.ArrayList;
import java.util.List;

/**
 * This class deals with building the abstract syntax tree of statements.
 * 
 * At any time, the AST might be obtained using {@link #getResultTree()}.
 * 
 * @author dsporn
 *
 */
public class TreeBuilder
{
	private Statement myLastStatement = null;

	private final List<Statement> myTree = new ArrayList<Statement>();

	public void add(Statement statement)
	{
		setLastStatement(statement);
		getTree().add(statement);
	}

	public void clean()
	{
		setLastStatement(null);
		getTree().clear();
	}

	public List<Statement> getResultTree()
	{
		return new ArrayList<Statement>(getTree());
	}

	private Statement getLastStatement()
	{
		return myLastStatement;
	}

	private List<Statement> getTree()
	{
		return myTree;
	}

	private void setLastStatement(Statement lastStatement)
	{
		myLastStatement = lastStatement;
	}

}
