package io.github.georgeshanna97.discord_ign_bot;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import net.rithms.riot.api.endpoints.league.dto.LeaguePosition;
import org.json.JSONArray;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IGuild;
import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.league.dto.LeagueList;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;
import com.google.gson.*;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.RateLimitException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.*;
import java.util.*;


import java.io.IOException;

public class CommandProcessor {

    private static Pattern command = Pattern.compile("^\\s*(~)([a-zA-Z0-9]*)(,solo|,flex)?\\s*$");
    private static boolean solo_or_flex = true;
    private static File image;
    private static MessageBuilder response;
    private static String spacing;


    public static String getApiKey() throws IOException {
        Setup setup = new Setup();
        return setup.getLeagueAPI();
    }

    public static void processCommand(IMessage message, String prefix, IDiscordClient client) throws IOException, RiotApiException, RateLimitException {
        @SuppressWarnings("unused")
        IUser sender = message.getAuthor();
        response = new MessageBuilder(client);
        ApiConfig config = new ApiConfig().setKey(getApiKey());
        RiotApi api = new RiotApi(config);
        String readM = message.toString();
        Matcher m = command.matcher(readM);
        IChannel channel = message.getChannel();
        response.withChannel(channel);
        try {
            if (m.matches()) {
                Summoner summoner = api.getSummonerByName(Platform.NA, m.group(2));
                Set<LeaguePosition> leagueList = api.getLeaguePositionsBySummonerId(Platform.NA, summoner.getId());
                if(m.group(3) != null && m.group(3).equals(",flex")) solo_or_flex = false;
                if (leagueList.isEmpty()) leagueList = null;
                if (leagueList != null) {
                    for (LeaguePosition league : leagueList) {
                        String queue = league.getQueueType();
                        if (queue.equals("RANKED_SOLO_5x5") && solo_or_flex) {
                            findRankPNG(league);
                            response.withFile(image);
                            response.withContent(sender.mention() + "\n" +
                                    "**" + spacing + "" + league.getTier() + " " + league.getRank() + "\n" +
                                    "\t\t\t\t\t\t" + league.getLeaguePoints() + " LP \n\t\t\t\t\tLEVEL: " +
                                    "" + summoner.getSummonerLevel() + "**");
                            response.build();

                        }else if(queue.equals("RANKED_FLEX_SR") && !solo_or_flex){
                            findRankPNG(league);
                            response.withFile(image);
                            response.withContent(""+ sender.mention() + "\n" +
                                    "**" + spacing + "" + league.getTier() + " " + league.getRank() + "\n" +
                                    "\t\t\t\t\t\t" + league.getLeaguePoints() + " LP \n\t\t\t\t\tLEVEL: " +
                                    "" + summoner.getSummonerLevel() + "**");
                            response.build();
                        }
                    }
                } else {
                    image = new File("C:\\Users\\Georges\\DiscordBots\\discordignbot\\LoLRankPng\\unranked.png");
                    response.withFile(image);
                    response.withContent(sender.mention() +"\n" +
                            "**\t\t\t\t\t\tLEVEL: " + summoner.getSummonerLevel() + "**");
                    response.build();
                }
            }
        } catch (RiotApiException r) {
            if (r.getErrorCode() == 404) {
                channel.sendMessage(r.getMessage());
                channel.sendMessage("NA, Account does not exist");
            } else if (r.getErrorCode() == 403) {
                channel.sendMessage("API key expired");
            }
        } catch (RateLimitException r) {
            throw r;
        }

    }

    public static void findRankPNG(LeaguePosition league) {
        if (league.getTier().equals("CHALLENGER")) {
            image = new File("C:\\Users\\Georges\\DiscordBots\\discordignbot\\LoLRankPng\\challenger_1.png");
            spacing = "\t\t\t\t";
        } else if (league.getTier().equals("MASTER")) {
            image = new File("C:\\Users\\Georges\\DiscordBots\\discordignbot\\LoLRankPng\\master_1.png");
            spacing = "\t\t\t\t\t";
        } else if (league.getTier().equals("DIAMOND")) {
            image = new File("C:\\Users\\Georges\\DiscordBots\\discordignbot\\LoLRankPng\\diamond_1.png");
            spacing = "\t\t\t\t";
        } else if (league.getTier().equals("PLATINUM")) {
            image = new File("C:\\Users\\Georges\\DiscordBots\\discordignbot\\LoLRankPng\\platinum_1.png");
            spacing = "\t\t\t\t";
        } else if (league.getTier().equals("GOLD")) {
            image = new File("C:\\Users\\Georges\\DiscordBots\\discordignbot\\LoLRankPng\\gold_1.png");
            spacing = "\t\t\t\t\t";
        } else if (league.getTier().equals("SILVER")) {
            image = new File("C:\\Users\\Georges\\DiscordBots\\discordignbot\\LoLRankPng\\silver_1.png");
            spacing = "\t\t\t\t\t";
        } else {
            image = new File("C:\\Users\\Georges\\DiscordBots\\discordignbot\\LoLRankPng\\bronze_1.png");
            spacing = "\t\t\t\t\t";
        }
    }
}
