package com.tanchengjin.oauth2.conf;

import com.tanchengjin.oauth2.modules.user.service.UserService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        com.tanchengjin.oauth2.modules.user.pojo.User userByUsername = userService.getUserByUsername(s);
        if (userByUsername == null) {
            throw new UsernameNotFoundException("用户名或密码错误!");
        }
//        User user = new User();
//        user.setUsername("root");
//        user.setPassword(passwordEncoder.encode("root"));
        AuthorityUtils.commaSeparatedStringToAuthorityList("normal");
        User user = new User();
        user.setId(userByUsername.getId());
        user.setUsername(userByUsername.getUsername());
        user.setAvatar(userByUsername.getAvatar());
        user.setPhone(userByUsername.getPhone());
        user.setPassword(userByUsername.getPassword());
        return user;
//        return new org.springframework.security.core.userdetails.User(userByUsername.getUsername(), userByUsername.getPassword(), new ArrayList<GrantedAuthority>());
    }
}
