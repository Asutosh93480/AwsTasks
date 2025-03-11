package com.task02;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.annotations.lambda.LambdaUrlConfig;
import com.syndicate.deployment.model.RetentionSetting;
import com.syndicate.deployment.model.lambda.url.AuthType;

import java.util.HashMap;
import java.util.Map;

@LambdaHandler(
		lambdaName = "hello_world",
		roleName = "hello_world-role",
		isPublishVersion = true,
		aliasName = "${lambdas_alias_name}",
		logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
)

@LambdaUrlConfig(authType = AuthType.NONE)

public class HelloWorld implements RequestHandler<Object, Map<String, Object>> {

	@Override
	public Map<String, Object> handleRequest(Object request, Context context) {
		System.out.println("Hello from lambda");

		// Cast the request to a Map to access the HTTP method and path
		Map<String, Object> event = (Map<String, Object>) request;
		String method = (String) event.get("httpMethod");  // Get the HTTP method (GET, POST, etc.)
		String path = (String) event.get("path");  // Get the requested path (e.g., /hello)

		// Initialize response maps
		Map<String, Object> response = new HashMap<>();
		Map<String, Object> body = new HashMap<>();
		Map<String, String> headers = new HashMap<>();

		// Check if the request method is GET and the path is /hello
		if ("GET".equalsIgnoreCase(method) && "/hello".equals(path)) {
			// Return a successful response for /hello GET
			body.put("statusCode", 200);
			body.put("message", "Hello from Lambda");

			response.put("statusCode", 200);
			response.put("body", body);
		} else {
			// Return a 400 error for unsupported methods or incorrect paths
			body.put("statusCode", 400);
			body.put("message", "Bad request syntax or unsupported method. Request path: " + path + ". HTTP method: " + method);

			response.put("statusCode", 400);
			response.put("body", body);
		}

		// Set response headers and encoding
		headers.put("content-type", "application/json");
		response.put("headers", headers);
		response.put("isBase64Encoded", false);

		return response;
	}
}
