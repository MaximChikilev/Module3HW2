package chikilev;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Quiz {
    private Map<String, Map<String, Integer>> quizStatistic = new HashMap<>();
    private Set<String> questionsSet = new HashSet<>();
    private String filePath = "C:\\Users\\Maxim\\IdeaProjects\\Homeworks\\module3JavaPro\\HWLesson2\\demo\\src\\main\\resources\\statistic.txt";
    private String fileStatisticPagePath = "C:\\Users\\Maxim\\IdeaProjects\\Homeworks\\module3JavaPro\\HWLesson2\\demo\\src\\main\\resources\\statisticPage.txt";
    private StringBuilder meltingPointStatistic = new StringBuilder();
    private StringBuilder boilTemperatureStatistic = new StringBuilder();


    public Quiz() {
        loadStatistic();
        questionsSet = quizStatistic.keySet();
    }

    public void loadStatistic() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            while (bufferedReader.ready()) {
                String[] line = bufferedReader.readLine().split(";");
                String question = line[0];
                Map<String, Integer> answersStatistic = parseStatistic(line);
                quizStatistic.put(question, answersStatistic);
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

    private Map<String, Integer> parseStatistic(String[] line) {
        StringBuilder statisticForHtmlPage;
        if (line.length == 3) {
            statisticForHtmlPage = meltingPointStatistic;
        } else statisticForHtmlPage = boilTemperatureStatistic;
        Map<String, Integer> answers = new HashMap<>();
        for (int i = 1; i < line.length; i++) {
            String[] answer = line[i].split(":");
            statisticForHtmlPage.append("<td>" + answer[1] + "</td>\n");
            answers.put(answer[0], Integer.valueOf(answer[1]));
        }
        return answers;
    }

    public void addNewQuizAnswers(Map<String, String> newAnswers) {
        for (String element : questionsSet) {
            String answer = newAnswers.get(element);
            Map<String, Integer> answerStatistic = quizStatistic.get(element);
            Integer answerFrequency = answerStatistic.get(answer);
            answerFrequency += 1;
            answerStatistic.put(answer, answerFrequency);
        }
        saveStatistic();
    }

    private void saveStatistic() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (String element : questionsSet) {
                StringBuilder line = new StringBuilder(element + ";");
                Map<String, Integer> answerStatistic = quizStatistic.get(element);
                for (String answer : answerStatistic.keySet()) {
                    line.append(answer + ":" + answerStatistic.get(answer) + ";");
                }
                bufferedWriter.write(line.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {

        }
    }

    public String getStatisticPage() {
        StringBuilder statisticPage = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileStatisticPagePath));
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                statisticPage.append(line + "\n");
                if (line.contains("<td>BoilTemperature</td>")) {
                    statisticPage.append(boilTemperatureStatistic);
                } else if (line.contains("<td>MeltingPoint</td>")) {
                    statisticPage.append(meltingPointStatistic);
                }
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        return statisticPage.toString();
    }
}
