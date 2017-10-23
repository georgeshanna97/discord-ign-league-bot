package io.github.georgeshanna97.discord_ign_bot;


import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

import java.io.IOException;


public class MainBot {

    public static void main(String[] args) throws IOException{
        ClientBuilder build = new ClientBuilder();
        Setup setup = new Setup();
        build.withToken(setup.getToken());
        IDiscordClient client = build.login();
    }
}
