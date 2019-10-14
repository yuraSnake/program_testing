package com.yura.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yura.model.*;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = "/lavshuk", method = RequestMethod.GET)
public class LavshukController {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ArrayQuestion arrayQuestion;
    @Autowired
    private ArrayBlank arrayBlank;
    @Autowired
    private ArrayTheme arrayTheme;

    @RequestMapping(value = "/back", method = RequestMethod.POST)
    public @ResponseBody  ModelAndView getMainBack() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("questions", arrayQuestion.getQuestionArrayList());
        modelAndView.setViewName("main");
        return modelAndView;
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView getMainPage() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("questions", arrayQuestion.getQuestionArrayList());
        modelAndView.setViewName("main");
        return modelAndView;
    }

    @RequestMapping(value = "/question", method = RequestMethod.POST)
    public @ResponseBody ModelAndView getQuestionPage(
            @RequestParam(value = "countAnswer", required = true) int countAnswer
    ) {
        List<Integer> integers = new ArrayList<Integer>();
        ModelAndView modelAndView = new ModelAndView();
        for(int i = 1; i <= countAnswer; i++) {
            integers.add(i);
        }

        modelAndView.addObject("themes", arrayTheme.getThemeList());

        modelAndView.addObject("countAnswer", integers);
        modelAndView.setViewName("question");
        return modelAndView;
    }

    @RequestMapping(value = "/deleteQuestionFromArrayQuestion", method = RequestMethod.POST)
    public void deleteQuestionFromArrayQuestion(@RequestParam(value = "nameOfQuestion", required = true) String nameOfQuestion) {

        Question delete = null;
        for(Question q : arrayQuestion.getQuestionArrayList()) {
            if(q.getName().equals(nameOfQuestion))
                delete = q;
        }
        arrayQuestion.getQuestionArrayList().remove(delete);

    }

    @RequestMapping(value = "/showInformationQuestion", method = RequestMethod.POST)
    public @ResponseBody ModelAndView showInformationQuestion(@RequestParam(value = "nameOfQuestion", required = true) String nameOfQuestion) {

        Question information = null;
        for(Question q : arrayQuestion.getQuestionArrayList()) {
            if(q.getName().equals(nameOfQuestion))
                information = q;
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("information", information);
        modelAndView.setViewName("question_information");
        return modelAndView;

    }

    @RequestMapping(value = "/insertToArrayQuestion", method = RequestMethod.POST, consumes="application/json")
    public void insertArrayQuestionToFile(@RequestBody Question question) {
        arrayQuestion.getQuestionArrayList().add(question);
    }

    @RequestMapping(value = "/arrayQuestion", method = RequestMethod.GET)
    public void showArrayQuestion() {
        for(Question question : arrayQuestion.getQuestionArrayList()) {
            System.out.println(question.getName());
        }
    }

    @RequestMapping(value = "/cleanAllQuestion", method = RequestMethod.POST)
    public ResponseEntity clearAllQuestions() {
        arrayQuestion.getQuestionArrayList().clear();
        if (arrayBlank.getBlankList().size() == 0) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/saveArrayOfQuestionsToFile", method = RequestMethod.POST)
    public void write() {
        try {
            objectMapper.writeValue(new File("base/base.json"), arrayQuestion.getQuestionArrayList());
        }catch (Exception e) {

        }
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public @ResponseBody ModelAndView getTestPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("test");
        return modelAndView;
    }

    @RequestMapping(value = "/arrayBlank", method = RequestMethod.GET)
    public void showArrayTest() {
        System.out.println(arrayBlank.getBlankList().size());
    }

    @RequestMapping(value = "/cleanAllBlank", method = RequestMethod.POST)
    public ResponseEntity clearAllBlank() {
        arrayBlank.getBlankList().clear();
        if (arrayBlank.getBlankList().size() == 0) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/sendArrayBlank", method = RequestMethod.POST)
    public ResponseEntity insertArrayTestToFile(
            @RequestParam(value = "numberOfTest", required = true) int numberOfTest,
            @RequestParam(value = "countQuestion", required = true) int countQuestion
            ) {

        Random random = new Random();
        ArrayList<Integer> list = new ArrayList<>();
        while(list.size() < countQuestion) {
            int a = random.nextInt(arrayQuestion.getQuestionArrayList().size());
            if(!list.contains(a)) {
                list.add(a);
            }
        }

        List<Question> forTest = new ArrayList<>();
        List<Question> questions = arrayQuestion.getQuestionArrayList();
        for(Integer i : list) {
            Collections.shuffle(questions.get(i).getAnswers());
            forTest.add(questions.get(i));
        }

        Blank blank = new Blank(numberOfTest, forTest);
        int flag = 0;
        for(Blank b : arrayBlank.getBlankList()) {
            if(b.getNumber() == blank.getNumber())
                flag++;
        }

        if(flag == 0) {
            arrayBlank.getBlankList().add(blank);
            return new ResponseEntity(HttpStatus.OK);
        }else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "/makeRezultAnswersOfTests", method = RequestMethod.POST)
    public void rezult() {
        FileInputStream in = null;
        FileOutputStream out = null;
        XWPFDocument templateRezultAnswersOfTests = null;

        try {
            File file = new File("in/templateAnswersOfTests.docx");
            in = new FileInputStream(file);
            templateRezultAnswersOfTests = new XWPFDocument(in);

            templateRezultAnswersOfTests.createParagraph().createRun().setText("ОТВЕТЫ");
            String str = "";
            for(Blank test : arrayBlank.getBlankList()) {
                str = str + test.getNumber() + "____";
                    for(Question question : test.getQuestions()) {
                        str = str + (test.getQuestions().indexOf(question) + 1) + "-";
                        for(Answer answer : question.getAnswers()) {
                            if(answer.getRight() == true)
                                str = str + (question.getAnswers().indexOf(answer) + 1) + "; ";
                        }
                    }
                    templateRezultAnswersOfTests.createParagraph().createRun().setText(str);
                    str = "";
            }

            out = new FileOutputStream("out/rezultAnswersOfTests.docx");
            templateRezultAnswersOfTests.write(out);
            out.close();
            in.close();
            templateRezultAnswersOfTests.close();

        } catch(Exception e) {
            e.fillInStackTrace();
        }

    }

    @RequestMapping(value = "/makeRezultTest", method = RequestMethod.POST)
    public void makeRezultTest() {
        FileInputStream in = null;
        FileOutputStream out = null;
        XWPFDocument templateTest = null;

        try {
            File file = new File("in/templateTest.docx");
            in = new FileInputStream(file);
            templateTest = new XWPFDocument(in);

            for(Blank blank : arrayBlank.getBlankList()) {

                templateTest.createParagraph().createRun().setText("ФИО________________________________________________________");

                XWPFRun runTest = templateTest.createParagraph().createRun();
                runTest.setBold(true);
                runTest.setText("Тест № " + blank.getNumber());

                for(int qNumber = 0; qNumber < blank.getQuestions().size(); qNumber++) {
                    Question question = blank.getQuestions().get(qNumber);
                    templateTest.createParagraph().createRun().setText((qNumber+1) + question.getName());
                    for(int aNumber = 0; aNumber < question.getAnswers().size(); aNumber++) {
                        Answer answer = question.getAnswers().get(aNumber);
                        templateTest.createParagraph().createRun().setText((aNumber+1) + answer.getName());
                    }
                }
                templateTest.createParagraph().createRun().addBreak(BreakType.PAGE);
            }

            out = new FileOutputStream("out/rezultTest.docx");
            templateTest.write(out);
            out.close();
            in.close();
            templateTest.close();

        } catch(Exception e) {
            e.fillInStackTrace();
        }

    }

    @RequestMapping(value = "/theme", method = RequestMethod.POST)
    public @ResponseBody ModelAndView getThemePage() {
        ModelAndView modelAndView = new ModelAndView();

        List<Question> questions = arrayQuestion.getQuestionArrayList();
        List<String> themes = new ArrayList<>();
        for(Question question : questions) {
            String t = question.getTheme();
            if(!themes.contains(t)) {
                themes.add(t);
            }
        }
        modelAndView.addObject("themes", themes);
        modelAndView.setViewName("theme");
        return modelAndView;
    }

    @RequestMapping(value = "/sendArrayTheme", method = RequestMethod.POST)
    public void insertToArrayTheme(
            @RequestParam(value = "nameOfTheme", required = true) String nameOfTheme) {

        Theme theme = new Theme(nameOfTheme);
        int flag = 0;
        for(Theme t : arrayTheme.getThemeList()) {
            if(t.getName().equals(theme.getName()))
                flag++;
        }

        if(flag == 0) {
            arrayTheme.getThemeList().add(theme);
        }
    }

    @RequestMapping(value = "/arrayTheme", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody List<Theme> showArrayTheme() {
        return arrayTheme.getThemeList();
    }

    @RequestMapping(value = "/hardTest", method = RequestMethod.POST)
    public @ResponseBody ModelAndView getHardTestPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("themes", arrayTheme.getThemeList());
        modelAndView.setViewName("hard_test");
        return modelAndView;
    }

    @RequestMapping(value = "/sendArrayBlankHardTest", method = RequestMethod.POST, consumes="application/json")
    public void sendArrayBlankHardTest(@RequestBody HardTest hardTest) {

        try {

            List<Question> forTest = new ArrayList<>();
            List<Integer> listRandom = new ArrayList<>();
            List<Question> onTheme = new ArrayList<>();

            List<Question> questions = arrayQuestion.getQuestionArrayList();
            for(Information i : hardTest.getListInformation()) {

                for(Question q : questions) {
                    if (i.getNameTheme().equals(q.getTheme()))
                        onTheme.add(q);
                }

                Random random = new Random();
                while(listRandom.size() < i.getCountQuestion()) {
                    int a = 0;
                    if (!onTheme.isEmpty()){
                        a = random.nextInt(onTheme.size());
                    }
                    if (listRandom.isEmpty()) {
                        listRandom.add(a);
                    } else {
                        if (!listRandom.contains(a)) {
                            listRandom.add(a);
                        }
                    }
                }

                for(Integer integer : listRandom) {
                    Collections.shuffle(onTheme.get(integer).getAnswers());
                    forTest.add(onTheme.get(integer));
                }

                listRandom.clear();
                onTheme.clear();
            }

            Blank blank = new Blank(hardTest.getNumber(), forTest);

            int flag = 0;
            for(Blank b : arrayBlank.getBlankList()) {
                if (b.getNumber() == blank.getNumber())
                    flag++;
            }

            if (flag == 0) {
                arrayBlank.getBlankList().add(blank);
                //return new ResponseEntity(HttpStatus.OK);
            } else {
                //return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }









        } catch (Exception e) {
            System.out.println("");
            e.fillInStackTrace();
        }




    }

}