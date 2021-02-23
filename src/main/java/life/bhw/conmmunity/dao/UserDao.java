package life.bhw.conmmunity.dao;

import life.bhw.conmmunity.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserDao {

    int insertUser(User user);
}
