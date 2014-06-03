package ru.terra.dms.server.db.controller;

import ru.terra.dms.server.db.entity.User;
import ru.terra.server.db.controllers.AbstractDBController;
import ru.terraobjects.manager.ObjectsManager;

import java.util.List;

//import ru.ru.terraobjects.manager.ObjectsManager;

/**
 * Date: 30.05.14
 * Time: 13:18
 */
public class UsersDBController extends AbstractDBController<User> {
    private ObjectsManager<User> objectsManager;

    public UsersDBController() {
        super(User.class);
        objectsManager = new ObjectsManager<>();
    }

    public User findByNamePass(String user, String pass) {
        List<User> users = objectsManager.load(User.class, "name", user);
        return (users != null && users.size() > 0 && users.get(0).getPass().equals(pass)) ? users.get(0) : null;
    }

    @Override
    public void create(User user) throws Exception {
        objectsManager.saveOrUpdate(user);
    }

    @Override
    public void delete(Integer id) throws Exception {
        objectsManager.remove(get(id));
    }

    @Override
    public void update(User user) throws Exception {
        objectsManager.saveOrUpdate(user);
    }

    @Override
    public List<User> list(boolean all, int page, int perpage) throws Exception {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public User get(Integer id) throws Exception {
        return objectsManager.load(User.class, id);
    }
}
