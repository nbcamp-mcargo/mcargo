package com.mcargo.hubservice.infrastructure.Adapter;

import com.mcargo.hubservice.application.port.AuthPort;
import com.mcargo.hubservice.infrastructure.client.AuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthPortAdapter implements AuthPort {
    private final AuthClient authClient;

    @Override
    public UUID getNextDriver(UUID hubId) {
        return authClient.getNextDriver(hubId);
    }
}
