
package com.gabriela.Pojisteni.service;

import com.gabriela.Pojisteni.security.MyUserDetails;
import com.gabriela.Pojisteni.model.User;
import com.gabriela.Pojisteni.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    // Potřebné pro autentizaci uživatele (AuthenticationProvider) při loginu
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Uživatel nebyl nalezen.");
        }
        return new MyUserDetails(user);
    }

}
