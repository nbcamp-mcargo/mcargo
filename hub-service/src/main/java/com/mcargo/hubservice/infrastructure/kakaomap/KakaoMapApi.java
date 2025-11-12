package com.mcargo.hubservice.infrastructure.kakaomap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcargo.hubservice.application.util.PredictDistanceAndDuration;
import com.mcargo.hubservice.domain.entity.Hub;
import com.mcargo.hubservice.domain.exception.HubException;
import com.mcargo.hubservice.domain.response.HubResponseCode;
import com.mcargo.hubservice.application.dto.GetDistanceAndDurationResponse;
import com.mcargo.hubservice.presentation.dto.CompanyReadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class KakaoMapApi implements PredictDistanceAndDuration {

    @Value("${kakao.rest-api-key}")
    private String kakaoRestApiKey;

    // 허브 이동 간 예상 대기 시간
    public GetDistanceAndDurationResponse getDistanceAndDuration(Object from, Object to) {
        String jsonString = getRoute(toRoutingData(from), toRoutingData(to));

        long distance;
        long duration;
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
                distance,
                duration
        );
    }

    // 허브, 컴퍼니 데이터에서 경도,위도 데이터 추출 후 가공
    private String toRoutingData(Object obj) {
        if (obj instanceof Hub hub) {
            return hub.getLongitude() + "," + hub.getLatitude();
        }
        else if (obj instanceof CompanyReadResponse company) {
            return company.longitude() + "," + company.latitude();
        }
        throw new HubException(HubResponseCode.HUB_ROUTE_FAIL);
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

}
