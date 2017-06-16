package com.att.pricerd.service.restlet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.camel.Exchange;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "/service")
@Path("/service")
public interface RestletService {

	@GET

	@Path("/hello")
	@ApiOperation(value = "Respond Hello World!", notes = "Returns a JSON object with a string to say hello world . ", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Service not available"),
			@ApiResponse(code = 500, message = "Unexpected Runtime error") })
	public void speak(Exchange e);

}
