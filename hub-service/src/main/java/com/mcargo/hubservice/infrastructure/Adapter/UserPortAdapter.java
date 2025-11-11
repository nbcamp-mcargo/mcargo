package com.mcargo.hubservice.infrastructure.Adapter;

import com.mcargo.hubservice.application.port.UserPort;
import com.mcargo.hubservice.infrastructure.client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPortAdapter implements UserPort {
    private final UserClient userClient;
}
