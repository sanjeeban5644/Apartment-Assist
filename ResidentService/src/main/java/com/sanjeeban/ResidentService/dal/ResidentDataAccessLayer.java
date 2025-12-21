package com.sanjeeban.ResidentService.dal;


import com.sanjeeban.ResidentService.jdbc.JdbcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Repository
public class ResidentDataAccessLayer {

    @Autowired
    private JdbcUtil jdbcUtil;



    public int fetchCurrentCount(String residentUniqueId) {
        // check if resident already had registered any complaint or not
        int count = checkCurrentCount(residentUniqueId);
        if(count>=10) return -1;
        return count==0?insertIntoTable(residentUniqueId):updateCount(count+1,residentUniqueId);
    }

    private int updateCount(int count,String residentUniqueId) {
        int rowsAffected = 0 ;
        String sql = "update facility_workorder.t_resident_complaint_count t " +
                "set t.complaint_count = ? where t.resident_unique_id = ? and t.complaint_date = ?";

        try(Connection conn = jdbcUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, count);
            ps.setString(2,residentUniqueId);
            ps.setDate(3,  java.sql.Date.valueOf(LocalDate.now()));

            rowsAffected = ps.executeUpdate();

        }catch (SQLException ex){
            throw new RuntimeException("error in initializing db connnection-1");
        }
        return count;
    }

    private int insertIntoTable(String residentUniqueId) {
        int rowsAffected = 0;
        String sql = "INSERT INTO facility_workorder.t_resident_complaint_count \n" +
                "    (resident_unique_id, complaint_date, complaint_count)\n" +
                "VALUES \n" +
                "    (?,?,1)";

        try(Connection conn = jdbcUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, residentUniqueId);
            ps.setDate(2,  java.sql.Date.valueOf(LocalDate.now()));

            rowsAffected = ps.executeUpdate();

        }catch (SQLException ex){
            throw new RuntimeException("error in initializing db connnection-2");
        }

        return rowsAffected;
    }

    private int checkCurrentCount(String residentUniqueId) {

        int count = 0;

        String sql = "select * from facility_workorder.t_resident_complaint_count t where t.resident_unique_id=? and t.complaint_date=?";

        try(Connection conn = jdbcUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, residentUniqueId);
            ps.setDate(2,  java.sql.Date.valueOf(LocalDate.now()));

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    count = rs.getInt("complaint_count");
                }
            }
        }catch (SQLException ex){
            throw new RuntimeException("error in initializing db connnection");
        }
        return count;
    }
}
