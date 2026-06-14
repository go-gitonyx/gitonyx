package eu.gitonyx.gitonyx.route.user;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import de.tnttastisch.polydb.query.Repository;
import eu.gitonyx.gitonyx.annotation.http.Parent;
import eu.gitonyx.gitonyx.annotation.http.type.DELETE;
import eu.gitonyx.gitonyx.annotation.http.type.GET;
import eu.gitonyx.gitonyx.annotation.http.type.PATCH;
import eu.gitonyx.gitonyx.annotation.http.type.POST;
import eu.gitonyx.gitonyx.annotation.http.type.PUT;
import eu.gitonyx.gitonyx.http.Request;
import eu.gitonyx.gitonyx.http.Response;
import eu.gitonyx.gitonyx.model.ModelHandler;
import eu.gitonyx.gitonyx.model.UserModel;
import eu.gitonyx.gitonyx.session.SessionStore;

@Parent("/user")
public class UserRoute {

    private final Repository<UserModel> userRepository;
    private final SessionStore sessionStore;

    public UserRoute(Repository<UserModel> userRepository, SessionStore sessionStore) {
        this.userRepository = userRepository;
        this.sessionStore = sessionStore;
    }

    @GET("/info")
    public Response userInfo(Request request) {
        var id = request.json().get("id");

        Optional<UserModel> user = this.userRepository.findById(id);
        if (user == null || user.isEmpty()) {
            return Response.notFound("User not found");
        }

        return new Response().json(safeUserJson(user.get())).statusCode(200);
    }

    @PUT("/register")
    public Response userRegister(Request request) {
        JSONObject body = request.json();

        if (!body.has("username") || !body.has("email") || !body.has("password")
                || !body.has("firstName") || !body.has("lastName")) {
            return Response.badRequest("Missing required fields: username, email, password, firstName, lastName");
        }

        String username = body.getString("username");
        String email = body.getString("email");
        String password = body.getString("password");

        boolean taken = this.userRepository.findAll().stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username)
                        || u.getEmail().equalsIgnoreCase(email));
        if (taken) {
            return Response.conflict("Username or email already in use");
        }

        UserModel user = new UserModel();
        user.setId(UUID.randomUUID());
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(body.getString("firstName"));
        user.setLastName(body.getString("lastName"));
        user.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        this.userRepository.save(user);

        return new Response().json(safeUserJson(user)).statusCode(201);
    }

    @POST("/login")
    public Response userLogin(Request request) {
        JSONObject body = request.json();

        if (!body.has("login") || !body.has("password")) {
            return Response.badRequest("Missing required fields: login, password");
        }

        String login = body.getString("login");
        String password = body.getString("password");

        Optional<UserModel> match = this.userRepository.findAll().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(login)
                        || u.getEmail().equalsIgnoreCase(login))
                .findFirst();

        if (match.isEmpty()) {
            return Response.unauthorized("Invalid credentials");
        }

        UserModel user = match.get();
        if (!BCrypt.checkpw(password, user.getPasswordHash())) {
            return Response.unauthorized("Invalid credentials");
        }

        String token = sessionStore.create(user.getId());
        String sessionInfo = URLEncoder.encode(
                new JSONObject()
                        .put("username", user.getUsername())
                        .put("firstName", user.getFirstName())
                        .toString(),
                StandardCharsets.UTF_8);

        return new Response()
                .json(safeUserJson(user))
                .header("Set-Cookie", cookie("access_token", token))
                .header("Set-Cookie", cookie("session_info", sessionInfo))
                .statusCode(200);
    }

    @POST("/logout")
    public Response userLogout(Request request) {
        String token = cookieValue(request, "access_token");
        sessionStore.invalidate(token);

        return new Response()
                .header("Set-Cookie", expireCookie("access_token"))
                .header("Set-Cookie", expireCookie("session_info"))
                .statusCode(200);
    }

    @PATCH("/edit")
    public Response userEdit(Request request) {
        return new Response().statusCode(200);
    }

    @DELETE("/delete")
    public Response userDelete(Request request) {
        return new Response().statusCode(200);
    }

    private JSONObject safeUserJson(UserModel user) {
        JSONObject json = ModelHandler.parseObject(user);
        json.remove("passwordHash");
        return json;
    }

    private String cookie(String name, String value) {
        return name + "=" + value + "; Path=/; Domain=localhost; SameSite=Lax";
    }

    private String expireCookie(String name) {
        return name + "=; Path=/; Domain=localhost; SameSite=Lax; Max-Age=0";
    }

    private String cookieValue(Request request, String name) {
        String header = request.header("cookie");
        if (header == null) return null;
        for (String part : header.split(";")) {
            String[] kv = part.strip().split("=", 2);
            if (kv.length == 2 && kv[0].equals(name)) return kv[1];
        }
        return null;
    }
}
