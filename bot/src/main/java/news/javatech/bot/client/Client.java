package news.javatech.bot.client;

import news.javatech.model.Message;

public interface Client<T extends Message> {

    void send(T message);

    void send(T message, String action);
}