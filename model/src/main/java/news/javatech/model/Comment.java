package news.javatech.model;

import java.util.Set;

public class Comment implements Message {
    private int id;
    private String body;
    private Set<String> references;
    private Long creatorId;
    private Long chatId;

    public Comment() {
    }

    public Comment(int id, String body, Set<String> references, Long creatorId, Long chatId) {
        this.id = id;
        this.body = body;
        this.references = references;
        this.creatorId = creatorId;
        this.chatId = chatId;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public String body() {
        return body;
    }

    @Override
    public Set<String> references() {
        return references;
    }

    @Override
    public Long creatorId() {
        return creatorId;
    }

    @Override
    public Long chatId() {
        return chatId;
    }
}
