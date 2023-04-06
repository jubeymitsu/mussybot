package ru.stomprf.main;


import ru.stomprf.main.bot.TestEnum;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        System.out.println("Before loop " + TestEnum.JUPITER.name());
        for (TestEnum enumy : TestEnum.values()
             ) {
            enumy.firstValue = 30;
        }

        for (TestEnum enumy : TestEnum.values()
        ) {
            enumy.returnFirstValue();
        }

    }
}
