package november13;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import java.util.Optional;


public class MainRepository {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1548qq";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/testdb_3";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        UsersRepositoryJdbcImpl userRepository = new UsersRepositoryJdbcImpl(connection);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Добавить одного пользователя");
            System.out.println("2. Добавить нескольких пользователей");
            System.out.println("3. Показать всех пользователей");
            System.out.println("4. Найти пользователя по ID");
            System.out.println("5. Найти пользователя по email");
            System.out.println("6. Найти пользователя по номеру телефона");
            System.out.println("7. Найти пользователя по адресу");
            System.out.println("8. Удалить пользователя по ID");
            System.out.println("9. Обновить данные пользователя");
            System.out.println("10. Выйти");
            System.out.print("Ваш выбор: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.println("Введите данные пользователя (имя, фамилия, возраст, email, телефон, адрес):");
                    String firstName = scanner.nextLine();
                    String lastName = scanner.nextLine();
                    int age = Integer.parseInt(scanner.nextLine());
                    String email = scanner.nextLine();
                    String phone = scanner.nextLine();
                    String address = scanner.nextLine();

                    userRepository.save(new User(null, firstName, lastName, age, email, phone, address));
                    System.out.println("Пользователь добавлен.");
                }
                case 2 -> {
                    System.out.print("Сколько пользователей вы хотите добавить? ");
                    int count = Integer.parseInt(scanner.nextLine());
                    for (int i = 0; i < count; i++) {
                        System.out.println("Введите данные пользователя №" + (i + 1) + " (имя, фамилия, возраст, email, телефон, адрес):");
                        String firstName = scanner.nextLine();
                        String lastName = scanner.nextLine();
                        int age = Integer.parseInt(scanner.nextLine());
                        String email = scanner.nextLine();
                        String phone = scanner.nextLine();
                        String address = scanner.nextLine();

                        userRepository.save(new User(null, firstName, lastName, age, email, phone, address));
                        System.out.println("Пользователь №" + (i + 1) + " добавлен.");
                    }
                    System.out.println("Все пользователи успешно добавлены.");
                }
                case 3 -> {
                    System.out.println("Все пользователи:");
                    userRepository.findAll().forEach(user ->
                            System.out.println(user.getId() + ": " + user.getFirstname() + " " +
                                    user.getLastname() + ", Возраст: " + user.getAge() +
                                    ", Email: " + user.getEmail() + ", Телефон: " + user.getPhone() +
                                    ", Адрес: " + user.getAddress()));
                }
                case 4 -> {
                    System.out.print("Введите ID пользователя: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    Optional<User> user = userRepository.findById(id);
                    if (user.isPresent()) {
                        System.out.println("Пользователь найден: " + user.get());
                    } else {
                        System.out.println("Пользователь с таким ID не найден.");
                    }
                }
                case 5 -> {
                    System.out.print("Введите email пользователя: ");
                    String email = scanner.nextLine();
                    Optional<User> user = userRepository.findByEmail(email);
                    if (user.isPresent()) {
                        System.out.println("Пользователь найден: " + user.get());
                    } else {
                        System.out.println("Пользователь с таким email не найден.");
                    }
                }
                case 6 -> {
                    System.out.print("Введите номер телефона пользователя: ");
                    String phone = scanner.nextLine();
                    Optional<User> user = userRepository.findByPhone(phone);
                    if (user.isPresent()) {
                        System.out.println("Пользователь найден: " + user.get());
                    } else {
                        System.out.println("Пользователь с таким телефоном не найден.");
                    }
                }
                case 7 -> {
                    System.out.print("Введите адрес пользователя: ");
                    String address = scanner.nextLine();
                    Optional<User> user = userRepository.findByAddress(address);
                    if (user.isPresent()) {
                        System.out.println("Пользователь найден: " + user.get());
                    } else {
                        System.out.println("Пользователь с таким адресом не найден.");
                    }
                }
                case 8 -> {
                    System.out.print("Введите ID пользователя для удаления: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    userRepository.removeById(id);
                    System.out.println("Пользователь удалён.");
                }
                case 9 -> {
                    System.out.print("Введите ID пользователя для обновления: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    Optional<User> user = userRepository.findById(id);
                    if (user.isPresent()) {
                        System.out.println("Введите новые данные (имя, фамилия, возраст, email, телефон, адрес):");
                        String firstName = scanner.nextLine();
                        String lastName = scanner.nextLine();
                        int age = Integer.parseInt(scanner.nextLine());
                        String email = scanner.nextLine();
                        String phone = scanner.nextLine();
                        String address = scanner.nextLine();

                        userRepository.update(new User(id, firstName, lastName, age, email, phone, address));
                        System.out.println("Данные пользователя обновлены.");
                    } else {
                        System.out.println("Пользователь с таким ID не найден.");
                    }
                }
                case 10 -> {
                    connection.close();
                    scanner.close();
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}