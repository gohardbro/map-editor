package click.gohard.mapeditor.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class StaticMapController {
    private static final String KAKAO_API_KEY = "e57cb823413fd3e03d73727bc0605831"; // 발급받은 REST API 키

    // Kakao Static Map API 요청을 중계하는 엔드포인트
    @GetMapping("/staticmap")
    public ResponseEntity<byte[]> proxyStaticMap(
            @RequestParam String center,
            @RequestParam String level,
            @RequestParam String size,
            @RequestParam String maptype) {

        // Kakao Static Map API URL 생성
        String kakaoApiUrl = String.format(
                "https://map.kakao.com/map/staticmap?center=%s&level=%s&size=%s&maptype=%s",
                center, level, size, maptype, KAKAO_API_KEY);

        // REST 요청 수행
        RestTemplate restTemplate = new RestTemplate();
        try {
            byte[] imageBytes = restTemplate.getForObject(kakaoApiUrl, byte[].class);

            // 이미지 데이터를 반환
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}