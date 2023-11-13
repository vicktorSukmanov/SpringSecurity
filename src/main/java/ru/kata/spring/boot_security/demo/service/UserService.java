package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
    @Transactional
    public List<User> getListUser(){
        return userRepository.findAll();
    }
    @Transactional
    public void createUser(User user){
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if(userFromDB!=null){
            throw new UsernameNotFoundException("User already exists!");
        }
//        Set<Role> rols = new HashSet<>();
//        rols.add(new Role(1L,"ROLE_USER"));
//        user.setRole(rols);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    @Transactional
    public User readUser(long id){
        Optional<User> userFromDb = userRepository.findById(id);
        return userFromDb.orElse(new User());
    }
    @Transactional
    public void deleteUser(long id){
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
        throw new UsernameNotFoundException("User not found");
    }
    @Transactional
    public void updateUser(User user){
        entityManager.merge(user);
        entityManager.flush();

    }




}
