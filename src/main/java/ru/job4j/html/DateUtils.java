package ru.job4j.html;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private String date = "";

    private String[] shortMonths = {"янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен",
            "окт", "ноя", "дек"};
    private String[] months = {"января", "февраля", "марта", "апреля", "мая", "июня", "июля",
            "августа", "сентября", "октября", "ноября", "декабря"};


    public void dateConverter(String time) {
        var today = "сегодня";
        var yesterday = "вчера";

        for (int i = 0; i < shortMonths.length; i++) {
            if (time.contains(shortMonths[i])) {
                date = time.replace(shortMonths[i], months[i]);
                break;
            }
        }

        if (time.contains("сегодня") || time.contains("вчера")) {
            String[] split = time.split(",");
            switch (split[0]) {
                case ("сегодня") -> date = time.replace(today, new SimpleDateFormat("Сегодня, (dd MM yy)").format(new Date()));
                case ("вчера") -> date = time.replace(yesterday,
                        new SimpleDateFormat("Вчера, (dd MM yy)")
                                .format(new Date(System.currentTimeMillis() - 24 * 3600 * 1000)));
            }
        }

        System.out.println(date);
    }
}
