package com.tictactoe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "LogicServlet", value = "/logic")
public class LogicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession currentSession = req.getSession(false);
        if (currentSession.getAttribute("field") == null) {
            getServletContext().getRequestDispatcher("/start").forward(req, resp);
        }
        Field field = extractAttribute(currentSession, "field");
        GameScope gameScope = extractAttribute(currentSession, "gameScope");

        int index = getSelectedIndex(req);
        Sign currentSign = field.getField().get(index);
        if (Sign.EMPTY != currentSign) {
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        field.getField().put(index, Sign.CROSS);
        if (checkWin(resp, currentSession, field, gameScope)) {
            return;
        }

        int emptyFieldIndex = field.botMove();
        if (emptyFieldIndex >= 0) {
            field.getField().put(emptyFieldIndex, Sign.NOUGHT);
            if (checkWin(resp, currentSession, field, gameScope)) {
                return;
            }
        } else {
            gameScope.countDraw();
            currentSession.setAttribute("draw", true);
            List<Sign> data = field.getFieldData();
            currentSession.setAttribute("data", data);
            resp.sendRedirect("/index.jsp");
            return;
        }
        List<Sign> data = field.getFieldData();
        currentSession.setAttribute("data", data);
        currentSession.setAttribute("field", field);

        resp.sendRedirect("/index.jsp");
    }

    private int getSelectedIndex(HttpServletRequest req) {
        String click = req.getParameter("click");
        boolean isNumeric = click.matches("\\d+");
        return isNumeric ? Integer.parseInt(click) : 0;
    }

    private <T> T extractAttribute(HttpSession session, String nameAttribute) {
        Object attribute = session.getAttribute(nameAttribute);
        Class<?> clazz = getaClass(nameAttribute);
        if (clazz != attribute.getClass()) {
            session.invalidate();
            throw new RuntimeException("Session is broken, try one more time");
        }
        return (T) attribute;
    }

    private Class<?> getaClass(String nameAttribute) {
        Class<?> clazz;
        String firstChar = nameAttribute.substring(0, 1);
        String className = "com.tictactoe." + nameAttribute.replaceFirst(firstChar, firstChar.toUpperCase());
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return clazz;
    }

    private boolean checkWin(HttpServletResponse response, HttpSession session, Field field, GameScope gameScope) {
        Sign winner = field.checkWin();
        if (Sign.CROSS == winner || Sign.NOUGHT == winner) {
            gameScope.countScope(winner);
            List<Sign> data = field.getFieldData();
            List<Integer> listIndexWinCell = field.getListIndexWinCell();
            session.setAttribute("winner", winner);
            session.setAttribute("data", data);
            session.setAttribute("dataWinCell", listIndexWinCell);
            try {
                response.sendRedirect("/index.jsp");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }
}
