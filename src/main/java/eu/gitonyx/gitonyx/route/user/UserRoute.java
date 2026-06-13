package eu.gitonyx.gitonyx.route.user;

import org.json.JSONObject;

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

    private final ModelHandler modelHandler;

    public UserRoute(ModelHandler modelHandler) {
        this.modelHandler = modelHandler;
    }

    @GET("/info")
    public Response userInfo(Request request) {
        int id = request.json().getInt("id");

        UserModel user = this.modelHandler.loadById(UserModel.class, id);
        if (user == null) {
            return Response.notFound("User not found");
        }

        JSONObject jsonObject = this.modelHandler.parseObject(user);
        if (jsonObject == null) {
            return Response.notFound("User object not found");
        }

        return new Response().json(jsonObject).statusCode(200);
    }
    
    @PUT("/register")
    public Response userRegister(Request request) {


        return new Response().statusCode(200);
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
