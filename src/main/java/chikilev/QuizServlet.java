package chikilev;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class QuizServlet extends HttpServlet {
    private final String FIRSTQUESTION = "BoilTemperature";
    private final String SECONDQUESTION = "MeltingPoint";

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String firstAnswer = req.getParameter("BoilTemperature");
        String secondAnswer = req.getParameter("MeltingPoint");
        Quiz quiz = new Quiz();
        Map<String, String> newAnswers = new HashMap<>();
        newAnswers.put(FIRSTQUESTION, firstAnswer);
        newAnswers.put(SECONDQUESTION, secondAnswer);
        quiz.addNewQuizAnswers(newAnswers);
        PrintWriter pw = resp.getWriter();
        pw.println(quiz.getStatisticPage());
    }
}
