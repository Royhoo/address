package cn.royhoo.address.sprider.dao;

import cn.royhoo.address.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by luoyihu on 2017/9/21.
 */
public class SpriderAddressDao {
    public void insertThreeGradeData(List<String[]> places){
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO spider_address_contry_only(placeCode, placeName) values(?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            int cnt = 0;
            for(String[] place : places){
                ps.setString(1, place[0]);
                ps.setString(2, place[1]);
                ps.addBatch();
                cnt++;
                if(cnt % 1 == 1000){
                    ps.executeBatch();
                    conn.commit();
                }
            }
            if(cnt % 1000 != 0){
               ps.executeBatch();
               conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
