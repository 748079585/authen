package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

@EnableAuthorizationServer
@Configuration
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;  // TokenConfig 中配置

    @Autowired
    private ClientDetailsService clientDetailsService;  // 下面  clients 中配置

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;  // 最下面配置 授权码服务

    @Autowired
    private AuthenticationManager authenticationManager;  // 认证管理器，securityConfiguration中配置

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;  // JWT  TokenConfig中配置

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()//使用内存存储
                .withClient("c1")
                .secret(new BCryptPasswordEncoder().encode("123456"))
                .resourceIds("res1","res2") // 可以访问的资源列表
                .authorizedGrantTypes("authorization_code","password",
                        "client_credentials","implicit","refresh_token")//该client允许的授权类型
                .scopes("all")// 允许的授权范围
                .autoApprove(false)// false跳转到授权页面，默认true 自动授权
                .redirectUris("http://www.baidu.com");//验证回调地址
    }


    // 令牌管理服务
    @Bean
    public AuthorizationServerTokenServices tokenService(){
        DefaultTokenServices service = new DefaultTokenServices();
        service.setClientDetailsService(clientDetailsService);//客户端信息服务
        service.setSupportRefreshToken(true);//是否产生刷新令牌
        service.setTokenStore(tokenStore);//令牌存储策略
        // 令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        service.setTokenEnhancer(tokenEnhancerChain);

        service.setAccessTokenValiditySeconds(7200);//令牌有效期2小时
        service.setRefreshTokenValiditySeconds(259200);//刷新令牌有效期3天
        return service;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager) // 认证管理器 密码模式需要
                .authorizationCodeServices(authorizationCodeServices) //授权码模式需要
                .tokenServices(tokenService()) //令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST); //允许post提交
    }

    // 令牌访问端点安全策略
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();// 表单认证，申请令牌
        security.checkTokenAccess("permitAll()") // /oauth/check_token公开
                .tokenKeyAccess("permitAll()"); ///oauth/token_key公开
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){// 设置授权码模式的授权码如何存取，暂时采用内存方式
        return new InMemoryAuthorizationCodeServices();
    }
}
