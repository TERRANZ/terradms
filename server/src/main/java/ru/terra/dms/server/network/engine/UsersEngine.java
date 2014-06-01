package ru.terra.dms.server.network.engine;

import ru.terra.dms.server.db.controller.UsersDBController;
import ru.terra.dms.server.db.entity.User;
import ru.terra.server.dto.UserDTO;
import ru.terra.server.engine.AbstractEngine;

/**
 * Date: 30.05.14
 * Time: 13:16
 */
public class UsersEngine extends AbstractEngine<User, UserDTO> {
    public UsersEngine() {
        super(new UsersDBController());
    }

    @Override
    public UserDTO getDto(Integer id) {
        return null;
    }

    @Override
    public void dtoToEntity(UserDTO dto, User user) {

    }

    @Override
    public UserDTO entityToDto(User user) {
        return null;
    }
}
