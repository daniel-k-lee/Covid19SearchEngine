package org.covid19;

/**
 * Purpose: When the data isn't available for any reason, throw this exception.
 * Main Contributors:
 * 
 * @author Daniel Lee
 *
 */
public class DataException extends Exception
{

	public DataException(String message)
	{
		super(message);
	}
	public DataException(String message, Throwable cause) {
		super(message, cause);
	}
}
