package eu.gitonyx.gitonyx.route;

import eu.gitonyx.gitonyx.annotation.http.Parent;
import eu.gitonyx.gitonyx.annotation.http.type.*;
import eu.gitonyx.gitonyx.http.Request;
import eu.gitonyx.gitonyx.http.Response;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class OnyxRouter {

    private enum HttpMethod { GET, POST, PUT, PATCH, DELETE }
    private record RouteEntry(HttpMethod method, String path, Handler handler) {}

    private final String version;
    private final List<RouteEntry> pendingRoutes = new ArrayList<>();
    private Javalin app;

    public OnyxRouter(String version) {
        this.version = version;
    }

    public void registerRoutes(Object... routes) {
        for (Object route : routes) {
            collectRoutes(route);
        }
    }

    public void start(int port) {
        List<RouteEntry> routes = List.copyOf(pendingRoutes);

        this.app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors ->
                cors.addRule(rule -> {
                    rule.reflectClientOrigin = true;
                    rule.allowCredentials = true;
                })
            );
            config.routes.apiBuilder(() -> {
                for (RouteEntry entry : routes) {
                    switch (entry.method()) {
                        case GET    -> ApiBuilder.get(entry.path(), entry.handler());
                        case POST   -> ApiBuilder.post(entry.path(), entry.handler());
                        case PUT    -> ApiBuilder.put(entry.path(), entry.handler());
                        case PATCH  -> ApiBuilder.patch(entry.path(), entry.handler());
                        case DELETE -> ApiBuilder.delete(entry.path(), entry.handler());
                    }
                }
            });
        }).start(port);
    }

    private void collectRoutes(Object route) {
        Class<?> clazz = route.getClass();
        Parent parent = clazz.getAnnotation(Parent.class);
        String basePath = version + (parent != null ? parent.value() : "");

        for (Method method : clazz.getDeclaredMethods()) {
            GET get = method.getAnnotation(GET.class);
            if (get != null) {
                pendingRoutes.add(new RouteEntry(HttpMethod.GET, basePath + get.value(), ctx -> handle(route, method, ctx)));
            }

            POST post = method.getAnnotation(POST.class);
            if (post != null) {
                pendingRoutes.add(new RouteEntry(HttpMethod.POST, basePath + post.value(), ctx -> handle(route, method, ctx)));
            }

            PUT put = method.getAnnotation(PUT.class);
            if (put != null) {
                pendingRoutes.add(new RouteEntry(HttpMethod.PUT, basePath + put.value(), ctx -> handle(route, method, ctx)));
            }

            PATCH patch = method.getAnnotation(PATCH.class);
            if (patch != null) {
                pendingRoutes.add(new RouteEntry(HttpMethod.PATCH, basePath + patch.value(), ctx -> handle(route, method, ctx)));
            }

            DELETE delete = method.getAnnotation(DELETE.class);
            if (delete != null) {
                pendingRoutes.add(new RouteEntry(HttpMethod.DELETE, basePath + delete.value(), ctx -> handle(route, method, ctx)));
            }
        }
    }

    private void handle(Object route, Method method, Context ctx) throws Exception {
        Request request = new Request()
                .method(ctx.method().name())
                .path(ctx.path())
                .body(ctx.body());

        ctx.headerMap().forEach(request::header);
        ctx.queryParamMap().forEach((k, v) -> request.queryParam(k, v.getFirst()));
        ctx.pathParamMap().forEach(request::pathParam);

        Response response = (Response) method.invoke(route, request);

        for (String[] h : response.headers()) ctx.header(h[0], h[1]);
        ctx.status(response.code());
        ctx.contentType(response.contentType());
        if (response.body() != null) {
            ctx.result(response.body());
        }
    }

    public Javalin app() {
        return app;
    }
}
