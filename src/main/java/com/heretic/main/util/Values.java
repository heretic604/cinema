package com.heretic.main.util;

import java.util.regex.Pattern;

public final class Values {

    public static final String ZERO_STR = "0";
    public static final String ONE_STR = "1";
    public static final String TWO_STR = "2";
    public static final String THREE_STR = "3";
    public static final String FOUR_STR = "4";
    public static final String FIVE_STR = "5";

    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TEN = 10;

    public static final int MORNING_HOUR = 10;
    public static final int AFTERNOON_HOUR = 14;
    public static final int EVENING_HOUR = 18;
    public static final int COUNT_TICKETS = 50;
    public static final int PASSWORD_LENGTH = 4;
    public static final int CHARS_TO_LINE = 40;
    public static final int MILLIS_FOR_SLEEP = 100;

    public static final char SPACE = ' ';

    public static final String DRIVER = "com.mysql.jdbc.Driver";

    public static final String USER_NOT_FOUND_MESSAGE = "User not found. Please, try again";
    public static final String ACCOUNT_BLOCKED_MESSAGE = """
            Your account is blocked
            Please contact administration Groovy Movie:
            heretic604@gmail.com
            """;
    public static final String INCORRECT_PASSWORD_MESSAGE = "Incorrect password. Please, try again";
    public static final String INCORRECT_EMAIL_MESSAGE = "Incorrect email. Start registration from beginning";
    public static final String INCORRECT_USERNAME_MESSAGE = "Incorrect username. Please choose another username.";
    public static final String USERNAME_NOT_AVAILABLE_MESSAGE = "This username not available. Please choose another username.";
    public static final String AGE_LIMIT_MESSAGE = "This movie is not suitable for you due to age restrictions";
    public static final String MOVIE_EXIST = "This movie already exist in library";
    public static final String NO_MOVIE_MESSAGE = "Movie not found";
    public static final String TIME_NOT_AVAILABLE = "This time not available";
    public static final String SHORT_PASSWORD = "This password is too short. Please, try again";
    public static final String TICKET_NOT_FOUND = "Ticket not found";
    public static final String INCORRECT_INPUT = "Incorrect input";

    public static final String ACC_INFO = """
            ID: %s
            Username: %s
            Email: %s
            Birthday: %s
            """;
    public static final String MAIN_MENU_HEAD = """
            Groovy Movie glads to see you!
            Today is: %s (%s)
            You logged as: %s (%s)
            """;
    public static final String TICKET_MENU_HEAD = "List of your GM tickets:";
    public static final String NO_TICKETS = "You haven't got any tickets";
    public static final String TICKET_RETURNED = "Ticket successful returned";
    public static final String ENTER_BIRTHDAY = "Please, enter your birthday: yyyy ↵ mm ↵ dd ↵";
    public static final String ACCESS_DENIED = "Password accepted";
    public static final String ENTER_NAME = """
            Please, enter your username.
            The number of characters must be between 4 to 20.
            Username consists of alphanumeric characters (a-zA-Z0-9), lowercase, or uppercase.
            ._- must not be the first or last character.
            ._- does not appear consecutively.
            """;
    public static final String ENTER_PASSWORD = "Please, enter your password:";
    public static final String ENTER_NEW_PASSWORD = "Please, enter a new password (min %s symbols)";
    public static final String ENTER_EMAIL = "Please, enter your email:";

