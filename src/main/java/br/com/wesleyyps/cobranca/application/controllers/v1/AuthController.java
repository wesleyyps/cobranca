package br.com.wesleyyps.cobranca.application.controllers.v1;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wesleyyps.cobranca.domain.entity.UserEntity;
import br.com.wesleyyps.cobranca.domain.exceptions.CobrancaException;
import br.com.wesleyyps.cobranca.domain.repositories.UserRepository;
import br.com.wesleyyps.cobranca.application.dtos.requests.AuthRequest;
import br.com.wesleyyps.cobranca.application.dtos.responses.AuthResponse;
import br.com.wesleyyps.cobranca.application.dtos.responses.ErrorResponse;
import br.com.wesleyyps.cobranca.application.dtos.responses.ProfileResponse;
import br.com.wesleyyps.cobranca.infrastructure.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/auth")
@Tag(
    name = "001. Autenticação",
    description = "Endpoints para realizar login e outras ações sobre usuários"
)
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    protected AuthenticationManager authenticationManager;
    @Autowired
    protected ModelMapper modelMapper;
    @Autowired
    protected JwtTokenProvider tokenProvider;
    @Autowired
    protected UserRepository userRepository;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Login")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ok",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthResponse.class)
                )
            }
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Credenciais inválidas",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            }
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno do servidor",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            }
        )
    })
    public ResponseEntity<AuthResponse> authenticateUser(
        @RequestBody
        @Valid 
        AuthRequest authRequest
    ) throws CobrancaException {
        String email = authRequest.email();
        try {
            Authentication authentication = authenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        email, 
                        authRequest.password()
                    )
                );

            SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

            UserEntity user = this.userRepository
                .findByEmail(email)
                .orElseThrow(() -> new CobrancaException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenciais inválidas"
                ));

            String token = this.tokenProvider
                .generateToken(authentication);

            ProfileResponse profile = this.modelMapper.map(
                user,
                ProfileResponse.class
            );

            return ResponseEntity.ok(
                new AuthResponse(
                    token,
                    "Bearer",
                    profile
                )
            );
        } catch (BadCredentialsException e) {
            throw new CobrancaException(
                HttpStatus.UNAUTHORIZED,
                "Credenciais inválidas",
                e
            );
        }
    }    
}
