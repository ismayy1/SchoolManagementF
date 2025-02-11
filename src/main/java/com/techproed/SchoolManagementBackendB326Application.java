package com.techproed;

import com.techproed.entity.concretes.user.UserRole;
import com.techproed.entity.enums.RoleType;
import com.techproed.repository.user.UserRoleRepository;
import com.techproed.service.user.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolManagementBackendB326Application implements CommandLineRunner {

  private final UserRoleService userRoleService;
  private final UserRoleRepository userRoleRepository;

    public SchoolManagementBackendB326Application(UserRoleService userRoleService, UserRoleRepository userRoleRepository) {
        this.userRoleService = userRoleService;
        this.userRoleRepository = userRoleRepository;
    }

    public static void main(String[] args) {
    SpringApplication.run(SchoolManagementBackendB326Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

      if (userRoleService.getAllUserRoles().isEmpty()) {
//        Admin
        UserRole admin = new UserRole();
        admin.setRoleType(RoleType.ADMIN);
        admin.setRoleName(RoleType.ADMIN.getName());
        userRoleRepository.save(admin);

//        Dean
        UserRole dean = new UserRole();
        dean.setRoleType(RoleType.MANAGER);
        dean.setRoleName(RoleType.MANAGER.getName());
        userRoleRepository.save(dean);

        //        Vice-Dean
        UserRole viceDean = new UserRole();
        viceDean.setRoleType(RoleType.ASSISTANT_MANAGER);
        viceDean.setRoleName(RoleType.ASSISTANT_MANAGER.getName());
        userRoleRepository.save(viceDean);

        //        Student
        UserRole student = new UserRole();
        student.setRoleType(RoleType.STUDENT);
        student.setRoleName(RoleType.STUDENT.getName());
        userRoleRepository.save(student);

        //        Teacher
        UserRole teacher = new UserRole();
        teacher.setRoleType(RoleType.TEACHER);
        teacher.setRoleName(RoleType.TEACHER.getName());
        userRoleRepository.save(teacher);
      }
  }
}
