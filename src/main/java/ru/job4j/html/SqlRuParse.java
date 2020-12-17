package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class SqlRuParse {
    public static void main(String[] args) throws Exception{
        var doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        var row = doc.select(".postslisttopic");
        for (Element e : row) {
            var href = e.child(0);
            var date = href.parent().parent().child(5);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            System.out.println(date.text());
        }
    }
}
