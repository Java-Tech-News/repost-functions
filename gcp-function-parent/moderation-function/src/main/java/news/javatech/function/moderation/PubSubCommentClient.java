package news.javatech.function.moderation;

import io.micronaut.gcp.pubsub.annotation.PubSubClient;
import io.micronaut.gcp.pubsub.annotation.Topic;
import news.javatech.bot.client.CommentClient;
import news.javatech.model.Comment;

@PubSubClient
public interface PubSubCommentClient extends CommentClient {

    @Topic("comments")
    void send(Comment comment);

    @Topic("comments")
    void send(Comment comment, String action);
}
