import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ViewBusesServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String sid = request.getParameter("student_id");
        String dbURL = "jdbc:mysql://localhost:3306/student_transport";
        String dbUsername = "root";
        String dbPassword = "";

        out.println("<html><head>");
        out.println("<style>");
        out.println("body {");
        out.println("  font-family: 'Trebuchet MS', sans-serif;");
        out.println("  background-color: #e0f7fa;");
        out.println("  margin: 0;");
        out.println("  padding: 20px;");
        out.println("}");
        out.println("table {");
        out.println("  width: 80%;");
        out.println("  margin: 20px auto;");
        out.println("  border-collapse: collapse;");
        out.println("  background-color: #ffffff;");
        out.println("  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);");
        out.println("}");
        out.println("th, td {");
        out.println("  padding: 15px;");
        out.println("  text-align: left;");
        out.println("  border-bottom: 2px solid #009688;");
        out.println("}");
        out.println("th {");
        out.println("  background-color: #00796b;");
        out.println("  color: white;");
        out.println("  text-transform: uppercase;");
        out.println("  letter-spacing: 1px;");
        out.println("}");
        out.println("tr:nth-child(even) {");
        out.println("  background-color: #b2dfdb;");
        out.println("}");
        out.println("tr:nth-child(odd) {");
        out.println("  background-color: #e0f2f1;");
        out.println("}");
        out.println("tr:hover {");
        out.println("  background-color: #80cbc4;");
        out.println("  color: #ffffff;");
        out.println("}");
        out.println("td {");
        out.println("  color: #00796b;");
        out.println("}");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<table border=\"1\">");
        out.println("<tr>");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM buses");

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

        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }

        out.println("</table>");
        out.println("</body></html>");
    }
}
