package com.tictactoe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "InitServlet", value = "/start")
public class InitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession currentSession = req.getSession(true);
        Field field = new Field();
        GameScope gameScope = (GameScope) currentSession.getAttribute("gameScope");
        if(gameScope == null){
            gameScope = new GameScope();
            currentSession.setAttribute("gameScope", gameScope);
        }
        List<Sign> data = field.getFieldData();
        currentSession.setAttribute("field", field);
        currentSession.setAttribute("data", data);
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }



}
