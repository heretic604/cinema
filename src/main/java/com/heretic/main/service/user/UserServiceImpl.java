package com.heretic.main.service.user;

import com.heretic.main.controller.UserController;
import com.heretic.main.controller.role.AdminController;
import com.heretic.main.controller.role.RoleController;
import com.heretic.main.exception.AccountBlockedException;
import com.heretic.main.exception.UserNotFoundException;
import com.heretic.main.model.ticket.Ticket;
import com.heretic.main.model.user.Role;
import com.heretic.main.model.user.Status;
import com.heretic.main.model.user.User;
import com.heretic.main.repository.user.UserRepository;
import com.heretic.main.util.Creator;
import com.heretic.main.util.Values;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.regex.Matcher;

@Slf4j
@Builder
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean create(User user) {
        return userRepository.create(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll(this);
    }

    @Override
    public User get(String username) {
        return userRepository.get(username, this);
    }

    @Override
    public User get(Long id) {
        return userRepository.get(id, this);
    }

    @Override
    public User update(User user, Long id) {
        return userRepository.update(user, id);
    }

    @Override
    public User delete(Long id) {
        return userRepository.delete(id, this);
    }

    @Override
    public User checkAccount(String name) throws UserNotFoundException {
        User user = userRepository.get(name, this);
        if (user == null) {
            throw new UserNotFoundException();
        } else {
            try {
                checkBlocking(user);
            } catch (AccountBlockedException e) {
                log.info(Values.LOG_LOGGING_FAILED, user.getRole().getTitle(), user.getUsername());
                System.out.println(e.getMessage());
                System.exit(Values.ZERO);
            }
            return user;
        }
    }

    @Override
    public void checkBlocking(User user) throws AccountBlockedException {
        if (user.getStatus() == Status.BLOCKED) {
            throw new AccountBlockedException();
        }
    }

    @Override
    public Status checkStatus(String status) {
        if (status.equals(Status.ACTIVE.getTitle())) {
            return Status.ACTIVE;
        } else return Status.BLOCKED;
    }

    @Override
    public Role checkRole(String role) {
        if (role.equals(Role.VISITOR.getTitle())) {
            return Role.VISITOR;
        } else if (role.equals(Role.MANAGER.getTitle())) {
            return Role.MANAGER;
        } else return Role.ADMIN;
    }

    @Override
    public boolean checkUsername(String name) {
        User user = userRepository.get(name, this);
        return user == null;
    }

    @Override
    public User verification(String act, UserController userController) {

        User user = null;

        try {
            user = checkAccount(act);
            userController.checkPassword(user);
            log.info(Values.LOG_LOGIN, user.getRole().getTitle(), user.getUsername());
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            log.info(e.getMessage());
        }

        return user;
    }

    @Override
    public RoleController defineRoleController(User user) {
        return switch (user.getRole()) {
            case VISITOR -> Creator.createVisitorController();
            case MANAGER -> Creator.createManagerController();
            case ADMIN -> Creator.createAdminController();
        };
    }

    @Override
    public boolean validateEmail(String email) {
        Matcher matcher = Values.VALID_EMAIL_REGEX.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean validateUsername(String username) {
        Matcher matcher = Values.VALID_USERNAME_REGEX.matcher(username);
        return matcher.matches();
    }

    @Override
    public boolean changeRole(User user, AdminController adminController) {

        try {
            if (user == null) {
                throw new UserNotFoundException();
            } else {
                user.setRole(adminController.chooseRole());
                userRepository.update(user, user.getId());
                return true;
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean changeStatus(User user, AdminController adminController) {

        try {
            if (user == null) {
                throw new UserNotFoundException();
            } else {
                user.setStatus(adminController.chooseStatus());
                userRepository.update(user, user.getId());
                return true;
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean removeUser(User user) {

        try {
            if (user == null) {
                throw new UserNotFoundException();
            } else {
                delete(user.getId());
                userRepository.delete(user.getId(), this);
                return true;
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean checkAge(Ticket ticket, User user) {

        int limit = ticket.getSession().getMovie().getAgeLimit();

        LocalDate today = LocalDate.now();
        LocalDate birthday = user.getBirthday();
        Period period = Period.between(birthday, today);

        int age = period.getYears();

        return age > limit;
    }

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String encryptPassword(String password, String username) {
        return Integer.toString(password.concat(username).hashCode());
    }
}
