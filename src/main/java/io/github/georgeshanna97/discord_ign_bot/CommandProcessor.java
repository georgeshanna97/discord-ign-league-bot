package io.github.georgeshanna97.discord_ign_bot;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IGuild;
import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;
import java.io.IOException;

public class CommandProcessor{

    public static String getApiKey() throws IOException{
        Setup setup = new Setup();
        return setup.getAPI();
    }

    public static void processCommand(IMessage message, String prefix) throws IOException, RiotApiException{
        @SuppressWarnings("unused")
        IUser sender = message.getAuthor();
        ApiConfig config = new ApiConfig().setKey(getApiKey());
        RiotApi api = new RiotApi(config);

        IChannel channel = message.getChannel();
        try {
            String[] command = message.getContent().toLowerCase().replaceFirst(prefix, "").split(" ");
            Summoner summoner = api.getSummonerByName(Platform.NA, command[0]);
            Long id = summoner.getAccountId();
            channel.sendMessage(id.toString());
        }catch(RiotApiException r) {
            if(r.DATA_NOT_FOUND == 404) {
            }
        }

    }
}
