package com.epam.ap.service;

import org.junit.Test;
import sun.net.www.protocol.mailto.MailToURLConnection;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MailerTest {

    @Test
    public void sendEmail(){
        try {
            sendMail("ondre.no-ip.org", "Snipe94@mail.ru", "Hello", "have a good day", new String[]{"asd","asd"});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void sendMail(String from, String to, String subject, String body, String[] headers) throws IOException {
        System.setProperty("mail.host", "localhost");

        URL u = new URL("mailto:"+to);
        MailToURLConnection con = (MailToURLConnection)u.openConnection();
        OutputStream os = con.getOutputStream();
        OutputStreamWriter w = new OutputStreamWriter(os);

        DateFormat df = new SimpleDateFormat("E, d MMM yyyy H:mm:ss Z");
        Date d = new Date();
        String dt = df.format(d);
        String mid = d.getTime()+from.substring(from.indexOf('@'));

        w.append("Subject: "+subject+"\r\n");
        w.append("Date: " +dt+ "\r\n");
        w.append("Message-ID: <"+mid+ ">\r\n");
        w.append("From: "+from+"\r\n");
        w.append("To: <"+to+">\r\n");
        if(headers!=null) {
            for(String h: headers)
                w.append(h).append("\r\n");
        }
        w.append("\r\n");

        w.append(body.replace("\n", "\r\n"));
        w.flush();
        w.close();
        os.close();
        con.close();
    }
}