    public static final Pattern VALID_EMAIL_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_USERNAME_REGEX =
            Pattern.compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){2,18}[a-zA-Z0-9]$", Pattern.CASE_INSENSITIVE);

    public static final String GREETINGS = "Welcome to Groovy Movie!";
    public static final String STARS = "           ***";
    public static final String ENTER_LOGIN = "Please enter your login or";
    public static final String REGISTER_ACCOUNT = "Register new account";
    public static final String EXIT = "Exit";
    public static final String CHECK_ACCOUNT = "Check account";
    public static final String VIEW_TICKETS = "Check tickets";
    public static final String VIEW_SESSIONS = "Check sessions (buy ticket here)";
    public static final String VIEW_MOVIE_LIBRARY = "Check movie library";
    public static final String VIEW_USERS = "Check users";
    public static final String CHANGE_PASSWORD = "Change password";
    public static final String CHANGE_EMAIL = "Change email";
    public static final String RETURN_TICKET = "Return the ticket";
    public static final String REVOKE_TICKET = "Revoke user's ticket";
    public static final String ADD_TICKET = "Buy ticket for user";
    public static final String SHOW_SESSIONS = "View upcoming sessions";
    public static final String EDIT_SESSION = "Edit session";
    public static final String ADD_SESSION = "Add new session";
    public static final String REMOVE_SESSION = "Remove session";
    public static final String EDIT_TIME = "Edit session's time";
    public static final String EDIT_MOVIE = "Change movie for this session";
    public static final String EDIT_PRICE = "Change ticket's price for this session";
    public static final String MORNING = "Morning (10:00)";
    public static final String AFTERNOON = "Afternoon (14:00)";
    public static final String EVENING = "Evening (18:00)";
    public static final String SHOW_ALL_SESSIONS = "View all sessions";
    public static final String SHOW_TODAY_SESSIONS = "View today's sessions";
    public static final String SHOW_SESSIONS_DATE = "View sessions by date...";
    public static final String BUY_TICKET = "Buy ticket";
    public static final String READ_ABOUT_MOVIE = "Read about this movie";
    public static final String VIEW_MOVIES = "Show movie list";
    public static final String ADD_MOVIE = "Add new movie to library";
    public static final String REMOVE_MOVIE = "Remove movie from library";
    public static final String CHANGE_ROLE = "Change user's role";
    public static final String CHANGE_STATUS = "Change user's status";
    public static final String REMOVE_USER = "Remove user";
    public static final String SHOW_USERS = "Show all users";
    public static final String RETURN = "Return";
    public static final String LOGOUT = "Logout";

    public static final String CHOOSE_TICKET = "Choose a ticket";
    public static final String SELECT_TICKET = "Choose a ticket. Enter a seat (XX - occupied)";
    public static final String SESSION_SHOW_MENU_HEAD = "What sessions would you like to see:";
    public static final String ADMIN_MENU_HEAD = "Whatever you want, sir?";
    public static final String CHOOSE_USER_HEAD = "Enter user's ID or name";
    public static final String CHOOSE_DATE_SESSION = "Please enter the month & day: mm ↵ dd ↵";
    public static final String CHOOSE_SESSION = "Choose session from the list:";
    public static final String CHOOSE_MOVIE = "Choose movie from the list:";
    public static final String CHOOSE_ROLE = "Choose a role:";
    public static final String TICKET_ADDED = "Ticket successfully added to %s";
    public static final String USER_DELETED = "User %s successfully deleted";

    public static final String SMALL_SEAT_SAMPLE = "0%s ";
    public static final String BIG_SEAT_SAMPLE = "%s ";
    public static final String OCCUPIED_SEAT = "XX ";

    public static final String SUCCESSFUL_PURCHASE = "Purchase completed successfully";
    public static final String UNSUCCESSFUL_PURCHASE = "Purchase unsuccessful";
    public static final String CHANGES_SAVED = "Changes saved";
    public static final String SESSION_CREATED = "Session successfully created";
    public static final String SESSION_REMOVED = "Session successfully removed";
    public static final String DELETION_FILED = "Deletion failed";
    public static final String MOVIE_ADDED = "Movie successfully added to library";
    public static final String ADDING_FAILED = "Adding failed";
    public static final String MOVIE_REMOVED = "Movie successfully removed";
    public static final String CHANGED = "%s to user %s is set";
    public static final String CHANGE_FAILED = "Change failed";

    public static final String TICKED_PURCHASED = "This ticket is already purchased";
    public static final String ENTER_TIME = "Please, choose the time";
    public static final String ENTER_PRICE = "Please, enter the price";
    public static final String ENTER_MOVIE_NAME = "Please, enter movie name:";
    public static final String ENTER_IMDb = "Enter movie's IMDb";
    public static final String ENTER_AGE_LIMIT = "Enter movie's age limit";
    public static final String ENTER_DESCRIPTION = "Write movie's description";

    public static final String SAMPLE = "%s.%s";
    public static final String SAMPLE_USER = "%s | ID: %s | %s | %s | %s | %s";
    public static final String SAMPLE_MOVIE = "%s | IMDb:%s | %s+ | ID:%s";
    public static final String SAMPLE_SESSION = "%s %s %s %s+ Price: %s RUB (id:%s)";
    public static final String SAMPLE_TICKET = "%s %s %s Seat:%s Owner:%s (ID:%s)";

    public static final String LOG_LOGIN = "{} {} logged in";
    public static final String LOG_LOGGING_FAILED = "{} {} tried to enter to his blocked account";
    public static final String LOG_REGISTER_FAILED = "registration failed: ";
    public static final String LOG_REGISTER = "new user registered: {}";
    public static final String LOG_LOGOUT = "{} {} logged out";
    public static final String LOG_PASSWORD_CHANGED = "{} {} changed his password";
    public static final String LOG_EMAIL_CHANGED = "{} {} changed his email to {}";
    public static final String LOG_TICKET_RETURNED = "{} {} returned his ticket (id:{})";
    public static final String LOG_READ_ABOUT = "{} {} interested in {}";
    public static final String LOG_PURCHASE_COMPLETE = "{} {} bought ticket (id:{})";
    public static final String LOG_PURCHASE_FAIL = "{} {} tried unsuccessfully to buy a ticket";
    public static final String LOG_TICKET_REVOKING = "{} {} revoked {}'s ticket (id:{})";
    public static final String LOG_TICKET_ADDING = "{} {} added ticket (id:{}) to user {}";
    public static final String LOG_MOVIE_ADDING = "{} {} added movie {} to library";
    public static final String LOG_MOVIE_REMOVING = "{} {} removed movie {} from library";
    public static final String LOG_CHANGING_ROLE = "{} {} changed {}'s role to {}";
    public static final String LOG_CHANGE_ROLE_FAIL = "{} {} tried to change role for {} {}";
    public static final String LOG_CHANGING_STATUS = "{} {} changed {}'s status to {}";
    public static final String LOG_CHANGE_STATUS_FAIL = "{} {} tried to change status for {} {}";
    public static final String LOG_USER_REMOVING = "{} {} removed {} {}";
    public static final String LOG_USER_REMOVING_FAIL = "{} {} failed user deletion";

    public static final String FREE = "free";

    private Values() {
    }
}
