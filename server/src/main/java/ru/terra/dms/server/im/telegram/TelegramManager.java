package ru.terra.dms.server.im.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import ru.terra.server.config.Config;

public class TelegramManager {
    private TelegramBotsApi botsApi;
    private TelegramLongPollingBot botInfo;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Config c = Config.getConfig();

    private static TelegramManager instance = new TelegramManager();

    private TelegramManager() {
        if (!c.getValue("tg.botname", "").isEmpty()) {
            String botName = c.getValue("tg.botname", "");
            String token = c.getValue("tg.token", "");
            ApiContextInitializer.init();
            botsApi = new TelegramBotsApi();
            botInfo = new TelegramLongPollingBot() {
                @Override
                public void onUpdateReceived(Update update) {
                    if (update.hasMessage() && update.getMessage().hasText()) {
                        logger.info("Message from " + update.getMessage().getChatId().toString() + " : " + update.getMessage().getText());
                    }
                }

                @Override
                public String getBotUsername() {
                    return botName;
                }

                @Override
                public String getBotToken() {
                    return token;
                }
            };
            try {
                botsApi.registerBot(botInfo);
            } catch (TelegramApiRequestException e) {
                logger.error("Error with telegram interface", e);
            }
        }
    }

    public void sendMessage(String msg) {
        try {
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId("55227752")
                    .setText(msg);
            botInfo.sendMessage(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public boolean isOk() {
        return !c.getValue("tg.botname", "").isEmpty();
    }

    public static TelegramManager getInstance() {
        return instance;
    }
}
