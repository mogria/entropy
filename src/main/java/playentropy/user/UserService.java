package org.playentropy.user;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findById(String id) {
        return userRepository.findOne(id);
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean loginUser(String username, String password) {
        User user = findByUsername(username);
        return true;
    }

    public User createUser(User user) {
        user.setPasswordHash(passwordEncoder.encode(user.getPassword()));
        user.setPassword("");
        return userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
        User user = findByUsername(username);
        if(user == null) throw new UsernameNotFoundException("no user named '" + username + "'");
        return new UserSecurityDetails(user);
    }
}
