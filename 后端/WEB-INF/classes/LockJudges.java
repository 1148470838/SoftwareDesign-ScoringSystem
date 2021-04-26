import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/LockJudges")
public class LockJudges extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // JDBC �����������ݿ� URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql:///bearcome?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false";

    // ���ݿ���û��������룬��Ҫ�����Լ�������
    static final String USER = "root";
    static final String PASS = "qertyiop1a";



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        // ������Ӧ��������
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        try {
            // ע�� JDBC ������
            Class.forName(JDBC_DRIVER);
            // ��һ������
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //��ȡsession
            HttpSession session = request.getSession();
            int level = Integer.parseInt(session.getAttribute("level").toString());
            int userid = Integer.parseInt(session.getAttribute("userid").toString());

            String ContestName = request.getParameter("ContestName");
            String Lock = request.getParameter("Lock");
            String[] SplitContest = request.getParameter("ContestName").split("t");

            //�жϴ����ߵ�id
            String creatorids;
            creatorids = "SELECT creatorid FROM contest where id=?";
            pstmt = conn.prepareStatement(creatorids);
            pstmt.setInt(1, Integer.parseInt(SplitContest[2]));
            ResultSet Creaters = pstmt.executeQuery();
            while (Creaters.next())
            {
                String[] creatorid2 = Creaters.getString("creatorid").split(",");
                String creatorid = creatorid2[0];
            if (userid==Integer.parseInt(creatorid) || level == 3)
            {
                if (Lock.equals("jLock")) {
                    // ִ�� SQL ��ѯ
                    String sql;
                    sql = "UPDATE contest set jLock=? where id=?;";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, 1);
                    pstmt.setInt(2, Integer.parseInt(SplitContest[2]));
                    int updateRows = pstmt.executeUpdate();
                    if (updateRows > 0) {
                        out.write("1");//jLock�����ɹ�
                    } else {
                        out.write("2");//jLock����ʧ��
                    }
                    String Sql = "SELECT * FROM contest where id=?";
                    pstmt = conn.prepareStatement(Sql);
                    pstmt.setInt(1, Integer.parseInt(SplitContest[2]));
                    ResultSet rs = pstmt.executeQuery();
                    //                  ������ί��id
                    while (rs.next()) {
                        String jLock = rs.getString("jLock");
                        String judgeid = rs.getString("judgeid");
                        int a = Integer.parseInt(jLock);
                        if (a == 1) {
                            String[] tokens = judgeid.split(",");
                            String addsql;
                            addsql = "INSERT INTO " + ContestName + "(userid) VALUES(?)";
                            pstmt = conn.prepareStatement(addsql);
                            for (int i = 0; i < tokens.length; i++) {
                                pstmt.setInt(1, Integer.parseInt(tokens[i]));
                                int row = pstmt.executeUpdate();
                                if (row > 0) {}
                            }
                        }

                    }
                    rs.close();
                }
                if (Lock.equals("cLock")) {
                    // ִ�� SQL ��ѯ
                    String sql;
                    sql = "UPDATE contest set cLock=? where id=?;";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, 1);
                    pstmt.setInt(2, Integer.parseInt(SplitContest[2]));
                    int updateRows = pstmt.executeUpdate();
                    if (updateRows > 0) {
                        out.write("3");//cLock�����ɹ�
                    } else {
                        out.write("4");//cLock����ʧ��
                    }
                    String Sql = "SELECT * FROM contest where id=?";
                    pstmt = conn.prepareStatement(Sql);
                    pstmt.setInt(1, Integer.parseInt(SplitContest[2]));
                    ResultSet crs = pstmt.executeQuery();
                    // ���������id
                    while (crs.next()) {
                        String cLock = crs.getString("cLock");
                        String userids = crs.getString("userid");
                        int c = Integer.parseInt(cLock);
                        if (c == 1) {
                            String[] token = userids.split(",");
                            String ac;
                            for (int i = 0; i < token.length; i++) {
                                String d = token[i];
                                ac = "alter table " + ContestName + " add column `" + d + "` varchar(30)";
                                pstmt = conn.prepareStatement(ac);
                                int row = pstmt.executeUpdate();
                                if (row == 0) {}
                            }
                        }
                    }
                    crs.close();
                }
            }
        }


            Creaters.close();
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
