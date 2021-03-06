package ftn.dnb.dnbtravel.controller;

import ftn.dnb.dnbtravel.common.DeviceProvider;
import ftn.dnb.dnbtravel.model.User;
import ftn.dnb.dnbtravel.model.UserTokenState;
import ftn.dnb.dnbtravel.security.TokenUtils;
import ftn.dnb.dnbtravel.security.auth.JwtAuthenticationRequest;
import ftn.dnb.dnbtravel.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8000")
public class AuthenticationController {
    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private DeviceProvider deviceProvider;

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                       HttpServletResponse response) throws AuthenticationException {


        Authentication authentication = null;
        try {
                    authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        }
        catch (AuthenticationException a) {
            return new ResponseEntity<>("User authentication fail",HttpStatus.UNAUTHORIZED);
        }

        // Ubaci username + password u kontext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();
        String role = user.getAuthorityList().get(0).getAuthority();

        // Vrati token kao odgovor na uspesno autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn,role));
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request) {
        String token = tokenUtils.getToken(request);
        String username = this.tokenUtils.getUsernameFromToken(token);
        User user = (User) this.userDetailsService.loadUserByUsername(username);

        if (this.tokenUtils.canTokenBeRefreshed(token, new Date(user.getLastPasswordResetDate().getTime()))) {
            String refreshedToken = tokenUtils.generateToken(username);
            int expiresIn = tokenUtils.getExpiredIn();
            String role = user.getAuthorities().toString();
            return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn,role));
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.badRequest().body(userTokenState);
        }
    }

    @PostMapping(value = "/change-password")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasRole('AIRLINE_ADMIN') or hasRole('RAC_ADMIN') or hasRole('HOTEL_ADMIN') or hasRole('USER')")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return ResponseEntity.accepted().body(result);
    }

    static class PasswordChanger {
        public String oldPassword;
        public String newPassword;
    }
}
