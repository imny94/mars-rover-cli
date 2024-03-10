package com.jpm.mars.rover.assignment;

import com.jpm.mars.rover.assignment.domain.controller.RoverController;
import com.jpm.mars.rover.assignment.domain.entity.Rover;
import com.jpm.mars.rover.assignment.domain.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Scanner;


@Component
@Profile("!test") // Exclude this bean when running tests
public class RoverCLI implements CommandLineRunner {
    private final Scanner scanner = new Scanner(System.in);


    @Autowired
    private RoverController roverController;

    @Override
    public void run(String... args) {
        System.out.println("Welcome to the Mars Rover CLI Tool!");
        System.out.println("Please create a rover to start");

        createRovers();

        System.out.println("Rover creation completed. Here are the rovers:");

        printAllRovers();

        System.out.println("Kindly provide commands for the rover.");
        System.out.println("Accepted formats are: `{rover_id}, {command}` OR `getAll` OR `exit`");
        System.out.println("Valid commands are: (f, b, r, l)");

        listenForNewCommands();

        scanner.close();
    }

    private void printAllRovers() {
        for (Rover rover : roverController.getAllRovers()) {
            System.out.println(rover);
        }
        System.out.println();
    }

    private void listenForNewCommands() {
        commandListener:
        while (true) {
            String usrCmd = scanner.nextLine().trim();

            String[] parts = usrCmd.split(",");
            if (parts.length == 1) {
                String cmd = parts[0].trim();
                switch (cmd) {
                    case "getAll":
                        printAllRovers();
                        continue;
                    case "exit":
                        break commandListener;
                    default:
                        System.out.println("Unsupported command, please try again.");
                }
                continue;
            } else if (parts.length != 2) {
                System.out.println("Invalid command format, please try again.");
                continue;
            }

            String roverName = parts[0].trim();
            String cmd = parts[1].trim();
            Result<Rover> execRoverCmdResult = roverController.executeCommand(roverName, cmd);
            if (!execRoverCmdResult.isSuccess()) {
                System.out.printf("Failed to execute command, error: %s\n", execRoverCmdResult.getException().orElseThrow().getMessage());
                continue;
            }
            System.out.printf("Updated rover: %s\n", execRoverCmdResult.getValue().orElseThrow());
        }
    }

    private void createRovers() {
        createNewRover();

        while (true) {
            System.out.println("Do you want to create another new rover? (yes/no)");
            boolean isCreateRover = scanner.nextLine().trim().equalsIgnoreCase("yes");

            if (!isCreateRover) {
                break;
            }

            createNewRover();
        }
    }

    private void createNewRover() {
        while (true) {
            System.out.println("Time to specify the coordinates and direction! Accepted values for directions are (N, S, E, W)");
            System.out.println("Enter the starting coordinates and direction of the rover in the following format (x, y, direction):");
            String rover_init_str = scanner.nextLine().trim();
            String[] parts = rover_init_str.split(",");
            if (parts.length != 3) {
                System.out.println("Invalid format, please try again.");
                continue;
            }

            double x, y;
            try {
                x = Double.parseDouble(parts[0].trim());
                y = Double.parseDouble(parts[1].trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid coordinates, unable to parse the string to a double: " + e.getMessage());
                continue;
            }
            String cardinalDirection = parts[2].trim().toUpperCase();
            Result<Rover> createRoverResult = roverController.createRover(x, y, cardinalDirection);
            if (createRoverResult.isSuccess()) {
                break;
            }

            System.out.println("Failed to create rover, Error: " + createRoverResult.getException().orElseThrow().getMessage() + ". Check the input format, and that directions are valid (N, S, E, W). Please try again");
        }
    }
}
