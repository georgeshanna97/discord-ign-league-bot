package io.github.georgeshanna97.discord_ign_bot;

import java.io.File;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

 class Setup {

    private String token;
    private String leagueAPI;
    private String battleRiteAPI;

    protected Setup() throws IOException {

        Properties prop = new Properties();
        File bot = new File("prop.properties");

        if(!bot.exists()){
            prop.setProperty("token", " your bot token goes here");
            prop.setProperty("LeagueAPI", "your riot api goes here");
            prop.setProperty("BattleRiteAPI", "your BattleRite api goes here");
            prop.store(new FileOutputStream(bot), "Bot Settings");
            System.out.printf("Modify this file");
            System.exit(1);
        }
        prop.load(new FileInputStream(bot));

       this.token = prop.getProperty("token");
       this.leagueAPI = prop.getProperty("leagueAPI");
       this.battleRiteAPI = prop.getProperty("battleRiteAP");
    }
     String getToken() {
        return token;
    }

    String getLeagueAPI(){
     return leagueAPI;
    }

    String getBattleRiteAPI(){
        return battleRiteAPI;
    }
 }