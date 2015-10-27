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
	private final List<Statement> myTree = new ArrayList<Statement>();
	
	private Statement myLastStatement = null ;
	
	public void add(Statement statement)
	{
		setLastStatement(statement);
		getTree().add(statement);
	}

	private Statement getLastStatement()
	{
		return myLastStatement;
	}

	private void setLastStatement(Statement lastStatement)
	{
		myLastStatement = lastStatement;
	}

	private List<Statement> getTree()
	{
		return myTree;
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
	
	
}
