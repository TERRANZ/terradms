package ru.terra.dms.server.network.controller;

import com.sun.jersey.api.core.HttpContext;
import ru.terra.dms.server.constants.URLConstants;
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
@Path(URLConstants.Login.USERS)
public class UsersController extends AbstractController<User, UserDTO, UsersEngine> {
    public UsersController() {
        super(UsersEngine.class, false, User.class, UserDTO.class);
    }

    @GET
    @Path(URLConstants.Login.DO_LOGIN)
    public LoginDTO login(@Context HttpContext hc, @QueryParam(URLConstants.Login.PARAM_USER) String user, @QueryParam(URLConstants.Login.PARAM_PASS) String pass) {
        User u = engine.getUser(user, pass);
        if (u != null) {
            return new LoginDTO("", true, sessionsHolder.registerUserSession(u));
        }
        return new LoginDTO();
    }

    @GET
    @Path(URLConstants.Login.DO_REG)
    public LoginDTO reg(@Context HttpContext hc, @QueryParam(URLConstants.Login.PARAM_USER) String user, @QueryParam(URLConstants.Login.PARAM_PASS) String pass) {
        User u = engine.getUser(user, pass);
        if (u != null)
            return new LoginDTO("already exists", false, "");
        LoginDTO ret = new LoginDTO();
        User newUser = new User();
        newUser.setLevel(0);
        newUser.setName(user);
        newUser.setPass(pass);
        newUser.setId(0);
        engine.createBean(newUser);
        Integer retId = newUser.getId();
        ret.logged = true;
        ret.id = retId;
        return new LoginDTO();
    }
}
