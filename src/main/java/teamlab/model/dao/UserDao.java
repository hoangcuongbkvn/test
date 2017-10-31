package teamlab.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import teamlab.model.entity.User;

public interface UserDao extends JpaRepository<User, Integer> {

    @Query("select s from User s where s.id in ?1")
    public List<User> findUserById(List<Integer> list);
}
