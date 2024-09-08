import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class review extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = getServletContext();
        String jdbcUrl = context.getInitParameter("jdbcUrl");
        String dbUser = context.getInitParameter("dbUser");
        String dbPassword = context.getInitParameter("dbPassword");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            jdbcUrl += "?characterEncoding=UTF-8";
            Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
            String query = "SELECT Name,Review,Image FROM review";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            out.println("<html><head><link rel=\"preconnect\" href=\"https://fonts.gstatic.com\"><link href="+"https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap"+" rel="+"stylesheet"+"><link rel="+"stylesheet"+" href="+"https://use.fontawesome.com/releases/v5.5.0/css/all.css"+
                    "\tintegrity="+"sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU"+ "crossorigin="+"anonymous"+"><style>.head{text-align: center;" +
                    "padding: 2rem 0;}" +
                    ".head span{" +
                    "font-size: 3rem;" +
                    " background-color: rgba(255,165,0,.2);" +
                    "color: lightsalmon;" +
                    "border-radius:  0rem;" +
                    "padding: .2rem 1rem;}" +
                    ".head span.space{background: none;}" +
                    ".box{" +
                    "padding: 2rem;margin-left:200px;margin-right:200px;" +
                    "text-align: center;" +
                    "box-shadow: 0 1rem 2rem rgba(0,0,0,.1);" +
                    "border-radius: .5rem;}" +
                    ".box img{" +
                    "height: 13rem;" +
                    "width: 13rem;" +
                    "border-radius: 50%;" +
                    "object-fit: cover;" +
                    "margin-bottom: 2rem;}" +

                    ".box h2{" +
                    "color:#333;" +
                    "}" +

                    ".box p{" +
                    "color:#666;" +
                    "font-size: 1rem;" +
                    "padding: 1rem 0;}" +
                    ".left-box {" +
                    "display: flex;flex-wrap:wrap;}" +
                    ".right-box {" +
                    "display: flex;flex-wrap:wrap;" +
                    "flex-direction: row-reverse;}" +
                    ".box .stars i{color:orange;font-size: 1.5rem;}"+
                    "</style><body><h1 class=\"head\">" + //
                    "<span>R</span>" + //
                    "<span>E</span>" + //
                    "<span>V</span>" + //
                    "<span>I</span>" + //
                    "<span>E</span>" + //
                    "<span>W</span>" + //
                    "<span>S</span>" + //
                    "</h1>");
            out.println();
            int count = 0;
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                String imageUrl = resultSet.getString(3);
                String content = resultSet.getString(2);

                String boxClass = count % 2 == 0 ? "left-box" : "right-box";
                out.println("<div class='" + boxClass + "'>");
                out.println("<div class=" + "box" + ">");
                out.println("<img src='" + imageUrl + "'>");
                out.println("<h2 style="+"font-size:40px;"+">" + name + "</h2>");
                out.println("<p style="+"font-size:20px;"+">" + content + "</p>");
                out.println("<div class="+"stars"+">" + //
                        "<i class="+"fas fa-star"+"></i>" + //
                        "<i class="+"fas fa-star"+"></i>" + //
                        "<i class="+"fas fa-star"+"></i>" + //
                        "<i class="+"fas fa-star"+"></i>" + //
                        "<i class="+"far fa-star"+"></i>" + //
                        "</div></div>");

                count++;
            }
            out.println("</body></html>");
            resultSet.close();
            statement.close();
            conn.close();
        } catch (ClassNotFoundException |

                SQLException e) {
            e.printStackTrace();

            out.println(e);
        }
    }
}
