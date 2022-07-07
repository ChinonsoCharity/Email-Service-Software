package com.tech9ners.emailservicesoftware.services;

import com.tech9ners.emailservicesoftware.data.models.MailBox;
import com.tech9ners.emailservicesoftware.data.models.MailType;
import com.tech9ners.emailservicesoftware.data.repositories.MailBoxRepository;
import com.tech9ners.emailservicesoftware.exceptions.UserAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailBoxServiceImpl implements MailBoxService{
    @Autowired
    private MailBoxRepository mailBoxRepository;

    @Override
    public MailBox createMailBox( String username,MailType type) {
        MailBox newMailBox = new MailBox(username,type);
        MailBox saveMailBox = mailBoxRepository.save(newMailBox);

        return saveMailBox;
    }

    @Override
    public void deleteMailbox(String id) throws UserAccountException {
        MailBox selectedMailBox = mailBoxRepository.findByUserID(id);
        if (!(mailBoxRepository.existsByUserID(id))) throw new UserAccountException("MailBox not found");
         mailBoxRepository.delete(selectedMailBox);
    }

    @Override
    public MailBox findMailBox(String username, MailType mailType) {
        MailBox theMailBox = mailBoxRepository.findMailBoxByMailNameAndAndUsername(username,mailType);
        return theMailBox;
    }

}
