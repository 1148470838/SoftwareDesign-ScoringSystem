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
public class ShowJoining extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // JDBC �����������ݿ� URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/bearcome?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8";

    // ���ݿ���û��������룬��Ҫ�����Լ�������
    static final String USER = "root";
    static final String PASS = "qertyiop1a";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowJoining() {
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
//            int level=1;
//            int userid=1;
//            //��ȡsession
            HttpSession session = request.getSession();
            int level = Integer.parseInt(session.getAttribute("level").toString());
            int userid = Integer.parseInt(session.getAttribute("userid").toString());
//            �жϵȼ�Ϊ1
            if(level==1)
            {
                // ִ�� SQL ��ѯ
                String max;
                max = "SELECT max(id) as id FROM contest;";
                pstmt = conn.prepareStatement(max);
                ResultSet rs = pstmt.executeQuery();
                // չ����������ݿ�
                while (rs.next())
                {
                    // ͨ���ֶμ���
                    String maxid = rs.getString("id");
                    int x=0;
                    for (int i=1;i<=Integer.parseInt(maxid);i++)
                    {
                        String getuseridS;
                        getuseridS="SELECT id,userid FROM contest where id=?";
                        pstmt = conn.prepareStatement(getuseridS);
                        pstmt.setInt(1,i);
                        ResultSet guserid = pstmt.executeQuery();
                        while (guserid.next())
                        {
                            String userids=guserid.getString("userid");

                            if(userids==null)
                            {
                                break;

                            }
                            String [] puserid = userids.split(",");
                            for(int a=0;a<puserid.length;a++)
                            {
                                if(userid==Integer.parseInt(puserid[a]))
                                {
                                    x++;
                                    break;
                                }
                            }
                        }
                        guserid.close();
                    }

                    out.println(x);
                }
                // ��ɺ�ر�
                rs.close();
                pstmt.close();
                conn.close();
            }

//            �жϵȼ�Ϊ2����3
            if(level==2||level==3)
            {
                // ִ�� SQL ��ѯ
                String max;
                max = "SELECT max(id) as id FROM contest;";
                pstmt = conn.prepareStatement(max);
                ResultSet rs = pstmt.executeQuery();
                // չ����������ݿ�
                while (rs.next())
                {
                    // ͨ���ֶμ���
                    String maxid = rs.getString("id");
                    int x=0;
                    for (int i=1;i<=Integer.parseInt(maxid);i++)
                    {
                        String getjudgeid;
                        getjudgeid="SELECT id,judgeid FROM contest where id=?";
                        pstmt = conn.prepareStatement(getjudgeid);
                        pstmt.setInt(1,i);
                        ResultSet gjudges = pstmt.executeQuery();
                        while (gjudges.next())
                        {
                            String judgeids=gjudges.getString("judgeid");
                            if(judgeids==null)
                            {
                                break;

                            }
                            String [] pjudgeid = judgeids.split(",");
                            for(int a=0;a<pjudgeid.length;a++)
                            {
                                if(userid==Integer.parseInt(pjudgeid[a]))
                                {
                                    x++;
                                    break;
                                }
                            }
                        }
                        gjudges.close();
                    }

                    out.println(x);
                }
                // ��ɺ�ر�
                rs.close();
                pstmt.close();
                conn.close();
            }
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