package ru.itmentor.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.itmentor.spring.boot_security.demo.models.Role;
import ru.itmentor.spring.boot_security.demo.models.User;
import ru.itmentor.spring.boot_security.demo.repositories.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                             .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Transactional
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void add(User user) {
        userRepository.save(user);
    }

    @Transactional
    public User addRest(User user) {
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role existingRole = roleRepository.findByName(role.getName());
            if (existingRole != null) {
                roles.add(existingRole);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "role not found");
            }
        }
        user.setRoles(roles);
        User userCreated = new User(user.getUsername(), user.getPassword(), user.getMail(), user.getRoles());
        userRepository.save(userCreated);
        return userCreated;
    }

    @Transactional
    public User updateRest(User user) {
        User updateUser = userRepository.findById(user.getId())
                                        .orElseThrow(() -> new UsernameNotFoundException("user not found" + user.getUsername()));
        updateUser.setUsername(user.getUsername());
        updateUser.setPassword(user.getPassword());
        updateUser.setMail(user.getMail());
        List<Role> roles = user.getRoles()
                               .stream()
                               .map(role -> roleRepository.findByName(role.getName()))
                               .collect(Collectors.toList());
        updateUser.setRoles(roles);
        userRepository.save(updateUser);
        return updateUser;
    }

    @Transactional
    public void update(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void deleteById(User user) {
        userRepository.delete(user);
    }
}
