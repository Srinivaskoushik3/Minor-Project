import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class RemoveBusServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        String bid = request.getParameter("bus_id");
        String dbURL = "jdbc:mysql://localhost:3306/student_transport";
        String dbUsername = "root";
        String dbPassword = "";
        int ocseats=0;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            String query = "DELETE FROM buses WHERE bus_id='"+bid+"'";
            Statement stmt=conn.createStatement();
            int eu=stmt.executeUpdate(query);
            if (eu!=0) {
                RequestDispatcher rd=request.getRequestDispatcher("ReallocateServlet");
                rd.forward(request,response);
                // out.println("INSERTED success");
            } else {
                // response.sendRedirect("adminLogin.html?error=1");
                RequestDispatcher rd=request.getRequestDispatcher("fail.html");
                        rd.forward(request,response);
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
           
            out.println(e);
        }
    }
}
