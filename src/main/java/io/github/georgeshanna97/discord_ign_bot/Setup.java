package io.github.georgeshanna97.discord_ign_bot;

import java.io.File;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

 class Setup {

    private String token;

    protected Setup() throws IOException {

        Properties prop = new Properties();
        File bot = new File("prop.properties");

        if(!bot.exists()){
            prop.setProperty("token", " your bot token goes here");
            prop.store(new FileOutputStream(bot), "Bot Settings");
            System.out.printf("Modify this file");
            System.exit(1);
        }
        prop.load(new FileInputStream(bot));

        System.out.println(prop.get("token"));
       this.token = prop.getProperty("token");
    }
     String getToken() {
        return token;
    }
}