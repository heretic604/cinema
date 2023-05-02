package com.heretic.main;

import com.heretic.main.controller.UserController;
import com.heretic.main.util.Creator;

public class Main {

    public static void main(String[] args) {

        UserController userController = Creator.createUserController();
        userController.startMenu();

    }

}