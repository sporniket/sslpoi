/**
 * 
 */
package com.sporniket.scripting.sslpoi.vess;

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
public abstract class VessNode
{
	/**
	 * Link to the next node, if any.
	 */
	private VessNode myNext = null;

	/**
	 * Put the given node to the end of the chaining.
	 * 
	 * @param lastNode
	 *            the node to put at the end of the chain.
	 */
	public void enqueue(VessNode lastNode)
	{
		if (isLastNode())
		{
			setNext(lastNode);
		}
		else
		{
			getNext().enqueue(lastNode);
		}
	}

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
	 * Fluent version of {@link #enqueue(VessNode)}.
	 * 
	 * @param lastNode
	 *            the node to put at the end of the chain.
	 * @return this node.
	 */
	public VessNode withLastNode(VessNode lastNode)
	{
		enqueue(lastNode);
		return this;
	}
	//
	// /**
	// * Fluent version of {@link #setNext(VessNode)}.
	// *
	// * @param next
	// * the next node.
	// * @return this node.
	// */
	// public VessNode withNext(VessNode next)
	// {
	// setNext(next);
	// return this;
	// }
	//
}
