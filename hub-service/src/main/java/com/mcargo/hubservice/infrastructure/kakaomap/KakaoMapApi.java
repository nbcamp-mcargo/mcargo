package com.mcargo.hubservice.infrastructure.kakaomap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcargo.hubservice.domain.entity.Hub;
import com.mcargo.hubservice.domain.exception.HubException;
import com.mcargo.hubservice.domain.response.HubResponseCode;
import com.mcargo.hubservice.infrastructure.kakaomap.dto.GetDistanceAndDurationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class KakaoMapApi {
    @Value("${kakao.rest-api-key}")
    private String kakaoRestApiKey;

    public GetDistanceAndDurationResponse getDistanceAndDuration(Hub fromHub, Hub toHub) {
        String distanceText;
        String durationText;
        String jsonString = getRoute(transformHubToRoutingData(fromHub), transformHubToRoutingData(toHub));
        ObjectMapper mapper = new ObjectMapper();
        try {
            // String → JsonNode 변환
            JsonNode root = mapper.readTree(jsonString);
            // "routes" 배열 접근
            JsonNode routes = root.path("routes");

            // 첫 번째 route의 summary 가져오기
            JsonNode summary = routes.get(0).path("summary");
            // 거리(km)와 소요시간(초)
            int distance = summary.path("distance").asInt();
            int duration = summary.path("duration").asInt();


            // 예상 거리 보기 쉽게
            if (distance >= 1000) {
                distanceText = String.format("%.1f km", distance / 1000.0);
            } else {
                distanceText = distance + " m";
            }
            // 예상 시간 보기 쉽게
            if (duration >= 3600) {
                durationText = String.format("%d시간 %d분", duration / 3600, (duration % 3600) / 60);
            } else if (duration >= 60) {
                durationText = String.format("%d분", duration / 60);
            } else {
                durationText = duration + "초";
            }

        } catch (Exception e) {
            System.out.println(e.getMessage()); // JSON 파싱 실패 시 에러 출력
            throw new HubException(HubResponseCode.HUB_ROUTE_JSON_FAIL);
        }

        return new GetDistanceAndDurationResponse(
                distanceText,
                durationText
        );
    }

    private String getRoute(String origin, String destination) {
        // origin: "127.1086228,37.4012191"
        // destination: "127.1058342,37.3979657"

        String url = UriComponentsBuilder
                .fromHttpUrl("https://apis-navi.kakaomobility.com/v1/directions")
                .queryParam("origin", origin)
                .queryParam("destination", destination)
                .build()
                .toUriString();

        WebClient client = WebClient.create();

        return client.get()
                .uri(url)
                .header("Authorization", "KakaoAK " + kakaoRestApiKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String transformHubToRoutingData(Hub hub) {
        return (hub.getLongitude()).toString() +","+ (hub.getLatitude()).toString();
    }


}
