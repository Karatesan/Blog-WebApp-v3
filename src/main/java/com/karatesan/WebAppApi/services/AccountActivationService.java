package com.karatesan.WebAppApi.services;


import com.karatesan.WebAppApi.exception.ActivateAccountException;
import com.karatesan.WebAppApi.exception.EmailSendingException;
import com.karatesan.WebAppApi.model.security.BlogUser;
import com.karatesan.WebAppApi.model.security.UserStatus;
import com.karatesan.WebAppApi.model.security.role.Role;
import com.karatesan.WebAppApi.repositories.BlogUserRepository;
import com.karatesan.WebAppApi.ulilityClassess.Token;
import com.karatesan.WebAppApi.utility.AuthenticatedUserIdProvider;
import com.karatesan.WebAppApi.utility.CacheManager;
import com.karatesan.WebAppApi.utility.TokenGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountActivationService {

    private final BlogUserRepository userRepository;
    private final TokenGenerator tokenGenerator;
    private final EmailService emailService;
    private final CacheManager cacheManager;
    private final RoleService roleService;
    private final AuthenticatedUserIdProvider authenticatedUserIdProvider;

    void sendActivationRequest(BlogUser user){

        Token activationToken = tokenGenerator.createActivationToken();
        cacheManager.save(activationToken,user.getId());
        emailService.sendVerificationEmail(user.getName(),user.getEmail(),activationToken.token());
    }

    @Transactional
    public void activateAccount(String token) {
        final Long userId = cacheManager.fetch(token, Long.class).orElseThrow( ActivateAccountException::new );
        final BlogUser user = userRepository.findById(userId)
                .orElseThrow(IllegalStateException::new);

        //checkign if user is activated and throwing exception here (below, in resendActivationLink, different approach)
        if(isUserActivated(user)){
            throw new ActivateAccountException("Account is already activated.");
        }

        user.setUserStatus(UserStatus.APPROVED);
        Role role = roleService.getUserRole();
        List<Role> roles = new ArrayList<>(List.of(role));
        user.setRoles(roles);
        userRepository.save(user);
        cacheManager.delete(token);
    }

    /**
     *
     *
     * This method sets user status to approved after successfully activating account via link sent to user's email.
     * First it checks wheter link is still active (24h timeout) after that it also checks if users account hasn't already been activated
     * If not users status is updated to Approved and his role to full user, gaining access to all users activities
     */

//for logged in user
    public void resendActivationLink() {
        Long userId = authenticatedUserIdProvider.getUserId();
        BlogUser user = userRepository.findById(userId).orElseThrow(IllegalStateException::new);
        resendActivationLinkForUser(user);
    }

    //for user that is not logged in
    public void resendActivationLink(String email) {
        BlogUser user = userRepository.findByEmail(email).orElseThrow(IllegalStateException::new);
        resendActivationLinkForUser(user);
    }

    public boolean isUserActivated(BlogUser user) {
        return user.getUserStatus().equals(UserStatus.APPROVED);
    }

    public void ensureUserIsNotActivated(BlogUser user){
        if (isUserActivated(user)) {
            throw new ActivateAccountException("Account is already activated.");
        }
    }

    public void sendVerificationEmail(BlogUser user){
        try {
            sendActivationRequest(user);
        }catch (EmailSendingException ex) {
            userRepository.delete(user);
            throw ex;
        }
    }

    public void resendActivationLinkForUser(BlogUser user){
        ensureUserIsNotActivated(user);
        sendVerificationEmail(user);
    }
}
