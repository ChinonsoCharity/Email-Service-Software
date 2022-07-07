package com.tech9ners.emailservicesoftware.services;

import com.tech9ners.emailservicesoftware.data.models.MailBox;
import com.tech9ners.emailservicesoftware.data.models.MailType;
import com.tech9ners.emailservicesoftware.exceptions.UserAccountException;

public interface MailBoxService {
    MailBox createMailBox( String username, MailType type);
    void deleteMailbox(String id) throws UserAccountException;
    MailBox findMailBox(String username,MailType mailType);

}
