package ru.terra.dms.server.db.controller;

import ru.terra.dms.server.db.entity.User;
import ru.terra.server.db.controllers.AbstractDBController;
//import ru.ru.terraobjects.manager.ObjectsManager;

import java.util.List;

/**
 * Date: 30.05.14
 * Time: 13:18
 */
public class UsersDBController extends AbstractDBController<User> {
  //  private ObjectsManager<User> objectsManager;

    public UsersDBController() {
        super(User.class);
        //objectsManager = new ObjectsManager<>();
    }

    @Override
    public void create(User user) throws Exception {

    }

    @Override
    public void delete(Integer id) throws Exception {

    }

    @Override
    public void update(User user) throws Exception {

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
        return null;
    }
//
//    @Override
//    public void create(User user) throws Exception {
//        objectsManager.saveOrUpdate(user);
//    }
//
//    @Override
//    public void delete(Integer id) throws Exception {
//        objectsManager.remove(get(id));
//    }
//
//    @Override
//    public void update(User user) throws Exception {
//        objectsManager.saveOrUpdate(user);
//    }
//
//    @Override
//    public List<User> list(boolean all, int page, int perpage) throws Exception {
//        return null;
//    }
//
//    @Override
//    public int count() {
//        return 0;
//    }
//
//    @Override
//    public User get(Integer id) throws Exception {
//        return objectsManager.load(User.class, id);
//    }
}
