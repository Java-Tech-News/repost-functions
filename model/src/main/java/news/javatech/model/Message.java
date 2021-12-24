package news.javatech.model;

import java.util.Set;

public interface Message {
    int id();
    String body();
    Set<String> references();
    Long creatorId();
    Long chatId();
}
