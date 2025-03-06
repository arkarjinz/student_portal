package org.example.studentprotal.security;

import org.example.studentprotal.dao.StudentDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final StudentDao studentDao;

    public CustomUserDetailsService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return studentDao.findByUsername(username)
                .map(SecurityStudent::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
