package com.karatesan.WebAppApi.utility;

import com.karatesan.WebAppApi.model.security.BlogUser;
import com.karatesan.WebAppApi.model.security.UserStatus;
import com.karatesan.WebAppApi.testUtilities.WithMockCustomUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
//z tego co rozumiem to SpringBootTest laduje caly kontekst- cala aplikacje, wiec mamy dostep do wszystkich rzeczy
@SpringBootTest
public class AuthenticatedUserIdProviderTest {

    @Autowired
    AuthenticatedUserIdProvider authenticatedUserIdProvider;

    @Test
    @WithMockCustomUser(userId = 12L)
    public void getUserId_shouldReturnAuthenticatedUserId_whenUserIsLogged(){

        long userId = 12L;

        long userIdFromContext = authenticatedUserIdProvider.getUserId();

        assertThat(userIdFromContext).isEqualTo(userId);
    }

    @Test
    public void getUserId_shouldThrowError_whenNoUserIsLogged(){

        SecurityContextHolder.getContext().setAuthentication(null);

        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() ->authenticatedUserIdProvider.getUserId());
    }


}
