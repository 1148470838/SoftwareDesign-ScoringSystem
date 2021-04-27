import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Servlet implementation class JoinContest
 */
@WebServlet("/ShowJoining")
public class ShowJoin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // JDBC �����������ݿ� URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/bearcome?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8";

    // ���ݿ���û��������룬��Ҫ�����Լ�������
    static final String USER = "root";
    static final String PASS = "";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowJoin() {
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
        // ������Ӧ��������
        response.setContentType("text/html;charset=UTF-8");
        try{
            // ע�� JDBC ������
            Class.forName(JDBC_DRIVER);

            // ��һ������
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
//            int level=2;
//            int userid=4;
//            //��ȡsession
//
            HttpSession session = request.getSession();
            int level = Integer.parseInt(session.getAttribute("level").toString());
            int userid = Integer.parseInt(session.getAttribute("userid").toString());
//            �жϵȼ�Ϊ1
            if(level==1)
            {
                // ִ�� SQL ��ѯ
                int x=0;
                String getuserid;
                getuserid= "SELECT userid,partake FROM competitor where userid=?;";
                pstmt = conn.prepareStatement(getuserid);
                pstmt.setInt(1,userid);
                ResultSet rs = pstmt.executeQuery();
                // չ����������ݿ�
                while(rs.next())
                {
                    String[] Splitid=rs.getString("partake").split(",");
                    for(int i=1;i<=Splitid.length;i++)
                    {
                        x=i;
                    }
                }
                out.println(x);
                rs.close();
            }

//            �жϵȼ�Ϊ2����3
            if(level==2)
            {
                // ִ�� SQL ��ѯ
                int z=0;
                String getjudge;
                getjudge= "SELECT userid,group_concat(partake,createpartake) as partake FROM judge where userid=? ;";
                pstmt = conn.prepareStatement(getjudge);
                pstmt.setInt(1,userid);
                ResultSet rsjudge = pstmt.executeQuery();
                // չ����������ݿ�
                while(rsjudge.next())
                {
                    String[] Splitidj=rsjudge.getString("partake").split(",");
                    for(int i=1;i<=Splitidj.length;i++)
                    {
                        z=i;
                    }
                }
                out.println(z);
                rsjudge.close();
            }
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