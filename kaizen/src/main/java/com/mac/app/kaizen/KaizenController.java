/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.app.kaizen;

import com.mac.app.document.model.Document;
import com.mac.app.kaizen.model.Mail;

import com.mac.app.kaizen.model.TKaizen;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nidura Prageeth
 */
@RestController
@CrossOrigin
@RequestMapping("/api/kaizen")
public class KaizenController {

    @Autowired
    private KaizenService kaizenService;

    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping(method = RequestMethod.GET)
    public List<TKaizen> allKaizen() {
        return kaizenService.allKaisen();
    }

    @RequestMapping(value = "/department-kaizen/{indexNo}", method = RequestMethod.GET)
    public List<TKaizen> getKaizenByDepartment(@PathVariable Integer indexNo) {
        return kaizenService.getKaizenByDepartment(indexNo);
    }

    @RequestMapping(value = "/save-kaizen", method = RequestMethod.POST)
    public TKaizen saveKaizen(@RequestBody TKaizen kaizen) {
        return kaizenService.saveKazen(kaizen);
    }

    @RequestMapping(value = "/update-kaizen", method = RequestMethod.POST)
    public TKaizen updateByManager(@RequestBody TKaizen kaizen) {
        return kaizenService.kaizenUpdateByManager(kaizen);
    }

    @RequestMapping(value = "/update-committee-kaizen", method = RequestMethod.POST)
    public TKaizen updateByCommittee(@RequestBody TKaizen kaizen) {
        return kaizenService.kaizenUpdateByCommittee(kaizen);
    }

    // send appreciation mail
    @RequestMapping(value = "/send-mail/{indexNo}", method = RequestMethod.POST)
    public void sendEmail(@RequestBody Mail mail, @PathVariable Integer indexNo) {
        kaizenService.updateKaizenByIndex(indexNo);
        System.out.println(mail.getEmail());
        System.out.println(mail.getMessage());
//    
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("kaizencommittee1@gmail.com");
            messageHelper.setTo(mail.getEmail());
//            messageHelper.setTo("niduraprageeth@gmail.com");
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getMessage());
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            System.out.println(e);
        }

    }

}
