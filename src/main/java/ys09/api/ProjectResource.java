package ys09.api;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import ys09.conf.Configuration;
import ys09.data.DataAccess;
import ys09.model.Project;

import java.util.Optional;

public class ProjectResource extends ServerResource {

    private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

    @Override
    protected Representation get() throws ResourceException {

        //we assume that the user with id = 1 is logged in
        long ownerId = 1;

        //TODO: Is this as secure as it should be?

        String idAttr = getAttribute("id");

        if (idAttr == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Missing project id");
        }

        Long id = null;
        try {
            id = Long.parseLong(idAttr);
        }
        catch(Exception e) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid project id: " + idAttr);
        }

        Optional<Project> optional = dataAccess.getProject(id);
        Project project = optional.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Project not found - id: " + idAttr));

        return JsonMapRepresentation.forSimpleResult(project);
    }

    @Override
    protected Representation delete() throws ResourceException {
        //TODO: Implement this
        throw new ResourceException(Status.SERVER_ERROR_NOT_IMPLEMENTED);
    }
}
