package com.mcargo.hubservice.application.util;

import com.mcargo.hubservice.application.dto.RouteCreatorsResponse;
import com.mcargo.hubservice.domain.entity.Hub;

import java.util.List;

public interface RouteCreator {

    List<RouteCreatorsResponse> routeCreate(List<Hub> hubs);
}
