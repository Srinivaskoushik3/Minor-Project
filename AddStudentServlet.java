import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class AddStudentServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        String name = request.getParameter("name");
        String roln = request.getParameter("roll_number");
        String uname = request.getParameter("username");
        String pass = request.getParameter("password");
        String sto = request.getParameter("stop");
        String mnum = request.getParameter("mobile_number");
        String dbURL = "jdbc:mysql://localhost:3306/student_transport";
        String dbUsername = "root";
        String dbPassword = "";
        int ocseats=0;
        String query ="";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
             query = "SELECT * FROM students WHERE username = '"+uname+"'";
            Statement stmt1=conn.createStatement();
            ResultSet result = stmt1.executeQuery(query);
            if (result.next()) {
                out.println("<font color='red'>userneme already used! Please try another</font>");
                RequestDispatcher rd=request.getRequestDispatcher("/addStudent.html");
                rd.include(request,response);
            }
            else{
                query = "INSERT INTO students(name,roll_number,username,password,stop,mobile_number) VALUES('"+name+"','"+roln+"','"+uname+"','"+pass+"','"+sto+"','"+mnum+"')";
                Statement stmt=conn.createStatement();
                int eu=stmt.executeUpdate(query);
                if (eu!=0) {
                    query="Select bus_id,occupied_seats,seats FROM buses Where FIND_IN_SET('"+sto+"', stops)>0";
                    Statement stmt2=conn.createStatement();
                    ResultSet bus = stmt2.executeQuery(query);
                    int min=Integer.MAX_VALUE;
                    int bid=0;
                    while(bus.next()){
                        if(bus.getInt("occupied_seats")<min && bus.getInt("seats")>=bus.getInt("occupied_seats")+1 )
                        {
                            min=bus.getInt("occupied_seats");
                            bid=bus.getInt("bus_id");
                        }
                    }
                    query="UPDATE students SET  bus_id='"+bid+"' WHERE roll_number='"+roln+"'";
                    Statement stmt3=conn.createStatement();
                    boolean eu1=stmt3.execute(query);
                    min+=1;
                    query="UPDATE buses SET  occupied_seats='"+min+"' WHERE bus_id='"+bid+"'";
                    Statement stmt4=conn.createStatement();
                    eu1=stmt4.execute(query);
                    if(!eu1)
                    {
                        RequestDispatcher rd=request.getRequestDispatcher("success.html");
                        rd.forward(request,response);
                    }
                } else {
                    // response.sendRedirect("adminLogin.html?error=1");
                    RequestDispatcher rd=request.getRequestDispatcher("fail.html");
                        rd.forward(request,response);
                }
            }
            // stmt1.close();
            conn.close();
        } catch (Exception e) {
           
            out.println(e);
        }
    }
}
