package news.javatech.bot.client;

import io.micronaut.gcp.pubsub.annotation.PubSubClient;
import io.micronaut.gcp.pubsub.annotation.Topic;
import news.javatech.model.Comment;

@PubSubClient
public interface PubSubCommentClient extends CommentClient {

    @Topic("comments")
    void send(Comment comment);
}
