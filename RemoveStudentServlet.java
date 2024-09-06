import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class RemoveStudentServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        String sid = request.getParameter("student_id");
        String dbURL = "jdbc:mysql://localhost:3306/student_transport";
        String dbUsername = "root";
        String dbPassword = "";
        int ocseats=0;
        int bid=0;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            String query="Select bus_id FROM students Where student_id='"+sid+"'";
            Statement stmt2=conn.createStatement();
            ResultSet bidrs = stmt2.executeQuery(query);
            if(bidrs.next())
            {
                bid=bidrs.getInt("bus_id");
            }
            // out.println("err");
            query="Select occupied_seats FROM buses Where bus_id='"+bid+"'";
            Statement stmt3=conn.createStatement();
            ResultSet occrs = stmt3.executeQuery(query);
            if(occrs.next())
            {
                ocseats=occrs.getInt("occupied_seats")-1;
            }
            query = "DELETE FROM students WHERE student_id='"+sid+"'";
            Statement stmt=conn.createStatement();
            int eu=stmt.executeUpdate(query);
            if (eu!=0) {
                query="UPDATE buses SET  occupied_seats='"+ocseats+"' WHERE bus_id='"+bid+"'";
                Statement stmt1=conn.createStatement();
                boolean eu1=stmt.execute(query);
                if(!eu1){
                    RequestDispatcher rd=request.getRequestDispatcher("success.html");
                    rd.forward(request,response);
                    out.println("removed success");
                }
                else {
                    // response.sendRedirect("adminLogin.html?error=1");
                    RequestDispatcher rd=request.getRequestDispatcher("fail.html");
                        rd.forward(request,response);
                }
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
