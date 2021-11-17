package com.cashRegister.controller;

import com.cashRegister.WebAdresses;
import com.cashRegister.exception.ShiftNotFoundException;
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
import java.time.LocalDate;
import java.util.List;

@WebServlet("/cashertStart")
public class CasherStartServlet extends HttpServlet {

    private ShiftRepository shiftRepository;

    private static final Logger log = LogManager.getLogger(CasherStartServlet.class);

    public CasherStartServlet() {
        this.shiftRepository = ShiftRepository.getShiftRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDate localDate = LocalDate.now();
        Shift openShift = ShiftRepository.getShiftRepository().firstOpenShift();
        req.getSession().setAttribute("openshift", openShift);
        req.getSession().setAttribute("localDate", localDate);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(WebAdresses.CASHER_START_FORWARD);
        requestDispatcher.forward(req, resp);
    }
}
