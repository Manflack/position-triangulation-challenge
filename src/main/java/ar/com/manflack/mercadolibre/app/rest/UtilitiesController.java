package ar.com.manflack.mercadolibre.app.rest;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.manflack.mercadolibre.app.api.ApiError;
import ar.com.manflack.mercadolibre.app.api.SatelliteApi;
import ar.com.manflack.mercadolibre.domain.exception.UtilityException;
import ar.com.manflack.mercadolibre.domain.service.UtilitiesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.MISSING_INFORMATION_TO_COMPUTE;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.MISSING_INFORMATION_TO_COMPUTE_MSG;

@CrossOrigin(origins = "*", maxAge = 86400)
@RestController
@RequestMapping(path = "")
public class UtilitiesController
{
	@Autowired
	private UtilitiesService utilitiesService;

	@PostMapping(path = "/topsecret", produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(description = "Compute location. It's required a List of SatelliteApi data object with their fields provided.", operationId = "topsecret.computeFull")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Position computed.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = List.class)))),
			@ApiResponse(responseCode = "404", description = "Error in data", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "500", description = "Internal error.", content = @Content(schema = @Schema(implementation = ApiError.class))) })
	public ResponseEntity<?> computeFull(
			@Parameter(description = "List of satellites data.") @Valid @RequestBody(required = true) List<SatelliteApi> satellites)
			throws UtilityException
	{
		if (satellites.size() != 3)
			throw new UtilityException(MISSING_INFORMATION_TO_COMPUTE, MISSING_INFORMATION_TO_COMPUTE_MSG);

		return new ResponseEntity<>(utilitiesService.obtainIntersection(satellites), HttpStatus.OK);
	}

	@PostMapping(path = "/topsecret_split/{satellite_name}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "Receive information by part from the Nave. Add the Satellite to Data information for lately be computed after. "
			+ "Its required provide only three (3) Satellites, otherwhise a exception must be thrown. The data will persist until compute "
			+ "method doesn't was invoke.", operationId = "topsecret.computeByStep")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Nave received correctly."),
			@ApiResponse(responseCode = "200", description = "Data received.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ar.com.manflack.mercadolibre.app.api.ApiResponse.class)))),
			@ApiResponse(responseCode = "404", description = "Error in data.", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "500", description = "Internal error.", content = @Content(schema = @Schema(implementation = ApiError.class))) })
	public ResponseEntity<?> computeByStep(
			@Parameter(description = "Satellite name.", required = true) @PathVariable("satellite_name") final String satelliteName,
			@Parameter(description = "Satellite data.", required = true) @RequestBody(required = true) SatelliteApi satelliteApi)
			throws Exception
	{
		satelliteApi.setName(satelliteName);
		utilitiesService.setSatellite(satelliteApi);
		return new ResponseEntity<>(new ar.com.manflack.mercadolibre.app.api.ApiResponse("Nave received correctly"),
				HttpStatus.OK);
	}

	@GetMapping(path = "/topsecret_split/", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "Compute the Nave information by Satellites data provided before.", operationId = "topsecret.computePutByPart")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Distance computed."),
			@ApiResponse(responseCode = "200", description = "Data received.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ar.com.manflack.mercadolibre.app.api.ApiResponse.class)))),
			@ApiResponse(responseCode = "404", description = "Error in data.", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "500", description = "Internal error.", content = @Content(schema = @Schema(implementation = ApiError.class))) })
	public ResponseEntity<?> getComputeByStep() throws Exception
	{
		return new ResponseEntity<>(utilitiesService.obtainIntersectionByStep(), HttpStatus.OK);
	}
}
