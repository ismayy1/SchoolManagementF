package com.techproed.repository.user;

import com.techproed.entity.concretes.user.User;
import com.techproed.entity.enums.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u WHERE u.userRole.roleName = :userRole")
    Page<User> findUserByUserRoleQuery(String userRole, Pageable pageable);

    User findByUsername(String username);

    @Query("SELECT (COUNT (u) > 0) FROM User u WHERE u.userRole.roleType = 'STUDENT'")
    boolean findStudent();

    @Query("SELECT max (u.studentNumber) FROM User u")
    int getMaxStudentNumber();

    List<User> findByAdvisorTeacherId(Long advisorTeacherId);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.advisorTeacherId = NULL WHERE u.advisorTeacherId = :teacherId")
    void removeAdvisorFromStudents(@Param("teacherId") Long teacherId);

    @Query("SELECT u FROM User u WHERE u.id IN :userIdList")
    List<User> findByUserIdList(List<Long> userIdList);

    Page<User> findAllByUserRole(RoleType roleType, Pageable pageable);
}
