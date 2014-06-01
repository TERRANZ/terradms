package ru.terra.dms.server.network.controller;

import ru.terra.dms.server.db.entity.User;
import ru.terra.dms.server.network.engine.UsersEngine;
import ru.terra.server.controller.AbstractController;
import ru.terra.server.dto.UserDTO;

import javax.ws.rs.Path;

/**
 * Date: 30.05.14
 * Time: 13:32
 */
@Path("/users")
public class UsersController extends AbstractController<User, UserDTO, UsersEngine> {
    public UsersController() {
        super(UsersEngine.class, false, User.class, UserDTO.class);
    }
}
