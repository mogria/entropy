package org.playentropy.user;

import org.springframework.stereotype.Component;
import java.beans.PropertyEditorSupport;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class UserPropertyEditor extends PropertyEditorSupport {

    @Autowired
    private UserService service;

    @Override
    public void setAsText(String text) {
        setValue(service.findById(text));
    }

    @Override
    public String getAsText() {
        return ((User)getValue()).getId();
    }
}
