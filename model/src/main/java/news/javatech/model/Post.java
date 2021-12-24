package news.javatech.model;

import java.util.Set;

public class Post implements Message {
    private int id;
    private String title;
    private String body;
    private String imagePath;
    private Set<String> references;
    private Set<String> tags;
    private Long creatorId;
    private Long chatId;

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
