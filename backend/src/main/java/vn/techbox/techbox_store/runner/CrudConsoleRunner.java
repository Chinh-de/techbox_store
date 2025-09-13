package vn.techbox.techbox_store.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vn.techbox.techbox_store.model.User;
import vn.techbox.techbox_store.model.UserRole;
import vn.techbox.techbox_store.service.UserService;

import java.util.List;
import java.util.Scanner;

@Component
public class CrudConsoleRunner implements CommandLineRunner {

    private final UserService userService;

    public CrudConsoleRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=============================================");
        System.out.println("    SPRING BOOT CRUD CONSOLE APPLICATION     ");
        System.out.println("=============================================");

        // Thêm một vài user mẫu nếu database trống
        if (userService.getAllUsers().isEmpty()) {
            System.out.println("Database is empty. Creating sample users...");
            User user1 = new User();
            user1.setUsername("admin_user");
            user1.setEmail("admin@example.com");
            user1.setPasswordHash("hashed_password_123"); // In real app, use BCrypt
            user1.setRole(UserRole.admin);
            userService.createUser(user1);

            User user2 = new User();
            user2.setUsername("customer_user");
            user2.setEmail("customer@example.com");
            user2.setPasswordHash("hashed_password_456");
            user2.setRole(UserRole.customer);
            userService.createUser(user2);
            System.out.println("Sample users created.");
        }


        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nChoose an operation:");
            System.out.println("1. List all users");
            System.out.println("2. Add a new user");
            System.out.println("3. Update a user");
            System.out.println("4. Delete a user");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }


            switch (choice) {
                case 1:
                    listAllUsers();
                    break;
                case 2:
                    addUser(scanner);
                    break;
                case 3:
                    updateUser(scanner);
                    break;
                case 4:
                    deleteUser(scanner);
                    break;
                case 5:
                    System.out.println("Exiting application.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void listAllUsers() {
        System.out.println("\n--- All Users ---");
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            users.forEach(System.out::println);
        }
        System.out.println("-----------------");
    }

    private void addUser(Scanner scanner) {
        try {
            System.out.println("\n--- Add New User ---");
            User newUser = new User();

            System.out.print("Enter username: ");
            newUser.setUsername(scanner.nextLine());

            System.out.print("Enter email: ");
            newUser.setEmail(scanner.nextLine());

            System.out.print("Enter role (admin, staff, customer): ");
            newUser.setRole(UserRole.valueOf(scanner.nextLine().toLowerCase()));

            // For simplicity, using a placeholder for password
            newUser.setPasswordHash("default_password");

            User createdUser = userService.createUser(newUser);
            System.out.println("User created successfully: " + createdUser);
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    private void updateUser(Scanner scanner) {
        try {
            System.out.println("\n--- Update User ---");
            System.out.print("Enter user ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (userService.getUserById(id).isEmpty()) {
                System.out.println("User with ID " + id + " not found.");
                return;
            }

            User updatedDetails = new User();
            System.out.print("Enter new username: ");
            updatedDetails.setUsername(scanner.nextLine());

            System.out.print("Enter new email: ");
            updatedDetails.setEmail(scanner.nextLine());

            System.out.print("Enter new role (admin, staff, customer): ");
            updatedDetails.setRole(UserRole.valueOf(scanner.nextLine().toLowerCase()));

            User updatedUser = userService.updateUser(id, updatedDetails);
            System.out.println("User updated successfully: " + updatedUser);
        } catch (Exception e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    private void deleteUser(Scanner scanner) {
        try {
            System.out.println("\n--- Delete User ---");
            System.out.print("Enter user ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (userService.getUserById(id).isEmpty()) {
                System.out.println("User with ID " + id + " not found.");
                return;
            }

            userService.deleteUser(id);
            System.out.println("User with ID " + id + " deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }
}
