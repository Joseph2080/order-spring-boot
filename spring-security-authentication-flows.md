# Authentication Flows in Spring Security

## 1. Form Login Authentication
```java
@Configuration
@EnableWebSecurity
public class FormLoginSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .formLogin()
            .loginPage("/custom-login")
            .loginProcessingUrl("/authenticate")
            .defaultSuccessUrl("/dashboard")
            .failureUrl("/login?error=true")
            .and()
            .authorizeRequests()
            .antMatchers("/login", "/public/**").permitAll()
            .anyRequest().authenticated();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
            .withUser("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
```

## 2. JWT Authentication
```java
@Configuration
@EnableWebSecurity
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/auth/login").permitAll()
            .anyRequest().authenticated();
  }

  @Bean
  public JwtTokenFilter jwtTokenFilter() {
    return new JwtTokenFilter(jwtTokenProvider);
  }
}

// JWT Token Provider
@Component
public class JwtTokenProvider {
  public String createToken(String username, List<String> roles) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("roles", roles);

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser()
              .setSigningKey(secretKey)
              .parseClaimsJws(token);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (JwtException | IllegalArgumentException e) {
      throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
    }
  }
}
```

## 3. OAuth 2.0 Authentication
```java
@Configuration
@EnableWebSecurity
public class OAuth2SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .authorizeRequests()
            .antMatchers("/", "/error", "/webjars/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2Login()
            .defaultSuccessUrl("/dashboard")
            .failureUrl("/login?error=true")
            .and()
            .logout()
            .logoutSuccessUrl("/").permitAll();
  }

  @Bean
  public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
    return new CustomOAuth2UserService();
  }
}

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User user = super.loadUser(userRequest);
    // Custom logic to process OAuth2 user
    return new CustomOAuth2User(user);
  }
}
```

## 4. Basic Authentication
```java
@Configuration
@EnableWebSecurity
public class BasicAuthSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/public/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
            .withUser("apiuser")
            .password(passwordEncoder().encode("apipassword"))
            .roles("API_USER");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
```

## 5. Remember-Me Authentication
```java
@Configuration
@EnableWebSecurity
public class RememberMeSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
            .rememberMe()
            .key("uniqueAndSecret")
            .tokenValiditySeconds(86400) // 24 hours
            .userDetailsService(userDetailsService);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
```

## 6. LDAP Authentication
```java
@Configuration
@EnableWebSecurity
public class LdapSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .formLogin();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
            .ldapAuthentication()
            .userDnPatterns("uid={0},ou=people")
            .groupSearchBase("ou=groups")
            .contextSource()
            .url("ldap://localhost:8389/dc=example,dc=com")
            .and()
            .passwordCompare()
            .passwordEncoder(new BCryptPasswordEncoder())
            .passwordAttribute("userPassword");
  }
}
```

## 7. Two-Factor Authentication (2FA)
```java
@Configuration
@EnableWebSecurity
public class TwoFactorAuthSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private TwoFactorAuthenticationProvider twoFactorAuthProvider;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .authorizeRequests()
            .antMatchers("/login", "/verify-2fa").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .successHandler(new TwoFactorAuthenticationSuccessHandler())
            .and()
            .addFilterAfter(new TwoFactorVerificationFilter(),
                    UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public TwoFactorAuthenticationProvider twoFactorAuthenticationProvider() {
    return new TwoFactorAuthenticationProvider();
  }
}

public class TwoFactorVerificationFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    // Implement 2FA verification logic
    if (requiresTwoFactorVerification()) {
      redirectToTwoFactorVerification(response);
      return;
    }
    filterChain.doFilter(request, response);
  }
}
```

## 8. OpenID Connect Authentication
```java
@Configuration
@EnableWebSecurity
public class OpenIDConnectSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .authorizeRequests()
            .antMatchers("/", "/error", "/webjars/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2Login()
            .userInfoEndpoint()
            .userService(openIdUserService())
            .and()
            .logout()
            .logoutSuccessUrl("/").permitAll();
  }

  @Bean
  public OAuth2UserService<OidcUserRequest, OidcUser> openIdUserService() {
    return new OidcUserService() {
      @Override
      public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        // Additional processing of OpenID Connect user
        return oidcUser;
      }
    };
  }
}
```

## Best Practices
- Always use strong password encoding
- Implement proper error handling
- Use HTTPS for all authentication endpoints
- Implement proper token management
- Validate and sanitize user inputs
- Configure appropriate session management
- Implement proper logout mechanisms