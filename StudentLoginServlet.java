import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class StudentLoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String dbURL = "jdbc:mysql://localhost:3306/student_transport";
        String dbUsername = "root";
        String dbPassword = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            String query = "SELECT * FROM students WHERE username = '" + username + "' AND password ='" + password + "'";
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            if (result.next()) {
                int sid = result.getInt("student_id");

                out.println("<html><head>");
                out.println("<style>");
                out.println("body {");
                out.println("  font-family: 'Arial', sans-serif;");
                out.println("  background-color: #f0f4f7;");
                out.println("  margin: 0;");
                out.println("  padding: 20px;");
                out.println("}");
                out.println("table {");
                out.println("  width: 80%;");
                out.println("  margin: 20px auto;");
                out.println("  border-collapse: collapse;");
                out.println("  background-color: #ffffff;");
                out.println("  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);");
                out.println("}");
                out.println("th, td {");
                out.println("  padding: 15px;");
                out.println("  text-align: left;");
                out.println("  border-bottom: 2px solid #607d8b;");
                out.println("}");
                out.println("th {");
                out.println("  background-color: #37474f;");
                out.println("  color: white;");
                out.println("  text-transform: uppercase;");
                out.println("  letter-spacing: 1px;");
                out.println("}");
                out.println("tr:nth-child(even) {");
                out.println("  background-color: #cfd8dc;");
                out.println("}");
                out.println("tr:nth-child(odd) {");
                out.println("  background-color: #eceff1;");
                out.println("}");
                out.println("tr:hover {");
                out.println("  background-color: #78909c;");
                out.println("  color: #ffffff;");
                out.println("}");
                out.println("td {");
                out.println("  color: #37474f;");
                out.println("}");
                out.println("</style>");
                out.println("</head><body>");
                out.println("<table border=\"1\">");
                out.println("<tr>");

                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM students WHERE student_id='" + sid + "'");
                int columns = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= columns; i++) {
                    out.println("<th>" + rs.getMetaData().getColumnName(i) + "</th>");
                }
                out.println("</tr>");
                while (rs.next()) {
                    out.println("<tr>");
                    for (int i = 1; i <= columns; i++) {
                        out.println("<td>" + rs.getString(i) + "</td>");
                    }
                    out.println("</tr>");
                }

            } else {
                RequestDispatcher rd = request.getRequestDispatcher("/studentLogin.html");
                out.print("<p style=\"color: red;\">Incorrect Username or Password</p>");
                rd.include(request, response);
            }
            result.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            out.println(e);
        }
    }
}
