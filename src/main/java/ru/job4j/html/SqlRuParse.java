package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse {
    private static String url = "https://www.sql.ru/forum/job-offers/";

    public static void main(String[] args) throws Exception {
        var doc = Jsoup.connect(url).get();
        var row = doc.select(".postslisttopic");
        for (Element e : row) {
            var href = e.child(0);
            var date = href.parent().parent().child(5);
//            System.out.println(href.attr("href"));
//            System.out.println(href.text());

            new DateUtils().newMethod(date.text());
        }
    }

    public static List<String> dataToString(String url) {
        var result = new ArrayList<String>();
        try {
            var doc = Jsoup.connect(url).get();
            var row = doc.select(".postslisttopic");
            for (Element e : row) {
                var href = e.child(0);
                var date = href.parent().parent().child(5);
                result.add(date.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<String> parseFirstFivePagesDateToString(String url, Integer page) throws IOException {
        var result = new ArrayList<String>();
        for (int i = 1; i <= page; i++) {
            var doc = Jsoup.connect(url + page).get();
            var row = doc.select(".postslisttopic");
            for (Element e : row) {
                var href = e.child(0);
                var date = href.parent().parent().child(5);
                result.add(date.text());
            }
        }
        return result;
    }

    public static void downloadDetails() throws IOException {
        var result = new ArrayList<String>();
        url = "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";
        var doc = Jsoup.connect(url).get();
        var row = doc.select(".msgBody");

        for (Element e : row) {
            var href = e.child(0).parent().parent();
            result.add(href.text());
        }
        System.out.println(result);
    }
}
