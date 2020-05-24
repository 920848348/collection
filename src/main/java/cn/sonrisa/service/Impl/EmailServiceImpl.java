/**
 * @ClassName EmailServiceImpl
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/24 19:01
 */
package cn.sonrisa.service.Impl;

import cn.sonrisa.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMail(String to, String subject, String msg){
        try {
            MimeMessage s = mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(s, true);
            message.setFrom(from);

            message.setTo(to);
            message.setSubject(subject);
            message.setText(msg,true);

            mailSender.send(s);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
