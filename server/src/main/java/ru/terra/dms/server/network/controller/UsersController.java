package ru.terra.dms.server.network.controller;

import com.sun.jersey.api.core.HttpContext;
import ru.terra.dms.server.db.entity.User;
import ru.terra.dms.server.network.engine.UsersEngine;
import ru.terra.server.controller.AbstractController;
import ru.terra.server.dto.LoginDTO;
import ru.terra.server.dto.UserDTO;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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

    @POST
    @Path("/login")
    public LoginDTO login(@Context HttpContext hc, @FormParam("user") String user, @FormParam("pass") String pass) {
        return new LoginDTO();
    }
}
