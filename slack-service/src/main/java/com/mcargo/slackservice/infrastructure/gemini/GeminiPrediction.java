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
                        "상품 주문이 들어왔는데 정보를 줄테니 최소 발송기한이 얼마인지 예측해줘.\n" +
                                aiRequestMessage +
                                "이 정보를 기반으로 최소 발송 기한 날짜와 시간을 예측해줘",
                        config
                );

        return response.text();
    }

}
