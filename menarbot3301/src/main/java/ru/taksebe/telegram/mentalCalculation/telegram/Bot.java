package ru.taksebe.telegram.mentalCalculation.telegram;

import lombok.Getter;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.taksebe.telegram.mentalCalculation.Utils;
import ru.taksebe.telegram.mentalCalculation.telegram.commands.operations.*;
import ru.taksebe.telegram.mentalCalculation.telegram.commands.service.HelpCommand;
import ru.taksebe.telegram.mentalCalculation.telegram.commands.service.SettingsCommand;
import ru.taksebe.telegram.mentalCalculation.telegram.commands.service.StartCommand;
import ru.taksebe.telegram.mentalCalculation.telegram.nonCommand.NonCommand;
import ru.taksebe.telegram.mentalCalculation.telegram.nonCommand.Settings;

import java.util.HashMap;
import java.util.Map;

/**
 * Собственно, бот
 */
public final class Bot extends TelegramLongPollingCommandBot {


    private final String BOT_NAME;
    private final String BOT_TOKEN;

    @Getter
    private static final Settings defaultSettings = new Settings(1, 15,1);
    private final NonCommand nonCommand;

    /**
     * Настройки файла для разных пользователей. Ключ - уникальный id чата
     */
    @Getter
    private static Map<Long, Settings> userSettings;

    public Bot(String botName, String botToken) {
        super();

        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;


        this.nonCommand = new NonCommand();


        register(new StartCommand("start", "Старт"));

        register(new PlusCommand("plus", "Сложение"));

        register(new MinusCommand("minus", "Вычитание"));

        register(new PlusMinusCommand("plusminus", "Сложение и вычитание"));


        register(new MultiplicationCommand("multiply", "Умножение"));


        register(new DivisionCommand("divide", "Деление"));


        register(new MultiplicationDivisionCommand("multdivide", "Умножение и деление"));


        register(new AllCommand("all", "4 действия"));


        register(new HelpCommand("help","Помощь"));


        register(new SettingsCommand("settings", "Мои настройки"));


        userSettings = new HashMap<>();

    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    /**
     * Ответ на запрос, не являющийся командой
     */
    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = Utils.getUserName(msg);

        String answer = nonCommand.nonCommandExecute(chatId, userName, msg.getText());
        setAnswer(chatId, userName, answer);
    }

    /**
     * Получение настроек по id чата. Если ранее для этого чата в ходе сеанса работы бота настройки не были установлены,
     * используются настройки по умолчанию
     */
    public static Settings getUserSettings(Long chatId) {
        Map<Long, Settings> userSettings = Bot.getUserSettings();
        Settings settings = userSettings.get(chatId);
        if (settings == null) {
            return defaultSettings;
        }
        return settings;
    }

    /**
     * Отправка ответа
     */
    private void setAnswer(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {

            e.printStackTrace();
        }
    }
}