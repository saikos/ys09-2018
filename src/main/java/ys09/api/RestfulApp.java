package ys09.api;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * The Restlet App, mapping URL patterns to ServerSideResources.
 */
public class RestfulApp extends Application {

	@Override
	public synchronized Restlet createInboundRoot() {

		Router router = new Router(getContext());

		//GET
		router.attach("/config", ConfigResource.class);

		//GET, POST
		router.attach("/projects", ProjectsResource.class);

		//GET, DELETE
		router.attach("/projects/{id}", ProjectResource.class);

		return router;
	}

}