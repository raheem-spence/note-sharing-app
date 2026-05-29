/*
    Why do we need SecurityConfig?

    Without Spring Security, every endpoint is automatically public meaning it is accessible without authentication or
    logging in
     - NOTE: public DOESNT mean no security exists, no backend checks or server logic, it only means no authentication
     is required to access the endpoint

     With Spring Security, every endpoint is treated as protected by default unless you explicitly allow it.
     A protected request means Spring will not let the request reach the controller unless the user is authenticated
     - protected = access control is enforced
     - protected does not mean encrypted or hidden it just means you must be logged in (authenticated) to access it
 */
package com.raheemspence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/*
    This annotation basically means this class contains Spring configuration (setup instructions)
    IMPORTANT: this class is NOT business logic

    @Configuration tells Spring "this class contains bean definitions (setup instructions) that you should load at
    startup"

    bean = an object managed by Spring's container (Spring creates it, stores it, injects it where needed)

    Takeaway:  This annotation marks a class as a source of bean definitions. Spring processes it at startup and uses it
    to build and manage objects inside the application context, ensuring consistent lifecycle and dependency management
 */
@Configuration

/*
    This means "turn on Spring Security's web security system for this application"
    - turn on here means start applying security filters to HTTP requests because when Spring Boot starts, by defualt
    the Spring Security classes exist but they are not actively enforcing rules unless enabled/configured.

    This annotation triggers Spring to enable the following:
    1. Create a Security Filter Chain - pipeline that every HTTP request passes through
    request -> security filters -> controller

    2. Intercept ALL HTTP requests
    before the controller runs, Spring inserts itself in the middle:

    client request
          |
    spring security filters (ACTIVE NOW_
          |
    your controller

    3. Activate authentication logic
    it enables things like:
        - login processing
        - session checking
        - authorization rules

    Simple analogy:
    w/o @EnableWebSecurity --> security system installed but turned OFF

    w/ @EnableWebSecurity --> security system is ON and actively checking every door
 */
@EnableWebSecurity
public class SecurityConfig {

    /*
        This annotation means "this method produces an object that Spring should manage" i.o. take whatever this method
        returns and store it inside Spring so it can be reused.
     */
    @Bean

    /*
        This means "this method will produce a SecurityFilterChain object" essentially saying Spring, build me something
        that matches your SecurityFilerChain contract
            - SecurityFilterChain object is an interface
            - represents a security filter pipeline
            - Spring will create the actual implementation

        interface = defines required behavior, while a class provides the actual implementation

        HttpSecurity http -- this is not the final security system but rather a configuration builder that Spring gives
        us. Spring is basically handing us a "setup object" and saying tell me how you want security to behave
            - NOTE: we are configuring how Spring should build the final object
     */
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // http -- the security configuration object Spring gave us, it already exists we are just modifying it
        http
                /*
                    CRSF = Cross Site Request Forgery
                    - this line basically means protect against fake requests being sent on behalf of a logged-in user

                    Spring will check incoming requests, ensure they are legit, block suspicious POST/PUT/DELETE actions
                    if needed
                 */
                .csrf(Customizer.withDefaults())

                /*
                    This tells Spring to enable HTTP basic authentication for this app by adding a filter to the
                    SecurityFilterChain that reads and validates credentials from HTTP request headers.

                    It is a very simple login method built into HTTP itself

                    The browser sends:
                    Authorization: Basic ...

                    That value is:
                        - username:password
                        - encoded in Base64 (NOT encrypted)

                    So when it is enabled it:
                        1. Checks incoming request headers
                        2. Looks for Authorization: Basic ...
                        3. Decodes credentials
                        4. Validates username/password
                        5. sets authentication in security context
                 */
                .httpBasic(Customizer.withDefaults())

                /*
                   This enables Springs default login system instead of having to write our own

                   In practice --
                   When someone hits a protected route:
                       - Spring redirects to /login
                       - Shows default login page
                       - handles username/password authentication
                       - creates session if successful
                */
                .formLogin(Customizer.withDefaults())

                /*
                    This is where you define who is allowed to access what.

                    Basically it means every request requires authentication so at runtime:
                    When someone hits GET /profile Spring checks:
                        1. Are you logged in?
                            yes -> allow request
                            no -> redirect to login page


                 */
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                );
                /* this tells Spring to actually construct the real security system cause before we were configuring the
        builder basically designing the security system blueprint. Everything before this line is configuration(instructions)

        This is what happens when it is called, Spring does 4 things:
        1. Reads your configuration(instructions):
            - Looks at everything you set -- csrf enabled, formLogin enabled, httpBasic enabled (if present), authorize rules
            So it now has the full "security blueprint"
        2. Creates security filters
            - Spring converts your config into real objects like:
                - CSRF filter
                - Authentication Filter (form login or basic auth)
                - Authorization filter
                - Session management filter
            These are real Java objects
        3. Orders them into a pipeline:
            - Spring arranges them in a specific order:
            Request ->
                CSRF Filter ->
                Authentication Filter ->
                Authorization Filter ->
                Controller
            This ordered structure is the "chain"
        4. Wraps everything into a SecurityFilterChain object
            - Now Spring creates: SecurityFilterChain (final object) which contains the full ordered list of filters
        5. That object is returned from your method
            - because of @Bean Spring stores it in the ApplicationContext

        Before build()
            You are designing a security system blueprint
        After build()
            The actual security system is installed and active

        */

        return http.build();
    }
}
