package ys09.data;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ys09.model.Project;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataAccess {

    private static final int MAX_TOTAL_CONNECTIONS = 16;
    private static final int MAX_IDLE_CONNECTIONS   = 8;
    
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setup(String driverClass, String url, String user, String pass) throws SQLException {

        //initialize the data source
	    BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName(driverClass);
        bds.setUrl(url);
        bds.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        bds.setMaxIdle(MAX_IDLE_CONNECTIONS);
        bds.setUsername(user);
        bds.setPassword(pass);
        bds.setValidationQuery("SELECT 1");
        bds.setTestOnBorrow(true);
        bds.setDefaultAutoCommit(true);

        //check that everything works OK        
        bds.getConnection().close();

        //initialize the jdbc template utilitiy
        jdbcTemplate = new JdbcTemplate(bds);

        //keep the dataSource for the low-level manual example to function (not actually required)
        dataSource = bds;
    }

    public List<Project> getProjects(long ownerId, Limits limits) {
        //TODO: Support limits
        Long[] sqlParams = new Long[]{ownerId};
        return jdbcTemplate.query("select * from project where owner_id = ?", sqlParams, new ProjectRowMapper());
    }

    public List<Project> getProjectsAlt(long ownerId) {
        
        List<Project> projects = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = dataSource.getConnection(); //borrow the connection from the pool
            ps = con.prepareStatement("select * from project where owner_id = ?");
            ps.setLong(1, ownerId);
            rs = ps.executeQuery();
            while(rs.next()) {
                
                Project project = new Project(
                    rs.getLong("id"),
                    rs.getLong("owner_id"),
                    rs.getString("name"),
                    rs.getString("description")
                );

                projects.add(project);
            }

            return projects;
        } 
        catch (SQLException e) {
            //report the error as a runtime exception
            throw new RuntimeException(e.getMessage(), e);
        } 
        finally {
            if (ps != null) {
                try { 
                    ps.close(); //closes the ResultSet too
                } 
                catch (Exception e) {
                    //log this (leak)
                } 
            }
            if (con != null) {
                try {
                    con.close(); //return the connection to the pool
                } 
                catch (Exception e) {
                    //log this (leak)
                }                    
            }    
        }
    }

}