package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.job4j.model.Post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SqlRuParse implements Parse {
    private static String url = "https://www.sql.ru/forum/job-offers/";
    private static String post = "https://www.sql.ru/forum/1331429/senior-php-razrabotchik-system-architect-part-time-remote";

    public static void main(String[] args) throws Exception {
        var doc = Jsoup.connect(url).get();
        var row = doc.select(".postslisttopic");
        for (Element e : row) {
            var href = e.child(0);
            var date = href.parent().parent().child(5);
            var altCol = href.parent().parent().child(2);
//            System.out.println(altCol.text());
//            System.out.println(href.attr("href"));
//            System.out.println(altCol.attr("href"));
//            System.out.println(href.text());
//            System.out.println(date.text());

//            new DateUtils().dateConverter(date.text());
        }
//        downloadDetails();
        var sqlRuParse = new SqlRuParse();
//        sqlRuParse.list(url);
        System.out.println(sqlRuParse.detail(post));
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

    @Override
    public List<Post> list(String link) {
        Document doc = null;
        var result = new ArrayList<Post>();
        var counter = 0L;

        try {
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        var row = doc.select(".postslisttopic");
        for (Element e : row) {
            var href = e.child(0);
            var date = href.parent().parent().child(5);
            var altCol = href.parent().parent().child(2);

            var post = new Post();

            post.setId(counter);
            post.setName(altCol.text());
            post.setText(href.text());
            post.setLink(href.attr("href"));
            post.setCreated(date.text());
            counter++;
            result.add(post);
        }
        result.forEach(System.out::println);
        return result;
    }

    @Override
    public Post detail(String link) {
        var post = new Post();
        var counter = 0L;
        Document doc = null;
        try {
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        var body = doc.select(".msgBody");
        var footer = doc.select(".msgFooter");

        var data = footer.stream()
                .map(element -> {
                    var href = element.child(0).parent().parent();
                    var split = href.text().split(",");
                    return split[0];
                }).findFirst().get();

        var result = body.stream()
                .map(element -> {
                    var name = element.child(0);
                    var text = element.child(0).parent().parent().child(1);
                    post.setId(counter);
                    post.setName(name.text());
                    post.setText(text.text());
                    post.setLink("empty");
                    post.setCreated(data);
                    return post;
                }).findFirst().get();
        return result;
    }
}
