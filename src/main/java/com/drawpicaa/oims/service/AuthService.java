package com.drawpicaa.oims.service;

import com.drawpicaa.oims.dto.AuthLoginRequestDTO;
import com.drawpicaa.oims.dto.AuthRegisterRequestDTO;
import com.drawpicaa.oims.dto.AuthResponseDTO;

public interface AuthService {

    AuthResponseDTO register(AuthRegisterRequestDTO requestDTO);

    AuthResponseDTO login(AuthLoginRequestDTO requestDTO);
}
