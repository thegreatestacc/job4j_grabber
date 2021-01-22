package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

//            new DateUtils().dateConverter(date.text());
        }
        downloadDetails();
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

    public static HashMap<Object, Object> downloadDetails() throws IOException {
        var desc = new ArrayList<String>();
        var data = new ArrayList<String>();
        var result = new HashMap<>();
        url = "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";
        var doc = Jsoup.connect(url).get();
        var row = doc.select(".msgBody");
        var footer = doc.select(".msgFooter");

        var info = row.stream()
                .map(element -> {
                    var href = element.child(0).parent().parent().child(1);
                    desc.add(href.text());
                    return desc.get(0);
                }).findFirst().get();

        var str = footer.stream()
                .map(element -> {
                    var href = element.child(0).parent().parent();
                    data.add(href.text());
                    return data.get(0);
                }).findFirst().get();

        var split = str.split(",");

        result.put(split[0], info);
        System.out.println(split[0] + "\n" + info);

        return result;
    }
}
