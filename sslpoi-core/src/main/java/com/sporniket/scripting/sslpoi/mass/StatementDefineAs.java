/**
 * 
 */
package com.sporniket.scripting.sslpoi.mass;

import com.sporniket.scripting.sslpoi.core.InitialisationMode;

/**
 * Statement <code>define IDENTIFIER as (new|null|undefined) CLASS_NAME['[]'] </code>.
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
public class StatementDefineAs extends Statement
{
	private final PartialIdentifier myIdentifier;

	private final InitialisationMode myInitialisationMode;

	public StatementDefineAs(String identifier, InitialisationMode initialisationMode, String className, boolean isArray)
	{
		myIdentifier = new PartialIdentifier(identifier, className, isArray);
		myInitialisationMode = initialisationMode;
	}

	public PartialIdentifier getIdentifier()
	{
		return myIdentifier;
	}

	public InitialisationMode getInitialisationMode()
	{
		return myInitialisationMode;
	}
}
