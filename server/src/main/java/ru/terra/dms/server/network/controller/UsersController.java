package ru.terra.dms.server.network.controller;

import com.sun.jersey.api.core.HttpContext;
import ru.terra.dms.server.db.entity.User;
import ru.terra.dms.server.network.engine.UsersEngine;
import ru.terra.server.controller.AbstractController;
import ru.terra.server.dto.LoginDTO;
import ru.terra.server.dto.UserDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

/**
 * Date: 30.05.14
 * Time: 13:32
 */
@Path("/users")
public class UsersController extends AbstractController<User, UserDTO, UsersEngine> {
    public UsersController() {
        super(UsersEngine.class, false, User.class, UserDTO.class);
    }

    @GET
    @Path("/do.login.json")
    public LoginDTO login(@Context HttpContext hc, @QueryParam("user") String user, @QueryParam("pass") String pass) {
        User u = engine.getUser(user, pass);
        if (u != null) {
            return new LoginDTO("",true,sessionsHolder.registerUserSession(u));
        }
        return new LoginDTO();
    }
}
