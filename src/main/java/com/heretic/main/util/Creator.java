package com.heretic.main.util;

import com.heretic.main.controller.UserController;
import com.heretic.main.controller.UserControllerImpl;
import com.heretic.main.controller.role.AdminController;
import com.heretic.main.controller.role.ManagerController;
import com.heretic.main.controller.role.VisitorController;
import com.heretic.main.repository.movie.MovieRepository;
import com.heretic.main.repository.movie.MovieRepositoryImpl;
import com.heretic.main.repository.session.SessionRepository;
import com.heretic.main.repository.session.SessionRepositoryImpl;
import com.heretic.main.repository.ticket.TicketRepository;
import com.heretic.main.repository.ticket.TicketRepositoryImpl;
import com.heretic.main.repository.user.UserRepository;
import com.heretic.main.repository.user.UserRepositoryImpl;
import com.heretic.main.service.movie.MovieService;
import com.heretic.main.service.movie.MovieServiceImpl;
import com.heretic.main.service.session.SessionService;
import com.heretic.main.service.session.SessionServiceImpl;
import com.heretic.main.service.ticket.TicketService;
import com.heretic.main.service.ticket.TicketServiceImpl;
import com.heretic.main.service.user.UserService;
import com.heretic.main.service.user.UserServiceImpl;

public final class Creator {

    public static VisitorController createVisitorController() {
        return new VisitorController(
                createUserController(),
                createSessionService(),
                createTicketService(),
                createUserService()
                );
    }

    public static ManagerController createManagerController(){
        return new ManagerController(
                createUserController(),
                createSessionService(),
                createTicketService(),
                createUserService(),
                createMovieService()
        );
    }

    public static AdminController createAdminController(){
        return new AdminController(
                createUserController(),
                createSessionService(),
                createTicketService(),
                createUserService(),
                createMovieService()
        );
    }

    public static UserController createUserController() {
        return new UserControllerImpl(createUserService());
    }

    private static UserService createUserService() {
        return UserServiceImpl.builder()
                .userRepository(createUserRepository())
                .build();
    }

    private static SessionService createSessionService() {
        return SessionServiceImpl.builder()
                .sessionRepository(createSessionRepository())
                .build();
    }

    private static TicketService createTicketService() {
        return TicketServiceImpl.builder()
                .ticketRepository(createTicketRepository())
                .build();
    }

    private static MovieService createMovieService(){
        return MovieServiceImpl.builder()
                .movieRepository(createMovieRepository())
                .build();
    }

    private static SessionRepository createSessionRepository() {
        return SessionRepositoryImpl.builder()
                .movieRepository(createMovieRepository())
                .build();
    }

    private static TicketRepository createTicketRepository() {
        return TicketRepositoryImpl.builder()
                .userService(createUserService())
                .sessionRepository(createSessionRepository())
                .userRepository(createUserRepository())
                .build();
    }

    private static MovieRepository createMovieRepository() {
        return MovieRepositoryImpl.builder().build();
    }

    private static UserRepository createUserRepository() {
        return UserRepositoryImpl.builder().build();
    }

    private Creator() {
    }

}
