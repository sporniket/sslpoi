/**
 * 
 */
package com.sporniket.scripting.ssl.vess;

/**
 * Node build by the parsing of a Vess source.
 * 
 * <p>
 * A node can be linked to another one : a next node that is at the same level (list of statements, list of parameters, etc...).
 * </p>
 * 
 * <p>
 * A specific node subclass can contains one or more lists of nodes.
 * </p>
 * 
 * A node may be linked to another one, to get
 * 
 * @author dsporn
 *
 */
public abstract class VessNode
{
	/**
	 * Link to the next node, if any.
	 */
	private VessNode myNext = null;

	public VessNode getNext()
	{
		return myNext;
	}

	/**
	 * @return <code>true</code> if there is no next node.
	 */
	public boolean isLastNode()
	{
		return null == getNext();
	}

	public void setNext(VessNode next)
	{
		myNext = next;
	}

	/**
	 * Fluent version of {@link #setNext(VessNode)}.
	 * 
	 * @param next
	 *            the next node.
	 * @return this node.
	 */
	public VessNode withNext(VessNode next)
	{
		setNext(next);
		return this;
	}

}
