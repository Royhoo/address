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
    /**
     * 插入三级以内区划数据
     * @param places
     */
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
    /**
     * 插入四五级区划数据
     */
    public void insertTownVillageData(List<String[]> places){
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO spider_address_town_village(place_code, cx_flag, place_name) values(?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            int cnt = 0;
            for(String[] place : places){
                ps.setString(1, place[0]);
                if(place.length == 2){
                    ps.setString(2, "");
                    ps.setString(3, place[1]);
                } else{
                    ps.setString(2, place[1]);
                    ps.setString(3, place[2]);
                }
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
    /**
     * 插入道路数据
     * @param road
     */
    public void insertRoadData(List<String> road, String city){
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO spider_road(city, road_name) values(?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            int cnt = 0;
            for(String place : road){
                ps.setString(1, city);
                ps.setString(2, place);
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
    /**
     * 插入小区数据
     * @param house
     */
    public void insertHouseData(List<String> house, String city){
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO spider_house(city, house) values(?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            int cnt = 0;
            for(String place : house){
                ps.setString(1, city);
                ps.setString(2, place);
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
    /**
     * 插入美团地址数据
     * @param address
     */
    public void insertMeituanAddressData(List<String> address, String city){
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO spider_meituan_address(city, address) values(?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            int cnt = 0;
            for(String addr : address){
                ps.setString(1, city);
                ps.setString(2, addr);
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
