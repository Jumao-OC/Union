package com.zj.union.config;


import com.zj.union.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/hello").permitAll()//无论是否登录都可以访问
                // 对于登录接口 只允许匿名访问,登录后便不可以访问
                .antMatchers("/user/login").anonymous()
                .antMatchers("/register").anonymous()
                .antMatchers("/user/register").anonymous()
                .antMatchers("/t-user/**/**").hasAnyAuthority("system:normal:list")
/*                .antMatchers("/t-user/userInfo/update").anonymous()
                .antMatchers("/t-user/userInfo/upHeadImage").anonymous()*/

                .antMatchers("/activity").anonymous()
                .antMatchers("/activity/**").anonymous()
/*                .antMatchers("/activity/checkVerify").anonymous()*/
                .antMatchers("/sendPhoneCode").anonymous()
                .antMatchers("/PhoneLogin").anonymous()
                .antMatchers("/send").anonymous()
/*                .antMatchers("/t-animal/index").permitAll()*/
                .antMatchers("/t-animal/**").permitAll()
                .antMatchers("/upload").permitAll()
                .antMatchers("/t-feedback/setUp").permitAll()

                .antMatchers("/t-adopt/**").permitAll()
                .anyRequest().authenticated();
        //把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //配置异常处理器
/*        在SpringSecurity中，如果我们在认证或者授权的过程中出现了异常会被ExceptionTranslationFilter捕获到。在ExceptionTranslationFilter中会去判断是认证失败还是授权失败出现的异常。

​	如果是认证过程中出现的异常会被封装成AuthenticationException然后调用**AuthenticationEntryPoint**对象的方法去进行异常处理。

​	如果是授权过程中出现的异常会被封装成AccessDeniedException然后调用**AccessDeniedHandler**对象的方法去进行异常处理。*/
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).//配置认证失败处理器
                accessDeniedHandler(accessDeniedHandler);//配置授权失败处理器
        //允许跨域
        http.cors();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}