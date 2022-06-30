package ru.taksebe.telegram.mentalCalculation.telegram.commands.operations;


import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.taksebe.telegram.mentalCalculation.Utils;
import ru.taksebe.telegram.mentalCalculation.enums.OperationEnum;

/**
 * Команда получение файла с заданиями на сложение и вычитание
 */
public class PlusMinusCommand extends OperationCommand {


    public PlusMinusCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);


        sendAnswer(absSender, chat.getId(), OperationEnum.getPlusMinus(), this.getDescription(),
                this.getCommandIdentifier(), userName);

    }
}