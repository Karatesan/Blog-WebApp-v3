package com.karatesan.WebAppApi.services;


import com.karatesan.WebAppApi.repositories.BlogUserRepository;
import com.karatesan.WebAppApi.utility.AuthenticatedUserIdProvider;
import com.karatesan.WebAppApi.utility.CacheManager;
import com.karatesan.WebAppApi.utility.TokenGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountActivationServiceTest {


    @Mock
    BlogUserRepository userRepository;
    @Mock
    TokenGenerator tokenGenerator;
    @Mock
    EmailService emailService;
    @Mock
    CacheManager cacheManager;
    @Mock
    RoleService roleService;
    @Mock
    AuthenticatedUserIdProvider authenticatedUserIdProvider;

    @InjectMocks
    AccountActivationService accountActivationService;


}
