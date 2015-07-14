package org.playentropy.user;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import java.util.Collection;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    @Autowired
    public UserService(UserRepository userRepository, UserValidator userValidator, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
    }

    public User findById(String id) {
        return userRepository.findOne(id);
    }

    public Collection<User> getAll() {
        return userRepository.findAll();
    }

    public User createUser(User user, BindingResult errors) {
        userValidator.validate(user, errors);

        user.setPasswordHash(passwordEncoder.encode(user.getPassword()));
        user.setPassword(null); // clear the password, just for good measure
        user.setPasswordConfirmation(null);

        if(errors.hasErrors()) return null;
        return userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
        User user = userRepository.findByUsernameIgnoreCase(username);
        if(user == null) throw new UsernameNotFoundException("no user named '" + username + "'");
        return new UserSecurityDetails(user);
    }
}
