package news.javatech.function.moderation;

import io.micronaut.gcp.pubsub.annotation.PubSubClient;
import io.micronaut.gcp.pubsub.annotation.Topic;
import io.micronaut.messaging.annotation.MessageHeader;
import news.javatech.model.Comment;

@PubSubClient
public interface ErrorClient {

    @Topic("error-posts")
    void report(Comment post, @MessageHeader(name = "reason") String reason);
}