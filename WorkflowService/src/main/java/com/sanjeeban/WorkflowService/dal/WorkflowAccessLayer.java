package com.sanjeeban.WorkflowService.dal;


import com.sanjeeban.WorkflowService.entity.WorkflowSequence;
import com.sanjeeban.WorkflowService.jdbc.JdbcUtil;
import com.sanjeeban.WorkflowService.repository.WorkflowSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Repository
public class WorkflowAccessLayer {


    @Autowired
    private JdbcUtil jdbcUtil;

    @Autowired
    private WorkflowSequenceRepository workflowSequenceRepository;

    public String validateUserRequest(String usercode) {

        String sql = "select * from t_user_request_mapping t where t.user_code = ?";
        String availableRequests = "";

        try(Connection connection =jdbcUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){

                ps.setString(1,usercode);

                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        availableRequests = rs.getString("request_codes");
                        break;
                    }
                }

        }catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Database error with usercode : "+usercode);
        }
        return availableRequests;

    }

    public int checkIfWorkflowExists(Long workflowId)  {

        String sql = "select count(*) from facility_workorder.t_workflow_diary t where t.workflow_status_id=?";

        int count = 0;

        try(Connection conn = jdbcUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setLong(1,workflowId);


            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    count = rs.getInt(1);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Database error with error code : 532");
        }
        return count;
    }


    @Transactional
    public String generateWorkflowId() {

        LocalDate today = LocalDate.now();

        WorkflowSequence sequence = workflowSequenceRepository
                .findByDateForUpdate(today)
                .orElseGet(() -> {
                    WorkflowSequence newSequence = new WorkflowSequence();
                    newSequence.setDate(today);
                    newSequence.setLastSequence(0);
                    return newSequence;
                });

        int nextSequence = sequence.getLastSequence()+1;
        sequence.setLastSequence(nextSequence);

        workflowSequenceRepository.save(sequence);

        return formatSequence(today,nextSequence);

    }

    private String formatSequence(LocalDate today, int nextSequence) {

        return today.format(DateTimeFormatter.BASIC_ISO_DATE)+String.format("%04d",nextSequence);

    }
}
