/**
 * @ClassName EmailService
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/24 17:47
 */
package cn.sonrisa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public interface EmailService {
    void sendSimpleMail(String to, String subject, String msg);
}
