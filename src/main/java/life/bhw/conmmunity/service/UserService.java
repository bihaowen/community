package life.bhw.conmmunity.service;

import life.bhw.conmmunity.dao.UserDao;
import life.bhw.conmmunity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public int insertUser(User user) {
        return userDao.insertUser(user);
    }
}
