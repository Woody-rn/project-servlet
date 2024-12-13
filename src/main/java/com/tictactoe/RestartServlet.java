package com.tictactoe;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@WebServlet(name = "RestartServlet", value = {"/restart", "/reset"})
public class RestartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestURI = req.getRequestURI();
        var session = req.getSession();
        handleSession(requestURI, session);
        resp.sendRedirect("/start");
    }

    private void handleSession(String requestURI, HttpSession session) {
        if ("/reset".equals(requestURI)) {
            session.invalidate();
        } else {
            Collections.list(session.getAttributeNames()).stream()
                    .filter(name -> !Objects.equals(name, "gameScope"))
                    .forEach(session::removeAttribute);
        }
    }
}
