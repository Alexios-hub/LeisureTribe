package com.example.gamecenter.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


@Component
public class mailUtil {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    private String from="leisuretribe@163.com";
    public void sendSimpleMail(String to,String subject,String content){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setSubject(subject);
        message.setText(content);
        message.setTo(to);
        message.setFrom(from);
        mailSender.send(message);
    }

    public void sendHtmlMail(String to,String subject,String code){
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper=new MimeMessageHelper(message,true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            message.setSubject(subject);
            Context context = new Context();
            context.setVariable("code",code);
            String emailTemplate = templateEngine.process("mailHtml", context);
            messageHelper.setText(emailTemplate,true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public void sendAttachmentsMail(String to,String subject,String content,String filePath){
        MimeMessage message=mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);

            helper.setSubject(subject);
            helper.setText(content,true);
            FileSystemResource file =new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName,file);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
