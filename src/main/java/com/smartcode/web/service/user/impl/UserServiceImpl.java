package com.smartcode.web.service.user.impl;

import com.smartcode.web.exception.UsernameAlreadyExistsException;
import com.smartcode.web.exception.ValidationException;
import com.smartcode.web.exception.VerificationException;
import com.smartcode.web.model.UserEntity;
import com.smartcode.web.repository.UserRepository;
import com.smartcode.web.service.mail.MailService;
import com.smartcode.web.service.user.UserService;
import com.smartcode.web.utils.RandomGenerator;
import com.smartcode.web.utils.encoder.MD5Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MailService mailService;


    @Override
    @Transactional
    public void register(UserEntity user) {
        validateUserRegistration(user);
        user.setPassword(MD5Encoder.encode(user.getPassword()));
        String code = RandomGenerator.generateNumericString(6);
        user.setCode(code);
        userRepository.save(user);
        mailService.sendEmail(user.getEmail(), "Verification", "Your verification code is: " + code);
    }

    @Override
    @Transactional(readOnly = true)
    public void login(String username, String password) {
        UserEntity byUsername = userRepository.findByEmail(username);
        if (byUsername == null) {
            throw new ValidationException("User not present please register");
        }

        if (!byUsername.isVerified()) {
            throw new VerificationException("Please verify your account");
        }

        String encoded = MD5Encoder.encode(password);
        if (!byUsername.getPassword().equals(encoded)) {
            throw new ValidationException("Invalid login or password");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getUserByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    @Transactional
    public void verify(String email, String code) {
        UserEntity byEmailAndCode = userRepository.findByEmailAndCode(email, code);

        if (byEmailAndCode == null) {
            throw new ValidationException("Your code is incorrect!");
        }
        byEmailAndCode.setVerified(true);
        userRepository.save(byEmailAndCode);
    }

    private void validateUserRegistration(UserEntity user) {
        UserEntity userEntity = userRepository.findByEmail(user.getEmail());
        if (userEntity != null) {
            throw new UsernameAlreadyExistsException(String.format("User by email: %s already exists", user.getEmail()));
        }

        if (!user.getEmail().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            throw new ValidationException("Invalid Email format");
        }

        if (user.getPassword().length() < 8) {
            throw new ValidationException("Password must be more than 8 symbols");
        }

    }


}
