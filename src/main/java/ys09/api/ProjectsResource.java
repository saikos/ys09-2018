package ys09.api;

import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import ys09.conf.Configuration;
import ys09.data.DataAccess;
import ys09.data.Limits;
import ys09.model.Project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectsResource extends ServerResource {

    private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

    @Override
    protected Representation get() throws ResourceException {

        //we assume that the user with id = 1 is logged in
        long ownerId = 1;
        List<Project> projects = dataAccess.getProjects(ownerId, new Limits(0, 10));

        Map<String, Object> map = new HashMap<>();
        //map.put("start", xxx);
        //map.put("count", xxx);
        //map.put("total", xxx);
        map.put("results", projects);

        return new JsonMapRepresentation(map);
    }
}
