package ar.com.manflack.mercadolibre.app.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ar.com.manflack.mercadolibre.app.api.ApiResponse;
import ar.com.manflack.mercadolibre.app.api.SatelliteApi;
import ar.com.manflack.mercadolibre.app.api.SatelliteApiFixture;
import ar.com.manflack.mercadolibre.app.handler.RestExceptionHandler;
import ar.com.manflack.mercadolibre.domain.model.Point;
import ar.com.manflack.mercadolibre.domain.service.UtilitiesService;

@RunWith(MockitoJUnitRunner.Silent.class)
@EnableWebMvc
public class UtilitiesControllerTest
{
	@InjectMocks
	private UtilitiesController utilitiesController;

	@Mock
	private UtilitiesService utilitiesService;

	private MockMvc mockMvc;

	private ObjectMapper mapper;

	@Before
	public void setup()
	{
		mockMvc = MockMvcBuilders.standaloneSetup(utilitiesController)
				.setControllerAdvice(new RestExceptionHandler())
				.build();

		mapper = new ObjectMapper().findAndRegisterModules()
				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	@Test
	public void getLocationTest() throws Exception
	{
		ApiResponse apiResponse = new ApiResponse("este es un mensaje secreto", new Point(1, 1));
		List<SatelliteApi> satellites = new ArrayList<>();
		satellites.add(SatelliteApiFixture.withDefaults());
		satellites.add(SatelliteApiFixture.withDefaults());
		satellites.add(SatelliteApiFixture.withDefaults());

		when(utilitiesService.getLocation(any())).thenReturn(apiResponse);

		MvcResult result = mockMvc.perform(post("/topsecret")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(satellites)))
				.andExpect(status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		assertNotNull(response);

		ApiResponse finalResponse = mapper.readValue(response, ApiResponse.class);
		assertNotNull(finalResponse);
	}

	@Test
	public void setSatelliteTest() throws Exception
	{
		SatelliteApi satellite = SatelliteApiFixture.withDefaults();

		MvcResult result = mockMvc.perform(post("/topsecret_split/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(satellite)))
				.andExpect(status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		assertNotNull(response);

		ApiResponse finalResponse = mapper.readValue(response, ApiResponse.class);
		assertNotNull(finalResponse);
	}

	@Test
	public void obtainIntersectionByStepTest() throws Exception
	{
		ApiResponse apiResponse = new ApiResponse("este es un mensaje secreto", new Point(1, 1));

		when(utilitiesService.obtainIntersectionByStep()).thenReturn(apiResponse);

		MvcResult result = mockMvc.perform(get("/topsecret_split")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		assertNotNull(response);

		ApiResponse finalResponse = mapper.readValue(response, ApiResponse.class);
		assertNotNull(finalResponse);
	}

	public String asJsonString(final Object obj)
	{
		try
		{
			return mapper.writeValueAsString(obj);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
