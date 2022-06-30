package ru.taksebe.telegram.mentalCalculation.telegram.commands.operations;


import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.taksebe.telegram.mentalCalculation.calculation.ArithmeticService;
import ru.taksebe.telegram.mentalCalculation.calculation.Calculator;
import ru.taksebe.telegram.mentalCalculation.enums.OperationEnum;
import ru.taksebe.telegram.mentalCalculation.fileProcessor.WordFileProcessorImpl;
import ru.taksebe.telegram.mentalCalculation.telegram.Bot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Суперкласс для команд создания заданий с различными операциями
 */
abstract class OperationCommand extends BotCommand {

    private ArithmeticService service;

    OperationCommand(String identifier, String description) {
        super(identifier, description);
        this.service = new ArithmeticService(new WordFileProcessorImpl(), new Calculator());
    }

    /**
     * Отправка ответа пользователю
     */
    void sendAnswer(AbsSender absSender, Long chatId, List<OperationEnum> operations, String description,
                    String commandName, String userName) {
        try {
            absSender.execute(createDocument(chatId, operations, description));
        } catch (IOException | RuntimeException e) {

            e.printStackTrace();
        } catch (TelegramApiException e) {

            e.printStackTrace();
        }
    }

    /**
     * Создание документа для отправки пользователю
     */
    private SendDocument createDocument(Long chatId, List<OperationEnum> operations, String fileName) throws IOException {
        FileInputStream stream = service.getFile(operations, Bot.getUserSettings(chatId));
        SendDocument document = new SendDocument();
        document.setChatId(chatId.toString());
        document.setDocument(new InputFile(stream, String.format("%s.docx", fileName)));
        return document;
    }

    /**
     * Отправка пользователю сообщения об ошибке
     */
    private void sendError(AbsSender absSender, Long chatId, String commandName, String userName) {
        try {
            absSender.execute(new SendMessage(chatId.toString(), "Похоже, я сломался. Попробуйте позже"));
        } catch (TelegramApiException e) {

            e.printStackTrace();
        }
    }
}