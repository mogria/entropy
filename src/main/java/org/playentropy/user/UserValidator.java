package org.playentropy.user;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "username", "user.username.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "email", "user.email.empty");

        User user = (User)obj;
        Pattern usernamePattern = Pattern.compile("\\A[A-Za-z]{3,20}\\Z");
        Matcher usernameMatcher = usernamePattern.matcher(user.getUsername());
        if(!usernameMatcher.matches()) {
            e.rejectValue("username", "user.username.invalid");
        }

        Pattern emailPattern = Pattern.compile("\\A([A-Za-z0-9_\\-\\.\\+])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,6})\\Z");
        Matcher emailMatcher = emailPattern.matcher(user.getEmail());

        if(!emailMatcher.matches()) {
            e.rejectValue("email", "user.email.invalid");
        }

        if(user.getPassword() != null) {
            if(user.getPassword().length() < 8) {
                e.rejectValue("password", "user.password.tooweak");
            }

            if(!user.getPassword().equals(user.getPasswordConfirmation())) {
                e.rejectValue("passwordConfirmation", "user.passwordConfirmation.doesntmatch");
            }
        }

        if(userRepository.findByUsernameIgnoreCase(user.getUsername()) != null) {
            e.rejectValue("username", "user.username.notunique");
        }

        if(userRepository.findByEmailIgnoreCase(user.getEmail()) != null) {
            e.rejectValue("email", "user.email.notunique");
        }
    }
}
