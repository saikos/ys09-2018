package ys09.api

import org.restlet.Request
import spock.lang.Specification

import java.util.concurrent.ConcurrentHashMap

class ProjectResourceSpecification extends Specification {

    def "Requests with missing id attribute throw exception"() {

        given:
            ProjectResource resource = new ProjectResource()
            Request req = Mock()
            req.getAttributes() >> { [:] as ConcurrentHashMap }
            resource.setRequest(req)

        when:
            resource.get()

        then:
            thrown(org.restlet.resource.ResourceException)
    }

    def "Requests with non-numeric id attribute throws exception"() {
        given:
            ProjectResource resource = new ProjectResource()
            Request req = Mock()
            req.getAttributes() >> { [id: "abc"] as ConcurrentHashMap }
            resource.setRequest(req)

        when:
            resource.get()

        then:
            thrown(org.restlet.resource.ResourceException)
    }
}