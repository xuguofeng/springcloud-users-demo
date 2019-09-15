package org.net5ijy.springcloud.admindemo.web.configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CsrfToken;

/**
 * org.net5ijy.springcloud.admin.web.configuration.SecurityConfiguration 类
 *
 * @author xuguofeng
 * @date 2019/5/29 11:06
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private static final String CSRF_TOKEN_KEY = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

  private static final String CSRF_HEADER_KEY = "X-CSRF-TOKEN";

  private static final String CSRF_PARAMETER_KEY = "_csrf";

  private static final String X_REQUESTED_WITH = "X-Requested-With";

  private static final String XML_HTTP_REQUEST = "XMLHttpRequest";

  @Resource
  private UserDetailsService userDetailsService;

  @Resource
  private PasswordEncoder passwordEncoder;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  private AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);
    authenticationProvider.setHideUserNotFoundExceptions(false);
    return authenticationProvider;
  }

  /**
   * 自定义配置
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        // 都可以访问
        .antMatchers("/css/**", "/js/**", "/fonts/**", "/icon/**", "/favicon.ico").permitAll()
        // 需要相应的角色才能访问
        .antMatchers("/user/add", "/user/create", "/user/list",
            "/user/page", "/user/d/**", "/user/e/**", "/user/update")
        .hasRole("ADMIN")
        .and()
        .formLogin()
        // 基于Form表单登录验证
        .loginPage("/login")
        .successHandler(
            (HttpServletRequest request, HttpServletResponse response, Authentication auth) ->
                response.sendRedirect("/user/list")
        )
        .failureHandler(new SimpleUrlAuthenticationFailureHandler("/login-error"))
        .and().exceptionHandling()
        .accessDeniedHandler(
            (HttpServletRequest req, HttpServletResponse resp, AccessDeniedException deniedException) -> {

              String ajaxFlag = req.getHeader(X_REQUESTED_WITH);
              String method = req.getMethod();
              boolean isPost = "post".equalsIgnoreCase(method);

              HttpSession s = req.getSession();
              CsrfToken token = (CsrfToken) s.getAttribute(CSRF_TOKEN_KEY);

              // AJAX请求处理
              if (XML_HTTP_REQUEST.equals(ajaxFlag)) {

                String csrf = req.getHeader(CSRF_HEADER_KEY);

                // CSRF-TOKEN不匹配
                if (isPost && !token.getToken().equals(csrf)) {
                  resp.sendError(HttpStatus.UNAUTHORIZED.value(), deniedException.getMessage());
                } else {
                  resp.sendError(HttpStatus.FORBIDDEN.value(), deniedException.getMessage());
                }
              } else { // 普通请求
                String csrf = req.getParameter(CSRF_PARAMETER_KEY);

                // CSRF-TOKEN不匹配
                if (isPost && !token.getToken().equals(csrf)) {
                  resp.sendRedirect("/login");
                } else {
                  req.getRequestDispatcher("/Access_Denied").forward(req, resp);
                }
              }
            }
        ).authenticationEntryPoint(
        (HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) -> {

          String ajaxFlag = request.getHeader(X_REQUESTED_WITH);
          if (XML_HTTP_REQUEST.equals(ajaxFlag)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
          } else {
            response.sendRedirect("/login");
          }
        });
  }

  /**
   * 认证信息管理
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth)
      throws Exception {
    auth.userDetailsService(userDetailsService);
    auth.authenticationProvider(authenticationProvider());
  }
}
