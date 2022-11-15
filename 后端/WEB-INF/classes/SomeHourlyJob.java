import javax.servlet.http.HttpServlet;
import java.io.PrintWriter;
import java.sql.*;

public class SomeHourlyJob extends HttpServlet implements Runnable {
    private static final long serialVersionUID = 1L;
    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql:///bearcome?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "";

    @Override
    public void run() {
        // Do your quarterly job here.
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // 注册 JDBC 驱动器
            Class.forName(JDBC_DRIVER);

            // 打开一个连接
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //系统当前时间
            Timestamp CurrentTime = new Timestamp(System.currentTimeMillis());

            String SelectSql = "SELECT * FROM contest where jLock!=0 and cLock=0;";
            pstmt = conn.prepareStatement(SelectSql);
            ResultSet SelectRs = pstmt.executeQuery();
            while (SelectRs.next())
            {
                //选手参加截止时间
                Timestamp EndTime = SelectRs.getTimestamp("endtime");
                int id = SelectRs.getInt("id");

                if(CurrentTime.getTime() >= EndTime.getTime())
                {
                    String UpdateSql="UPDATE contest set cLock=? where id=?;";
                    pstmt = conn.prepareStatement(UpdateSql);
                    pstmt.setInt(1, 1);
                    pstmt.setInt(2, id);
                    int UpdateRows = pstmt.executeUpdate();
                    if(UpdateRows>0)
                    {
                        System.out.print("---------Automatic Lock----------\n");
                    }
                }
            }


            // 完成后关闭
            SelectRs.close();
            pstmt.close();
            conn.close();
        }catch(SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch(Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 最后是用于关闭资源的块
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

}