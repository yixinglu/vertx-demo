Vert.X Test
-----------

## OpenAPI 

- Add global handler
- RoutingContext/OperationRequest headers
- OpenApi `servers.url.basePath` and `Router.mountSubRouter`
- `mountServicesFromExtensions` and `x-vertx-event-bus` (address/method)
- `allOf`, Object and parameters `$refs` schema
- Mount different sub routers to same mount point

## Bug

Vertx Open API router could not work fine for following api definitions:

- `/api/{name}` name is a string path parameter
- `/api/hello`

## References

- https://github.com/vietj/scalable-open-api-with-vertx
- https://vertx.io/docs/vertx-web-api-service/java/
- https://vertx.io/docs/vertx-web-api-contract/java/
- https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.0.md
