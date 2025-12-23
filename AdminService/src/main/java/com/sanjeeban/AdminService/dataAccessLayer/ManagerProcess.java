package com.sanjeeban.AdminService.dataAccessLayer;


import com.sanjeeban.AdminService.dto.PendingApprovalsResponseDto;
import com.sanjeeban.AdminService.jdbc.JdbcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ManagerProcess {


    @Autowired
    private JdbcUtil jdbcUtil;

    public List<PendingApprovalsResponseDto> getListOfApprovals() {
        List<PendingApprovalsResponseDto> list = new ArrayList<>();

        String sql = "";

        try(Connection conn = jdbcUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){



        }catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return list;
    }
}
