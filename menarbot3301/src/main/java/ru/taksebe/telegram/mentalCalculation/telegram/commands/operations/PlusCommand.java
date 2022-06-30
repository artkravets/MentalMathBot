package ru.taksebe.telegram.mentalCalculation.telegram.commands.operations;


import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.taksebe.telegram.mentalCalculation.Utils;
import ru.taksebe.telegram.mentalCalculation.enums.OperationEnum;

import java.util.Collections;

/**
 * Команда получение файла с заданиями на сложение
 */
public class PlusCommand extends OperationCommand {


    public PlusCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);


        sendAnswer(absSender, chat.getId(), Collections.singletonList(OperationEnum.ADDITION), this.getDescription(),
                this.getCommandIdentifier(), userName);

    }
}