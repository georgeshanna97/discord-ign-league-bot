package io.github.georgeshanna97.discord_ign_bot;

import net.rithms.riot.api.RiotApiException;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.io.IOException;

public class EventListener {
    public String prefix = new String("~");

    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent e) throws IOException,RiotApiException{
        if(e.getMessage().getContent().toLowerCase().startsWith(prefix)){
            CommandProcessor.processCommand(e.getMessage(), prefix);
        }
    }
}
