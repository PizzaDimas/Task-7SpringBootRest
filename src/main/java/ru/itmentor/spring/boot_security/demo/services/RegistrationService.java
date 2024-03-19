package ru.itmentor.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.models.Role;
import ru.itmentor.spring.boot_security.demo.models.User;
import ru.itmentor.spring.boot_security.demo.repositories.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repositories.UserRepository;

import java.util.Optional;

@Service
public class RegistrationService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public RegistrationService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void register(User user){
        Optional<Role> role = roleRepository.findById(1L);
        user.getRoles().add(role.get());
        userRepository.save(user);
    }
}
