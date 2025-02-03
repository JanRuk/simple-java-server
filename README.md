# Simple Java Server

This project is a simple HTTP server implemented in Java. It was created as a learning exercise to understand the fundamentals of client-server architecture, HTTP protocols, and socket programming.

## Overview

The server listens on port 80 and handles `GET` requests from clients. It serves files from a predefined directory structure based on the requested resource path. The server also handles common HTTP errors such as `404 Not Found` and `405 Method Not Allowed`.

### Key Features
- **Basic HTTP Server**: Handles `GET` requests and serves static files.
- **Error Handling**: Returns appropriate HTTP status codes for errors like `404 Not Found` and `405 Method Not Allowed`.
- **Multi-threaded**: Each client connection is handled in a separate thread, allowing the server to handle multiple clients concurrently.
- **Dynamic Content-Type Detection**: Uses `Files.probeContentType` to determine the MIME type of the requested resource.

### How It Works
1. The server starts and listens on port 80.
2. When a client connects, the server reads the HTTP request line and headers.
3. The server processes the request:
   - If the request method is not `GET`, it responds with a `405 Method Not Allowed` error.
   - If the `Host` header is missing, it responds with a `400 Bad Request` error.
   - If the requested resource does not exist, it responds with a `404 Not Found` error.
   - If the resource exists, it sends the file content with the appropriate `Content-Type` header.
4. The server closes the connection after processing the request.

### Learning Goals
This project was created to achieve the following learning objectives:
- Understand the basics of client-server communication.
- Learn how to implement a simple HTTP server using Java sockets.
- Gain familiarity with HTTP protocols, headers, and status codes.
- Practice file handling and dynamic content-type detection in Java.

### Version
0.1.0

### License
Copyright &copy; 2025 Janinda Rukshan. All Rights Reserved.  
This project is licensed under the [MIT License](LICENSE.txt)


---

Thanks for checking it out!
