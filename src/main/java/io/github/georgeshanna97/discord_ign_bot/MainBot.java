package io.github.georgeshanna97.discord_ign_bot;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;
import java.util.Scanner;
import java.lang.String;



public class MainBot {

    public static IDiscordClient client;

    public MainBot() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("enter your bot token: ");
        String tokenId = scanner.nextLine();
        client = createClient( tokenId, true);
    }

    public static void main(String[] args){
       MainBot bot = new MainBot();
        System.out.println(client.getApplicationClientID());
    }

    public static IDiscordClient createClient(String token, boolean login) { // Returns a new instance of the Discord client
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder
        try {
            if (login) {
                return clientBuilder.login(); // Creates the client instance and logs the client in
            } else {
                return clientBuilder.build(); // Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
            }
        } catch (DiscordException e) { // This is thrown if there was a problem building the client
            e.printStackTrace();
            return null;
        }
    }
}