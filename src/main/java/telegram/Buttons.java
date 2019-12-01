package telegram;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.ArrayList;
import java.util.List;

class Buttons {

    static InlineKeyboardMarkup sendInlineKeyboardMessage(List<String> dataButtons) {
        InlineKeyboardMarkup inlineKM = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        for (int i = 0; i < dataButtons.size(); i++) {
            InlineKeyboardButton button = new InlineKeyboardButton()
                    .setText(dataButtons.get(i)).setCallbackData(dataButtons.get(i));
            keyboardButtonsRow.add(button);
        }
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKM.setKeyboard(rowList);
        return inlineKM;
    }

}
