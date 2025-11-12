package com.mcargo.hubservice.infrastructure.kakaomap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcargo.hubservice.application.util.PredictDistanceAndDuration;
import com.mcargo.hubservice.domain.entity.Hub;
import com.mcargo.hubservice.domain.exception.HubException;
import com.mcargo.hubservice.domain.response.HubResponseCode;
import com.mcargo.hubservice.application.dto.GetDistanceAndDurationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class KakaoMapApi implements PredictDistanceAndDuration {

    @Value("${kakao.rest-api-key}")
    private String kakaoRestApiKey;

    // 허브 이동 간 예상 대기 시간
    public GetDistanceAndDurationResponse getDistanceAndDuration(Hub fromHub, Hub toHub) {
        int distance;
        int duration;

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
            distance = summary.path("distance").asInt();
            duration = summary.path("duration").asInt();

        } catch (Exception e) {
            System.out.println(e.getMessage()); // JSON 파싱 실패 시 에러 출력
            throw new HubException(HubResponseCode.HUB_ROUTE_JSON_FAIL);
        }

        return new GetDistanceAndDurationResponse(
                distanceText(distance),
                durationText(duration)
        );
    }
    // TODO 컴퍼니 들어가도 작동하도록
//    public GetDistanceAndDurationResponse getDistanceAndDuration(Hub fromHub, Company toCompany) {
//        getDistanceAndDuration
//    }

    // 경도,위도 순으로 문자열로
    private String transformHubToRoutingData(Hub hub) {
        return (hub.getLongitude()).toString() +","+ (hub.getLatitude()).toString();
    }

    // TODO 컴퍼니 변환도 만들어서 오버라이드 만들기
//    private String transformCompanyToRoutingData(Company company) {
//        return (company.getLongitude()).toString() +","+ (company.getLatitude()).toString();
//    }

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


    // 예상 거리 보기 쉽게
    private String distanceText(int distance) {
        String returnText;

        if (distance >= 1000) {
            returnText = String.format("%.1f km", distance / 1000.0);
        } else {
            returnText = distance + " m";
        }

        return returnText;
    }

    // 예상 시간 보기 쉽게
    private String durationText(int duration) {
        String returnText;

        if (duration >= 3600) {
            returnText = String.format("%d시간 %d분", duration / 3600, (duration % 3600) / 60);
        } else if (duration >= 60) {
            returnText = String.format("%d분", duration / 60);
        } else {
            returnText = duration + "초";
        }

        return returnText;
    }

}
