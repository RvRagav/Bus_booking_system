import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class signup extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = getServletContext();
        String jdbcUrl = context.getInitParameter("jdbcUrl");
        String dbUser = context.getInitParameter("dbUser");
        String dbPassword = context.getInitParameter("dbPassword");

        String username = request.getParameter("uname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            jdbcUrl += "?characterEncoding=UTF-8";
            Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

            String insertQuery = "INSERT INTO user  VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            out.println("<html><body><p>");
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                //out.println("row inserted ");
                response.sendRedirect("tourlogin.html");
            }
            else {
                out.println("row is not inserted");
            }
            out.println("</p></body></html>");
            conn.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            out.println(e);
        } catch (SQLException e) {
            e.printStackTrace();
            out.println(e);
        }
    }
}
