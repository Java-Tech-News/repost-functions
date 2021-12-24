package news.javatech.bot;

import io.micronaut.runtime.Micronaut;

// TODO: https://cloud.google.com/docs/authentication/production
public class StandaloneBot {

    public static void main(String[] args) throws Exception {
        Micronaut.build(args)
                .eagerInitSingletons(true)
                .mainClass(StandaloneBot.class)
                .start();
    }
}