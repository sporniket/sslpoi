/**
 * 
 */
package com.sporniket.scripting.sslpoi.vess;

/**
 * Store the identifiers mapping ('on ...', functions definition, ...)
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
public class VessNodeIdentifierMapping extends VessNode
{
	/**
	 * <code>true</code> if this is an array.
	 */
	private boolean myArray;

	/**
	 * Type.
	 */
	private String myClassName;

	/**
	 * Mapped Identifier.
	 */
	private String myIdentifier;

	public String getClassName()
	{
		return myClassName;
	}

	public String getIdentifier()
	{
		return myIdentifier;
	}

	public boolean isArray()
	{
		return myArray;
	}

	public void setArray(boolean array)
	{
		myArray = array;
	}

	public void setClassName(String className)
	{
		myClassName = className;
	}

	public void setIdentifier(String identifier)
	{
		myIdentifier = identifier;
	}

	public VessNodeIdentifierMapping withArray(boolean array)
	{
		setArray(array);
		return this;
	}

	public VessNodeIdentifierMapping withClassName(String className)
	{
		setClassName(className);
		return this;
	}

	public VessNodeIdentifierMapping withIdentifier(String identifier)
	{
		setIdentifier(identifier);
		return this;
	}

}
