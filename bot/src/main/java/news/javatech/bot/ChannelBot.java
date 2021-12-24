package news.javatech.bot;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.MessageEntity;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import io.micronaut.context.annotation.Value;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import news.javatech.bot.client.CommentClient;
import news.javatech.model.Comment;
import news.javatech.model.Post;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public final class ChannelBot extends TelegramBot {

    private final CommentClient commentClient;

    public ChannelBot(@Value("${telegram.bot.token}") String botToken,
                      CommentClient commentClient) {
        super(botToken);
        this.commentClient = commentClient;
    }

    @PostConstruct
    void init() {
        setUpdatesListener(updates -> {
            updates.parallelStream()
                    .map(update -> update.message())
                    .filter(message -> message != null)
                    .filter(message -> message.chat() != null)
                    .filter(message -> message.from() != null && !message.from().isBot())
                    .map(message -> convertMessage(message))
                    .forEach(comment -> commentClient.send(comment));
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private Comment convertMessage(Message message) {
        final Set<String> references = Arrays.stream(message.entities())
                .filter(messageEntity -> MessageEntity.Type.url.equals(messageEntity.type()))
                .map(messageEntity -> message.text().substring(messageEntity.offset(), messageEntity.length()))
                .collect(Collectors.toSet());
        return new Comment(message.messageId(), message.text(), references, message.from().id(), message.chat().id());
    }

    public void deleteMessage(news.javatech.model.Message message, Callback<DeleteMessage, BaseResponse> callback) {
        super.execute(new DeleteMessage(message.chatId(), message.id()), callback);
    }
}
