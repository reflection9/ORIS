package november9;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1548qq";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/testdb_3";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from driver where age > 25");
        System.out.println("Водители старше 25 лет:");
        while (result.next()) {
            System.out.println(result.getInt("id") + " " + result.getString("name"));
        }
        String sqlInsertUser = "insert into driver(name, surname, age)" +
                "values (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertUser);
        for (int i = 0; i < 6; i++) {
            Scanner scanner = new Scanner(System.in);
            String firstName = scanner.nextLine();
            String secondName = scanner.nextLine();
            int age = Integer.parseInt(scanner.nextLine());

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, secondName);
            preparedStatement.setInt(3, age);
            preparedStatement.addBatch();
        }

        int[] affectedRows = preparedStatement.executeBatch();

        System.out.println("Было добавлено " + affectedRows.length + " строк");
    }
}