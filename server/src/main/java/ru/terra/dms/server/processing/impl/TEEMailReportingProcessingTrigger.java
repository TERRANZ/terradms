package ru.terra.dms.server.processing.impl;

import com.sun.mail.util.MailSSLSocketFactory;
import ru.terra.dms.server.processing.Processing;
import ru.terra.dms.server.processing.ProcessingTrigger;
import ru.terra.server.config.Config;
import ru.terraobjects.entity.TObject;
import ru.terraobjects.manager.ObjectsManager;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * Created by terranz on 13.10.15.
 */
@Processing("TERRAERRORS")
public class TEEMailReportingProcessingTrigger extends ProcessingTrigger {
    private String mailServer = Config.getConfig().getValue("errorreport.mail.server", null);
    private String mailPort = Config.getConfig().getValue("errorreport.mail.server.port", "465");
    private String mailUser = Config.getConfig().getValue("errorreport.mail.user", null);
    private String mailPass = Config.getConfig().getValue("errorreport.mail.pass", null);
    private String mailTo = Config.getConfig().getValue("errorreport.mail.to", "jbrss@terranz.ath.cx");
    private String mailFrom = Config.getConfig().getValue("errorreport.mail.from", "jbrss@terranz.ath.cx");

    @Override
    public void onCreate(Integer objectId) {
        if (mailServer != null && mailUser != null && mailPass != null) {
            logger.info("Sending email with error report");
            ObjectsManager<TObject> objectsManager = new ObjectsManager<>();
            Map<String, String> fields = objectsManager.getObjectFieldValues(objectId);
            String app = fields.get("PROJECT_NAME");
            String versionCode = fields.get("APP_VERSION_CODE");
            String versionName = fields.get("APP_VERSION_NAME");
            String stackTrace = fields.get("STACK_TRACE");
            StringBuilder report = new StringBuilder();
            report.append("Version: ");
            report.append(versionCode + " " + versionName);
            report.append("\n\n");
            report.append("StackTrace: ");
            report.append(stackTrace);
            sendError(app, report.toString());
        }
    }

    @Override
    public void onUpdate(Integer objectId) {

    }

    @Override
    public void onDelete(Integer objectId) {

    }

    public void sendError(String app, String error) {
        logger.info("sending error");
        try {
            Properties props = new Properties();

            MailSSLSocketFactory socketFactory = new MailSSLSocketFactory();
            socketFactory.setTrustAllHosts(true);
            props.put("mail.imaps.ssl.socketFactory", socketFactory);
            props.put("mail.transport.protocol", "smtps");
            props.put("mail.smtps.auth", "true");
            props.put("mail.smtps.ssl.checkserveridentity", "false");
            props.put("mail.smtps.ssl.trust", "*");

            Session mailSession = Session.getDefaultInstance(props);
            mailSession.setDebug(true);
            Transport transport = mailSession.getTransport();

            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject("Error report from " + app + " android client");
            message.setContent(error, "text/plain");
            message.setSentDate(new Date());
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            message.addFrom(new InternetAddress[]{new InternetAddress(mailFrom)});

            transport.connect(mailServer, Integer.parseInt(mailPort), mailUser, mailPass);

            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (Exception e) {
            logger.error("error while sending email", e);
        }
    }
}
