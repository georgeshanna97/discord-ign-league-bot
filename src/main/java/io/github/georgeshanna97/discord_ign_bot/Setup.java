package io.github.georgeshanna97.discord_ign_bot;

import java.io.File;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

 class Setup {

    private String token;
    private String keyAPI;

    protected Setup() throws IOException {

        Properties prop = new Properties();
        File bot = new File("prop.properties");

        if(!bot.exists()){
            prop.setProperty("token", " your bot token goes here");
            prop.setProperty("API", "your riot api goes here");
            prop.store(new FileOutputStream(bot), "Bot Settings");
            System.out.printf("Modify this file");
            System.exit(1);
        }
        prop.load(new FileInputStream(bot));

       this.token = prop.getProperty("token");
       this.keyAPI = prop.getProperty("API");
    }
     String getToken() {
        return token;
    }

    String getAPI(){
     return keyAPI;
    }
 }