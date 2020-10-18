package ar.com.manflack.mercadolibre.app.rest;

import java.util.List;

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

@CrossOrigin(origins = "*", maxAge = 86400)
@RestController
@RequestMapping(path = "/api")
public class UtilitiesController
{
	@Autowired
	private UtilitiesService utilitiesService;

	@PostMapping(path = "/topsecret", produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(description = "Compute location.", operationId = "topsecret.computeByBody")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Position computed.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = List.class)))),
			@ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "500", description = "Internal error.", content = @Content(schema = @Schema(implementation = ApiError.class))) })
	public ResponseEntity<?> getAll(
			@Parameter(description = "Fee affected transaction") @RequestBody(required = true) List<SatelliteApi> satellites)
			throws UtilityException
	{
		return new ResponseEntity<>(utilitiesService.obtenerCoordenadas(satellites), HttpStatus.OK);
	}
	
	
/*
	@Operation(description = "Create new fee.", operationId = "fee.post")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Fee created.", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "400", description = "Error in data.", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "401", description = "Access Denied.", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "403", description = "Error creating fee.", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "500", description = "Internal error.", content = @Content(schema = @Schema(implementation = ApiError.class))) })
	@PostMapping(path = "/", produces = {
			MediaType.APPLICATION_JSON_VALUE }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(
			@Parameter(description = "Fee to create.", required = true) @RequestBody FeeApi feeApi) throws Exception
	{

	}

	@Operation(description = "Delete a Fee.", operationId = "fee.delete")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Fee deleted."),
			@ApiResponse(responseCode = "400", description = "Error in data.", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "401", description = "Access Denied.", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "403", description = "Error deleting data.", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "404", description = "Fee not found.", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "500", description = "Internal error.", content = @Content(schema = @Schema(implementation = ApiError.class))) })
	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delete(
			@Parameter(description = "Fee id to delete.", required = true) @PathVariable("id") final Long id)
			throws Exception
	{

	}

	@Operation(description = "Update a Fee", operationId = "fee.put")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Fee updated.", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "400", description = "Error in data.", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "401", description = "Access Denied.", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "403", description = "Error updating data.", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "404", description = "Fee not found.", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "500", description = "Internal error.", content = @Content(schema = @Schema(implementation = ApiError.class))) })
	@PutMapping(path = "/{id}", produces = {
			MediaType.APPLICATION_JSON_VALUE }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(
			@Parameter(description = "Fee id to update.", required = true) @PathVariable("id") final Long id,
			@Parameter(description = "Fee data.", required = true) @RequestBody FeeApi feeApi) throws Exception
	{

	}

	@Operation(description = "Gets a Fee by Id.", operationId = "fee.getById")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Fee info.", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "400", description = "Error in data.", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "401", description = "Access Denied.", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "403", description = "Error deleting data.", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "404", description = "Fee not found.", content = @Content(schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "500", description = "Internal error.", content = @Content(schema = @Schema(implementation = ApiError.class))) })
	@GetMapping(path = "/byId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> get(
			@Parameter(description = "Fee id to get.", required = true) @PathVariable("id") final Long id)
			throws Exception
	{

	}*/
}
