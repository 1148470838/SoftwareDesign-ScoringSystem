import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servlet implementation class ExamineJudges
 */
@WebServlet("/ExamineJudges")
public class ExamineJudges extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // JDBC �����������ݿ� URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/bearcome?useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT%2B8";
    
    // ���ݿ���û��������룬��Ҫ�����Լ�������
    static final String USER = "root";
    static final String PASS = ""; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExamineJudges() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PrintWriter out = response.getWriter();
        ResultSet rs = null;
        // ������Ӧ��������
        response.setContentType("text/html;charset=UTF-8");
        try{
            // ע�� JDBC ������
            Class.forName(JDBC_DRIVER);
            // ��һ������
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            if(request.getParameter("action") != null)
            {
                String action = request.getParameter("action");
                String sql;
                if(action.equals("show")) //��ʾ������ί����Ϣ
                {
                    /*if(session.getAttribute("level") == 3){*/
                    sql = "SELECT * from examine;";
                    pstmt = conn.prepareStatement(sql);
                    rs = pstmt.executeQuery();
                    while(rs.next())
                    {
                        int userid = rs.getInt("userid");
                        String description = rs.getString("description");
                        sql = "SELECT name from users where userid=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1,userid);
                        rs = pstmt.executeQuery();
                        while(rs.next())
                        {
                            String name = rs.getString("name");
                            out.println("{\"userid\":\"" + userid +
                            "\",\"name\":\"" + name +
                            "\",\"description\":\"" + description +
                            "\"};");
                        }
                    }
                }
                else if(action.equals("request")) //�û�������ί
                {
                    String description = request.getParameter("description");
                    /*int userid = session.getAttribute("id");*/
                    int userid = 1;
                    /*if(userid != null && session.getAttribute("level") == 1){*/
                    sql = "SELECT * from examine where userid=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1,userid);
                    rs = pstmt.executeQuery();
                    if(!rs.first())
                    {
                        sql = "INSERT INTO examine(userid,description) VALUES(?,?);";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1,userid);
                        pstmt.setString(2,description);
                        pstmt.executeUpdate();

                        out.println(1);   
                    }
                    else out.println(2);                 
                }
                else if(action.equals("accept")) //����Ա��׼��ί����
                {
                    /*if(session.getAttribute("level") == 3){*/
                    int userid = Integer.parseInt(request.getParameter("userid"));
                    sql = "SELECT * FROM examine where userid=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1,userid);
                    rs = pstmt.executeQuery();
                    while(rs.next())
                    {
                        String description2 = rs.getString("description");
                        if(description2 != "")
                        {
                            String can = request.getParameter("can");
                            if(can.equals("yes"))
                            {
                                sql = "UPDATE users SET level=2 where userid=?";
                                pstmt = conn.prepareStatement(sql);
                                pstmt.setInt(1,userid);
                                pstmt.executeUpdate();

                                out.println(1); //ͨ�����
                            }
                            else if(can.equals("no"))
                            {
                                out.println(2); //�ܾ�ͨ��
                            } 
                            sql = "DELETE FROM examine WHERE userid=?";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setInt(1,userid);
                            pstmt.executeUpdate();   
                        }
                        else out.println(3); //�Ѿ�������
                    }
                }
            }
            // ��ɺ�ر�
            rs.close();
            pstmt.close();
            conn.close();             
        } catch(SQLException se) {
            // ���� JDBC ����
            se.printStackTrace();
        } catch(Exception e) {
            // ���� Class.forName ����
            e.printStackTrace();
        }finally{
            // ��������ڹر���Դ�Ŀ�
            try{
                if(pstmt!=null)
                pstmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
       
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}