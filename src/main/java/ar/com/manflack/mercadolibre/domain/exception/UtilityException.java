package ar.com.manflack.mercadolibre.domain.exception;

public class UtilityException extends GenericException
{
	private static final long serialVersionUID = -1334375722497229579L;

	public static final String NO_INTERSECTION = "U01";
	public static final String INVALID_RADII = "U02";
	public static final String MISSING_INFORMATION_TO_COMPUTE = "U03";
	public static final String OVERINFORMATION_TO_COMPUTE = "U03";

	public static final String NO_INTERSECTION_MSG = "No intersection between two circles.";
	public static final String INVALID_RADII_MSG = "Radius must be positive.";
	public static final String MISSING_INFORMATION_TO_COMPUTE_MSG = "Information missing, provide a valid data to compute.";
	public static final String OVERINFORMATION_TO_COMPUTE_MSG = "Too many Satellites provided. Compute the information to continue.";

	public UtilityException(String errorCode, String message)
	{
		super(errorCode, message);
	}
}
