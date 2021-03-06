package com.ledao.conf;

import com.ledao.entity.User;
import com.ledao.service.UserService;
import com.ledao.util.AscUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * spring security 配置
 *
 * @author LeDao
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserService userService;

    /**
     * 配置用户认证
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        User user = userService.findByUserName("admin");
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin")
                .password(new BCryptPasswordEncoder().encode(AscUtil.decrypt(user.getPassword())))
                .roles("ADMIN");
    }

    /**
     * 请求授权
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable().headers().disable()
                .authorizeRequests()
                // 配置不需要身份认证的请求地址
                .antMatchers("/","/index","/download","/checkCodeIsSuccess", "/static/**", "/blog/**", "/drawImage","/getFruitListJson","/getFruitJson").permitAll()
                // 配置需要身份认证的请求地址
                .antMatchers("/admin","/admin/**","/show","/changeSkin","/changeSendMail","/backup","/changeCodeStyle","/changeCodeFamily","/countIpNumByDay","/countInterviewNumByDay").hasRole("ADMIN")
                .and()
                .formLogin()
                // 指定登录请求地址
                .loginPage("/login")
                // 登录成功后的默认跳转页面
                .defaultSuccessUrl("/admin")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll();
    }
}
