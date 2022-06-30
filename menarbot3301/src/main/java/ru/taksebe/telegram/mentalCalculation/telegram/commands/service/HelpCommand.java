package ru.taksebe.telegram.mentalCalculation.telegram.commands.service;


import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.taksebe.telegram.mentalCalculation.Utils;

/**
 * Команда "Помощь"
 */
public class HelpCommand extends ServiceCommand {


    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);


        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Я бот ментальной арифметики\n\n" +
                        "Я сгенерирую word-файл с заданиями, чтобы Вам не пришлось искать или придумывать их. " +
                        "❗*Список команд*\n" +
                        "/plus - сложение\n" +
                        "/minus - вычитание\n" +
                        "/plusminus - сложение и вычитание\n" +
                        "/multiply - умножение\n" +
                        "/divide - деление\n" +
                        "/multdivide - умножение и деление\n" +
                        "/all - все четыре арифметических действия\n" +
                        "/settings - просмотреть текущие настройки\n" +
                        "/help - помощь\n\n" +
                        "По умолчанию я сформирую *1 страницу* заданий с использованием чисел *от 1 до 15*. Если Вы " +
                        "хотите изменить эти параметры, введите через пробел или запятую 3 числа - минимальное число " +
                        "для использования в заданиях, максимальное число и количество страниц в файле (не более 10)\n" +
                        "\uD83D\uDC49 Например, 3,15,6 или 4 17 3\n\n");

    }
}