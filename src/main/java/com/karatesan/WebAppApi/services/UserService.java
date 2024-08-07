package com.karatesan.WebAppApi.services;


import com.karatesan.WebAppApi.dto.ResetPasswordRequestDto;
import com.karatesan.WebAppApi.dto.UserCreationRequestDto;
import com.karatesan.WebAppApi.dto.UserDetailDto;
import com.karatesan.WebAppApi.exception.*;
import com.karatesan.WebAppApi.model.security.BlogUser;
import com.karatesan.WebAppApi.model.security.UserStatus;
import com.karatesan.WebAppApi.model.security.role.Role;
import com.karatesan.WebAppApi.repositories.BlogUserRepository;
import com.karatesan.WebAppApi.utility.AuthenticatedUserIdProvider;
import com.karatesan.WebAppApi.utility.CacheManager;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

/**
 *  This service handles create user, reset password, activating account via email after creating it and deactivating accout

 */

public class UserService {

    private final BlogUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRevocationService tokenRevocationService;
    private final CompromisedPasswordChecker compromisedPasswordChecker;
    private final RoleService roleService;
    private final AccountActivationService accountActivationService;


    public void create(@NonNull final UserCreationRequestDto userCreationRequest) {
        final String email = userCreationRequest.getEmail();
        if(!userCreationRequest.getPassword().equals(userCreationRequest.getConfirmPassword())){
            throw new PasswordMismatchException("Password and confirm password do not match");
        }
        final boolean accountWithEmailAlreadyExists = userRepository.existsByEmail(email);
        if (accountWithEmailAlreadyExists)
            throw new AccountAlreadyExistsException("Account with provided email-id already exists");

        final String requestPassword = userCreationRequest.getPassword();
        final boolean isPasswordCompromised = compromisedPasswordChecker.check(requestPassword).isCompromised();
        if (isPasswordCompromised)
            throw new CompromisedPasswordException("The provided password is compromised and cannot be used for account creation.");

        final BlogUser user = new BlogUser();
        final String encodedPassword = passwordEncoder.encode(requestPassword);
        final Role role = roleService.getPreactivatedUserRole();

        user.setName(userCreationRequest.getName());
        user.setLastName(userCreationRequest.getLastName());
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setUserStatus(UserStatus.PENDING_APPROVAL);
        user.setRoles(List.of(role));
        user.setCreatedAt(LocalDateTime.now());//ZoneOffset.UTC)

        BlogUser savedUser = userRepository.save(user);
        accountActivationService.sendVerificationEmail(savedUser);
        }


    //update
    //delete?

    /**
     *
     * @param resetPasswordRequest
     *
     * This method checks if user with provided email exists and checks wheter provided old password matches the one stored in database.
     * After that also checks if new password is compromised.
     * If everything is fine atm we encode new password, update user's password in database and invalidate
     * access and refresh tokens forcing user to relog with new credentials.
     */
    public void resetPassword(@NonNull final ResetPasswordRequestDto resetPasswordRequest) {
        final BlogUser user = userRepository.findByEmail(resetPasswordRequest.getEmail()).orElseThrow(
                () -> new InvalidCredentialsException("No user exists with given email/current-password combination.")
        );

        final String currentEncodedPassword = user.getPassword();
        final String plainTextCurrent = resetPasswordRequest.getCurrentPassword();
        final boolean isCurrentPasswordCorrect = passwordEncoder.matches(plainTextCurrent, currentEncodedPassword);
        if (!isCurrentPasswordCorrect)
            throw new InvalidCredentialsException("No user exists with given email/current-password combination.");

        final String newPassword = resetPasswordRequest.getNewPassword();
        final boolean isNewPasswordCompromised = compromisedPasswordChecker.check(newPassword).isCompromised();
        if (isNewPasswordCompromised)
            throw new CompromisedPasswordException("New password selected is compromised and cannot be used.");

        final String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        userRepository.save(user);
        tokenRevocationService.invalidateTokensForUser();
    }

    public UserDetailDto getUserById(@NonNull Long userId) {
        final BlogUser user = userRepository.findById(userId)
                .orElseThrow(IllegalStateException::new);

        return UserDetailDto.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .userStatus(user.getUserStatus().getValue())
                .createdAt(user.getCreatedAt())
                .roles(user.getRoles())
                .build();
    }

    //TODO how to use deactivate,
    public void deactivate(@NonNull Long userId){
        final BlogUser user = userRepository.findById(userId)
                .orElseThrow(IllegalStateException::new);
        user.setUserStatus(UserStatus.DEACTIVATED);
        userRepository.save(user);
        tokenRevocationService.revokeAccessToken();
    }


}
