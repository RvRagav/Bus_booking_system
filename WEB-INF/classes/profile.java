import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class profile extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = getServletContext();
        String jdbcUrl = context.getInitParameter("jdbcUrl");
        String jdbcUr = context.getInitParameter("dbUser");
        String dbPassword = context.getInitParameter("dbPassword");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("curname");
        String username1 = request.getParameter("curname");

        String newEmail = request.getParameter("email");
        String newPassword = request.getParameter("password");

        try {
            // Register the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            jdbcUrl += "?characterEncoding=UTF-8";

            Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUr, dbPassword);
            String updateEmailQuery = "UPDATE user SET Email=? WHERE Username=?";
            String updatePasswordQuery = "UPDATE user SET Password=? WHERE Username=?";
            PreparedStatement preparedStatement;

            if (newEmail != null && !newEmail.isEmpty()) {
                preparedStatement = conn.prepareStatement(updateEmailQuery);
                preparedStatement.setString(1, newEmail);
                preparedStatement.setString(2, username);
            } else if (newPassword != null && !newPassword.isEmpty()) {
                preparedStatement = conn.prepareStatement(updatePasswordQuery);
                preparedStatement.setString(1, newPassword);
                preparedStatement.setString(2, username1);
            } else {
                // Handle the case when both email and password are empty or null
                out.println("<h2>Update Failed</h2>");
                out.println("<p>No new email or password provided.</p>");
                return;
            }
            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();

            if (rowsAffected > 0) {
                out.println("<h2>Update Successful</h2>");
                out.println("<p>Password and Email updated for username: " + username + "</p>");
            } else {
                out.println("<h2>Update Failed</h2>");
                out.println("<p>No matching username found in the database.</p>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println(e);
            out.println("<p>An error occurred while updating the database.</p>");
        }
    }
}
