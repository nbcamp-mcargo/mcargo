package com.mcargo.slackservice.infrastructure.gemini;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.ThinkingConfig;
import com.mcargo.slackservice.application.service.util.PredictAiUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GeminiPrediction implements PredictAiUtils {

    @Value("${gemini.api-key}")
    private String apiKey;

    public String predictAi(String aiRequestMessage) {
        Client client = Client.builder().apiKey(apiKey).build();

        GenerateContentConfig config =
                GenerateContentConfig.builder()
                        // Disables thinking
                        .thinkingConfig(ThinkingConfig.builder().thinkingBudget(0))
                        .build();

        GenerateContentResponse response =
                client.models.generateContent("gemini-2.5-flash",
                        "주문 번호 : 1\n" +
                        "주문자 정보 : 김말숙 / msk@seafood.world\n" +
                        "주문 시간 : 2025-12-08 10:00:00\n" +
                        "상품 정보 : 마른 오징어 50박스\n" +
                        "요청 사항 : 12월 12일 3시까지는 보내주세요!\n" +
                        "발송지 : 경기 북부 센터\n" +
                        "경유지 : 대전광역시 센터, 부산광역시 센터\n" +
                        "도착지 : 부산시 사하구 낙동대로 1번길 1 해산물월드\n" +
                        "배송담당자 : 고길동 / kdk@sparta.world\n" +
                        "이 데이터를 보고 같은 형식으로 너가 아무거나 생성해서 보여줘봐",
                        config
                );

        return response.text();
    }

}
