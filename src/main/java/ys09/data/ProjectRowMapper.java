package ys09.data;

import org.springframework.jdbc.core.RowMapper;
import ys09.model.Project;

import java.sql.ResultSet;
import java.sql.SQLException;

class ProjectRowMapper implements RowMapper<Project>  {

	@Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        long id = rs.getLong("id");
        long ownerId = rs.getLong("owner_id");
        String name = rs.getString("name");
        String description = rs.getString("description");

        return new Project(id, ownerId, name, description);
    }
}