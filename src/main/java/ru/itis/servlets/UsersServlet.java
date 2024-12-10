package ru.itis.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/profile")
public class UsersServlet extends HttpServlet {

    private static final String DB_USERNAME = "postgres";

    private static final String DB_PASSWORD = "1548qq";

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/testdb_3";


    //private UserRepository usersRepos;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            throw new IllegalStateException();
        }


        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            //usersRepos = new UsersRepositoryJdbsImpl(connection);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/html/profile.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("first_name");
        String secondName = request.getParameter("second_name");
        System.out.println(firstName + " " + secondName);
        //User user = new User();
        //try{
        //usersRepos.save(user);
        //usersRepos.save(firstName, secondName);
        //} catch () {}
    }
}
