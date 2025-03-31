# HTTP Status Codes Reference

## 1xx: Informational
These status codes indicate that the request has been received and the process is continuing.

- **100 Continue** – The server has received the request headers, and the client can proceed to send the request body.
- **101 Switching Protocols** – The requester has asked the server to switch protocols, and the server agrees.
- **102 Processing** – The server has received and is processing the request, but no response is available yet.
- **103 Early Hints** – Used to return preliminary HTTP headers before the final response.

## 2xx: Success
These status codes indicate that the request was successfully received, understood, and accepted.

- **200 OK** – The request was successful.
- **201 Created** – The request was successful, and a new resource was created.
- **202 Accepted** – The request has been accepted but is still being processed.
- **203 Non-Authoritative Information** – The server is returning metadata that may not be from the original server.
- **204 No Content** – The server successfully processed the request, but there is no content to return.
- **205 Reset Content** – The server successfully processed the request and asks the client to reset its view.
- **206 Partial Content** – The server is delivering only part of the resource due to a range header sent by the client.

## 3xx: Redirection
These status codes indicate that further action needs to be taken to complete the request.

- **300 Multiple Choices** – The request has multiple possible responses.
- **301 Moved Permanently** – The requested resource has been permanently moved to a new URL.
- **302 Found** – The requested resource is temporarily located at a different URL.
- **303 See Other** – The response can be found at a different URL using a GET request.
- **304 Not Modified** – The resource has not changed since the last request.
- **307 Temporary Redirect** – The resource is temporarily available at a different URL but must be requested using the same method.
- **308 Permanent Redirect** – The resource is permanently available at a different URL using the same request method.

## 4xx: Client Errors
These status codes indicate an error caused by the client.

- **400 Bad Request** – The server cannot process the request due to client-side errors.
- **401 Unauthorized** – Authentication is required and has failed or has not been provided.
- **402 Payment Required** – Reserved for future use (e.g., digital payment gateways).
- **403 Forbidden** – The server understood the request but refuses to authorize it.
- **404 Not Found** – The requested resource was not found.
- **405 Method Not Allowed** – The HTTP method used is not allowed for the requested resource.
- **406 Not Acceptable** – The server cannot produce a response matching the client’s request.
- **407 Proxy Authentication Required** – Authentication with a proxy is required.
- **408 Request Timeout** – The server timed out waiting for the request.
- **409 Conflict** – The request conflicts with the current state of the resource.
- **410 Gone** – The requested resource is no longer available and will not return.
- **411 Length Required** – The server requires a valid `Content-Length` header.
- **412 Precondition Failed** – A precondition in the request headers was not met.
- **413 Payload Too Large** – The request entity is too large to be processed.
- **414 URI Too Long** – The requested URL is too long for the server to handle.
- **415 Unsupported Media Type** – The media format is not supported by the server.
- **416 Range Not Satisfiable** – The requested range is not available.
- **417 Expectation Failed** – The server cannot meet the `Expect` header requirements.
- **418 I'm a Teapot** – A joke response from the "Hyper Text Coffee Pot Control Protocol".
- **422 Unprocessable Entity** – The request is well-formed but cannot be processed due to semantic errors.
- **425 Too Early** – The server is unwilling to process a request that might be replayed.
- **426 Upgrade Required** – The client must switch to a different protocol.
- **428 Precondition Required** – The server requires the request to be conditional.
- **429 Too Many Requests** – The user has sent too many requests in a short period.
- **431 Request Header Fields Too Large** – The request headers are too large.
- **451 Unavailable For Legal Reasons** – The resource is blocked due to legal reasons.

## 5xx: Server Errors
These status codes indicate that the server failed to fulfill a valid request.

- **500 Internal Server Error** – A generic server error message.
- **501 Not Implemented** – The server does not support the functionality required to fulfill the request.
- **502 Bad Gateway** – The server received an invalid response from an upstream server.
- **503 Service Unavailable** – The server is currently unavailable due to overload or maintenance.
- **504 Gateway Timeout** – The server did not receive a timely response from an upstream server.
- **505 HTTP Version Not Supported** – The HTTP version used in the request is not supported.
- **506 Variant Also Negotiates** – There is a misconfiguration in the server's content negotiation.
- **507 Insufficient Storage** – The server is out of storage space.
- **508 Loop Detected** – The server detected an infinite loop in processing.
- **510 Not Extended** – Further extensions to the request are required for it to be processed.
- **511 Network Authentication Required** – The client needs to authenticate to gain network access.

## Conclusion
This README provides a quick reference to HTTP status codes, helping developers understand and debug HTTP responses effectively.
