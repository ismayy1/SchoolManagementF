package com.techproed.repository.user;

import com.techproed.entity.concretes.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u WHERE u.userRole.roleName = :userRole")
    Page<User> findUserByUserRoleQuery(String userRole, Pageable pageable);

    User findByUsername(String username);
}
