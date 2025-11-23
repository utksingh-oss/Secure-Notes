### Spring Security Introduction
- As soon as the spring-security dependency is added to the project 
- Due to the Spring Boot's Auto configuration, and spring security's default behavior
- We get the following screen while accessing the `/hello/{name}` API endpoint
![img.png](note-img/img.png)
- The password is auto generated, and all the endpoints are protected by it
![img.png](note-img/img_1.png)
- Username by default is "user"
- We have a `/login` page, and also a `/logout` page
- Default authentication is **Form based authentication**
- When we make a call to any of the endpoints we will be redirected to the form
![img.png](note-img/img_2.png)
```
2025-11-22T21:40:55.469+05:30  WARN 7875 --- [notes] [           main] .s.a.UserDetailsServiceAutoConfiguration : 

Using generated security password: 8734ea81-8d28-4375-b816-801a8d7dd5f3

This generated password is for development use only. Your security configuration must be updated before running your application in production.

2025-11-22T21:40:55.523+05:30  INFO 7875 --- [notes] [           main] r$InitializeUserDetailsManagerConfigurer : Global AuthenticationManager configured with UserDetailsService bean with name inMemoryUserDetailsManager
2025-11-22T21:40:55.719+05:30 DEBUG 7875 --- [notes] [           main] o.s.s.web.DefaultSecurityFilterChain     : Will secure any request with filters: DisableEncodeUrlFilter, WebAsyncManagerIntegrationFilter, SecurityContextHolderFilter, HeaderWriterFilter, CsrfFilter, LogoutFilter, UsernamePasswordAuthenticationFilter, DefaultResourcesFilter, DefaultLoginPageGeneratingFilter, DefaultLogoutPageGeneratingFilter, BasicAuthenticationFilter, RequestCacheAwareFilter, SecurityContextHolderAwareRequestFilter, AnonymousAuthenticationFilter, ExceptionTranslationFilter, AuthorizationFilter
```
#### Making the Password Static
```properties
# Making Password Static as to not have it change with each run
spring.security.user.name=admin
spring.security.user.password=admin
```
***
#### Debugging
- `AuthorizationFilter`: makes the url restricted
  - `doFilter()` comes from the base class `GenericFilterBean`
- `UsernamePasswordAuthenticationFilter`
  - `return this.getAuthenticationManager().authenticate(authRequest);`
  - Here `AuthenticationManager` interface implementation is used

```java
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationManager {
    //..
    Authentication authenticate(Authentication authentication) throws AuthenticationException;
}
```
- `AuthenticationProvider` are usually tried in order until one provides a non-null response.
- `DaoAuthenticationProvider` is provided by Spring-Security by default
  - If the authentication password is matched the Authentication object is updated 
- `DefaultLoginPageGeneratingFilter` class is responsible for generating the page initially loaded whenever APIs are called
***