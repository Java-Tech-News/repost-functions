package news.javatech.function.moderation;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import io.micronaut.gcp.pubsub.annotation.PubSubListener;
import io.micronaut.gcp.pubsub.annotation.Subscription;
import io.micronaut.messaging.annotation.MessageHeader;
import jakarta.inject.Inject;
import news.javatech.bot.ChannelBot;
import news.javatech.model.Comment;

import java.io.IOException;

@PubSubListener
public class ModerationListener {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ModerationListener.class);

    @Inject
    private ErrorClient errorClient;
    @Inject
    private ChannelBot channelBot;

    @Subscription("comment")
    public void onMessage(Comment comment,
                          @MessageHeader("action") String action) {
        takeAction(comment, action == null ? decideAction(comment) : action);
    }

    private String decideAction(Comment comment) {
        String action = null;
        if (!comment.references().isEmpty()) {
            action = "delete";
        }
        return action;
    }

    private void takeAction(Comment comment, String action) {
        if ("delete".equals(action)) {
            channelBot.deleteMessage(comment, new Callback() {
                @Override
                public void onResponse(BaseRequest request, BaseResponse response) {
                    log.info("{} was successfully deleted.", comment);
                }

                @Override
                public void onFailure(BaseRequest request, IOException e) {
                    errorClient.report(comment, String.format("Could not delete the post. %s", e.getLocalizedMessage()));
                }
            });
        } else if (action != null) {
            final String reason = String.format("Could not find a proper handler for the action [%s].", action);
            errorClient.report(comment, reason);
        }
    }
}