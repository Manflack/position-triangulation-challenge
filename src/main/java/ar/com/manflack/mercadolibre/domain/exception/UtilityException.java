package ar.com.manflack.mercadolibre.domain.exception;

public class UtilityException extends GenericException
{
	private static final long serialVersionUID = -1334375722497229579L;

	public static final String NO_INTERSECTION = "U01";

	public static final String NO_INTERSECTION_MSG = "No intersection between two circles";
	
	public UtilityException(String errorCode, String message)
	{
		super(errorCode, message);
	}
}
