package org.playentropy.user;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import java.util.Collection;
import org.playentropy.player.Player;
import org.playentropy.circuit.BoardService;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private BoardService boardService;


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

        user.setPlayer(new Player());
        boardService.createBoardForUser(user);

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
