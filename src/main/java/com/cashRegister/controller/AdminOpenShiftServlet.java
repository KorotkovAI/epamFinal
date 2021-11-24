package com.cashRegister.controller;

import com.cashRegister.WebAdresses;
import com.cashRegister.model.Shift;
import com.cashRegister.repository.ShiftRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(WebAdresses.OPEN_SHIFT)
public class AdminOpenShiftServlet extends HttpServlet {

    ShiftRepository shiftRepository;

    private static final Logger log = LogManager.getLogger(AdminOpenShiftServlet.class);

    public AdminOpenShiftServlet() {
        this.shiftRepository = ShiftRepository.getShiftRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Shift openShift = shiftRepository.firstOpenShift();

        if (openShift != null) {
            resp.sendRedirect(WebAdresses.ERROR_PAGE);
        } else {
            boolean status = shiftRepository.openShift();
            if (status) {
                Shift newOpenShift = shiftRepository.firstOpenShift();
                req.getSession().removeAttribute("openShift");
                req.getSession().setAttribute("openShift", newOpenShift);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(WebAdresses.OPEN_SHIFT_PAGE);
                requestDispatcher.forward(req, resp);
            } else {
                resp.sendRedirect(WebAdresses.ERROR_PAGE);
            }
        }
    }
}
