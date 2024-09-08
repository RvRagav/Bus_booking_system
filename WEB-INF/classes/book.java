import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class book extends HttpServlet {
    private static final long serialVersionUID = 1L;
   

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletContext context = getServletContext();
        String jdbcUrl = context.getInitParameter("jdbcUrl");
        String jdbcUr = context.getInitParameter("dbUser");
        String dbPassword = context.getInitParameter("dbPassword");

        response.setContentType("text/html");
        PrintWriter out1 = response.getWriter();
        String username = request.getParameter("name");
        String fromLocation = request.getParameter("from");
        String toLocation = request.getParameter("to");
        int members = Integer.parseInt(request.getParameter("members"));
        String arrivalDate = request.getParameter("arrival_date");
        String leavingDate = request.getParameter("leaving_date");

        Integer price = 0;
        // Insert data into the MySQL database
        try {
            Class.forName("com.mysql.jdbc.Driver");
            jdbcUrl += "?characterEncoding=UTF-8";

            Connection con = DriverManager.getConnection(jdbcUrl, jdbcUr, dbPassword);
            String sql = "INSERT INTO places (user,Source,Destination,Members,Leaving_date,Arrival_date) VALUES (?,?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, fromLocation);
            ps.setString(3, toLocation);
            ps.setInt(4, members);
            ps.setString(5, leavingDate);
            ps.setString(6, arrivalDate);

            String query = "SELECT Prices FROM prices WHERE Source = ? AND Destination = ?;";
            PreparedStatement ps2 = con.prepareStatement(query);
            ps2.setString(1, fromLocation);
            ps2.setString(2, toLocation);

            ResultSet rs =  ps2.executeQuery();
            if (rs.next())
            {
                price = rs.getInt("Prices");
            }
            ps.executeUpdate();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out1.println(e);
        }

        // Display data in the browser
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body><style>\r\n" + //
                ".btn {\r\n" + //
                "\tdisplay: inline-block;\r\n" + //
                "\tmargin-top: 1rem;\r\n" + //
                "\tcolor: black;\r\n" + //
                "\tbackground-color: orange;\r\n" + //
                "\tpadding: .6rem 2rem;\r\n" + //
                "\tborder: .2rem solid red;\r\n" + //
                "\tcursor: pointer;\r\n" + //
                "\tfont-size: 1.7rem;\r\n" + //
                "\tborder-radius: 10px;\r\n" + //
                "\ttext-decoration: none;\r\n" + //
                "}</style>");
        out.println("<div style=" + "text-align:center" + ">");
        out.println("<h2>Booking Details:</h2>");
        out.println("<table border=" + "2" + ">");
        out.println("<tr><td>From</td><td>" + fromLocation + "</td></tr>");
        out.println("<tr><td>To</td><td>" + toLocation + "</td></tr>");
        out.println("<tr><td>Members</td><td>" + members + "</td></tr>");
        out.println("<tr><td>Arrival Date</td><td>" + arrivalDate + "</td></tr>");
        out.println("<tr><td>Leaving Date</td><td>" + leavingDate + "</td></tr>");
        out.println("<tr><td>Price for 1 person </td><td>" + (price*members) + "</td></tr>");
        out.println("</table>");

        // Add a Submit button to redirect to the payment page
        out.println("<a href=" + "'pay.html'" + ">");
        out.println("<button class=" + "btn" + " type=\"submit\" value=\"Proceed to Payment\">Proceed to Payment");
        out.println("</a>");

        out.println("</div></body></html>");
    }
}
