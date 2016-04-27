/**
 * 
 */
package com.sporniket.scripting.sslpoi.core;

/**Exception for features that are not supported yet.
 * @author david
 *
 */
public class NotSupportedException extends SslpoiException
{
	private static final long serialVersionUID = -2145526346037308455L;

	/**
	 * 
	 */
	public NotSupportedException()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public NotSupportedException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public NotSupportedException(Throwable cause)
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NotSupportedException(String message, Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NotSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
