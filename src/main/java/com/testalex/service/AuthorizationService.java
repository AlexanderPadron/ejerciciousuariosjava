package com.testalex.service;

import com.testalex.dto.RegisterDto;
import com.testalex.exception.ExampleTestAlexException;
import com.testalex.model.Users;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AuthorizationService {

    private final UsersService usersService;
    private final PhoneService phoneService;

    @Transactional
    public Users register(RegisterDto registerDto) throws ExampleTestAlexException {
        log.info("register | registerDto={}", registerDto);
        usersService.validNotExistByEmail(registerDto.getEmail());
        Users users = usersService.save(registerDto);
        phoneService.saveAll(users, registerDto);
        return users;
    }
}
