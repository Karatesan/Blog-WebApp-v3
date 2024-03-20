package com.karatesan.WebAppApi.config;

import com.karatesan.WebAppApi.model.security.BlogUser;
import com.karatesan.WebAppApi.model.security.role.Privilege;
import com.karatesan.WebAppApi.model.security.role.Role;
import com.karatesan.WebAppApi.repositories.BlogUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private final BlogUserRepository blogUserRepository;

@Autowired
    public CustomUserDetailsService(BlogUserRepository blogUserRepository) {
        this.blogUserRepository = blogUserRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        BlogUser user = blogUserRepository.findByEmail(email);
        if(user == null) throw new UsernameNotFoundException("User with email: "+email+" not found.");
        return new User(email, user.getPassword(), getAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
    
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<? extends GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
    //zamienai wszystko na simplegrantedauthority
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges){
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
    return authorities;
    }

    private List<String> getPrivileges(Collection<Role> roles) {
//dostajemy liste wszystkich rol i privilegow
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();

        for(Role role : roles){
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for(Privilege p : collection){
            privileges.add(p.getName());
        }
    return privileges;

    }
}
