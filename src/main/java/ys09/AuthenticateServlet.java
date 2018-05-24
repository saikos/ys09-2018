package ys09;

import ys09.conf.Configuration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Always succeeds, signing in user with id = 1

        String basePath = Configuration.getInstance().getContextPath();

        //Create a new (server-side) session. This will also create client cookie.
        HttpSession session = req.getSession(true);
        //Set a session attribute
        session.setAttribute("test", "test");
        session.setAttribute("user", "1");
        //redirect to the home page
        resp.sendRedirect(basePath + "/scrumtool/home.vm");
    }
}
