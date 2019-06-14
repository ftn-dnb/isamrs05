package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Async
    public void sendNotificationAsync(User user) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Aktivacioni link za prijavu na DNB-Travel.");
        mail.setText("Ovde vam se nalazi aktivacioni link: \n\n" +
                "http://localhost:8080/api/users/confirmMail/" + user.getId());
        javaMailSender.send(mail);

    }
}
