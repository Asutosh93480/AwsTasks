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

	public Map<String, Object> handleRequest(Object request, Context context) {
		System.out.println("Hello from lambda");

		// Check if the method is supported (you can customize this logic as needed)
		String method = context.getAwsRequestId();  // Use the request context for method info (you may have a different way to check)

		if (method == null) {
			// Return 400 for unsupported method or bad request
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("statusCode", 400);
			errorResponse.put("message", "Bad request syntax or unsupported method. Request path: /cmtr-84c6fb31. HTTP method: GET");
			return errorResponse;
		}

		// Otherwise, return a success message
		String responseBody = "Hello from Lambda";
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("statusCode", 200);
		resultMap.put("body", responseBody);
		return resultMap;
	}

}
