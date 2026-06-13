package eu.gitonyx.gitonyx.route.user;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;

import de.tnttastisch.polydb.query.Repository;
import eu.gitonyx.gitonyx.annotation.http.Parent;
import eu.gitonyx.gitonyx.annotation.http.type.GET;
import eu.gitonyx.gitonyx.annotation.http.type.DELETE;
import eu.gitonyx.gitonyx.annotation.http.type.PATCH;
import eu.gitonyx.gitonyx.annotation.http.type.POST;
import eu.gitonyx.gitonyx.annotation.http.type.PUT;
import eu.gitonyx.gitonyx.http.Request;
import eu.gitonyx.gitonyx.http.Response;
import eu.gitonyx.gitonyx.model.ModelHandler;
import eu.gitonyx.gitonyx.model.UserModel;

@Parent("/user")
public class UserRoute {

    private final Repository<UserModel> userRepository;

    public UserRoute(Repository<UserModel> userRepository) {
        this.userRepository = userRepository;
    }

    @GET("/info")
    public Response userInfo(Request request) {
        var id = request.json().get("id");

        Optional<UserModel> user = this.userRepository.findById(id);
        if (user == null || user.isEmpty()) {
            return Response.notFound("User not found");
        }

        JSONObject jsonObject = ModelHandler.parseObject(user.get());
        if (jsonObject == null) {
            return Response.notFound("User object not found");
        }

        return new Response().json(jsonObject).statusCode(200);
    }
    
    @PUT("/register")
    public Response userRegister(Request request) {
        var id = UUID.randomUUID();
        var username = request.json().getString("username");
        var email = request.json().getString("email");
        var firstName = request.json().getString("firstName");
        var lastName = request.json().getString("lastName");
        var createdAt = Timestamp.valueOf(LocalDateTime.now());

        UserModel user = new UserModel();

        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCreatedAt(createdAt);

        this.userRepository.save(user);

        JSONObject jsonObject = ModelHandler.parseObject(user);
        if (jsonObject == null) {
            return Response.notFound("User object not found");
        }

        return new Response().json(jsonObject).statusCode(201);
    }
    
    @POST("/login")
    public Response userLogin(Request request) {


        return new Response().statusCode(200);
    }
    
    @PATCH("/edit")
    public Response userEdit(Request request) {


        return new Response().statusCode(200);
    }
    
    @DELETE("/delete")
    public Response userDelete(Request request) {


        return new Response().statusCode(200);
    }
}
