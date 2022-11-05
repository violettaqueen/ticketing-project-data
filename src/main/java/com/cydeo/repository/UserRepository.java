package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String username);

    @Transactional
    void deleteByUserName(String username);

    //go to DB, bring me users with certain roles

    List<User> findByRoleDescriptionIgnoreCase(String description);


}
