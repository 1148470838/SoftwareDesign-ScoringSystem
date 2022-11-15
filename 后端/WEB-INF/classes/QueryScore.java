import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;

@WebServlet("/QueryScore")
public class QueryScore extends HttpServlet{
    private static final long serialVersionUID = 1L;
    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql:///bearcome?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        // 设置响应内容类型
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        try {
            // 注册 JDBC 驱动器
            Class.forName(JDBC_DRIVER);
            // 打开一个连接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //获取session
            HttpSession session = request.getSession();
            int level = Integer.parseInt(session.getAttribute("level").toString());
            int JudgeId = Integer.parseInt(session.getAttribute("userid").toString());
            String ContestId = request.getParameter("ContestId");

            //定义储存Map的ArrayList
            ArrayList<Map> arraylist =new ArrayList<>();

            if(level==2||level==3)
            {
                //检索对应的judgeid的比赛
                String judge="SELECT partake from judge where userid=?";
                pstmt = conn.prepareStatement(judge);
                pstmt.setInt(1,JudgeId);
                ResultSet judgers = pstmt.executeQuery();
                while(judgers.next())
                {
                    String partake = judgers.getString("partake");
                    String[] tokenp = partake.split(",");
                    for (int p=0; p < tokenp.length; p++)
                    {
                        String contestid;
                        contestid = "SELECT * FROM contest where id=? ";
                        pstmt = conn.prepareStatement(contestid);
                        pstmt.setInt(1,Integer.parseInt(tokenp[p]));
                        ResultSet contest=pstmt.executeQuery();
                        while (contest.next())
                        {
                            String id= contest.getString("id");
                            if ( ContestId.equals(id))
                            {
                                String getuserid="SElECT userid from contest where id=?";
                                pstmt = conn.prepareStatement(getuserid);
                                pstmt.setInt(1,Integer.parseInt(id));
                                ResultSet contest1=pstmt.executeQuery();
                                while(contest1.next())
                                {
                                    String userid=contest.getString("userid");
                                    String[] tokenu = userid.split(",");
                                    for(int a=0;a<tokenu.length;a++)
                                    {
                                        String users="SELECT * from users where userid=?";
                                        pstmt = conn.prepareStatement(users);
                                        pstmt.setInt(1,Integer.parseInt(tokenu[a]));
                                        ResultSet  usrs = pstmt.executeQuery();
                                        while (usrs.next())
                                        {
                                            //map
                                            Map<String,String> map=new HashMap<>();
                                            String name=usrs.getString("nickname");
//                                            map.put(tokenu[a],name);
                                            map.put("userid",tokenu[a]);
                                            map.put("nickname",name);
                                            arraylist.add(map);
                                        }
                                        usrs.close();
                                    }
                                }
                                contest1.close();
                            }
                        }
                        contest.close();
                    }
                }
                Gson gson = new Gson();
                String userJson = gson.toJson(arraylist);
                out.write(userJson);
//            处理完关闭
                judgers.close();
                pstmt.close();
                conn.close();
            }
            else
            {
                out.write("9");
            }
        } catch(SQLException se) {
            // 处理 JDBC 错误

            se.printStackTrace();
        } catch(Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally {
            // 最后是用于关闭资源的块
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
