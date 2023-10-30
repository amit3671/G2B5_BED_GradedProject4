package com.Application.EmpRegistration.service;

import com.Application.EmpRegistration.payload.LoginDto;
import com.Application.EmpRegistration.payload.RegisterDto;

public interface AuthService {
	
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
