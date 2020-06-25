package org.zoxweb.server.api.provider.notification.smtp;

public class SMTPMessage
{
    public final String subject;
    public final String message;
    public SMTPMessage(String subject, String message)
    {
        this.subject = subject;
        this.message = message;
    }
}