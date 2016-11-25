package ru.terra.dms.server.jabber;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.terra.server.config.Config;

/**
 * Created by Vadim_Korostelev on 11/25/2016.
 */
public class JabberManager {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Config c = Config.getConfig();
    private static JabberManager instance = new JabberManager();
    private XMPPConnection connection;

    private JabberManager() {
    }

    public static JabberManager getInstance() {
        return instance;
    }

    public boolean isOk() {
        return connection.isConnected();
    }

    public void start() {
        try {
            if (!c.getValue("jabber.server", "").isEmpty()) {
                ConnectionConfiguration config = new ConnectionConfiguration(c.getValue("jabber.server", ""), Integer.parseInt(c.getValue("jabber.port", "5222")));
                connection = new XMPPConnection(config);
                connection.connect();
                connection.login(c.getValue("jabber.user", ""), c.getValue("jabber.pass", ""));
                PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
                connection.addPacketListener(new JabberPacketListener(), filter);
            } else {
                logger.warn("Unable to start jabber connection - server url is empty");
            }
        } catch (XMPPException ex) {
            logger.error("Exception in jabber service", ex);
        }
    }

    private class JabberPacketListener implements PacketListener {
        @Override
        public void processPacket(Packet packet) {
            Message message = (Message) packet;
            if (message.getBody() != null) {
                String fromName = message.getFrom();
                logger.info("Message from " + fromName + " : " + message.getBody());
            }
        }
    }

    public void sendMessage(String text) {
        Message msg = new Message();
        msg.setTo(c.getValue("jabber.to", ""));
        msg.setBody(text);
        msg.setType(Message.Type.chat);
        connection.sendPacket(msg);
    }
}
