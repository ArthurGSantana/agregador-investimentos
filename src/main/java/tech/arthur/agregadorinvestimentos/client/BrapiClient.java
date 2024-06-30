package tech.arthur.agregadorinvestimentos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.arthur.agregadorinvestimentos.model.dto.BrapiResponseDto;

@FeignClient(
        name = "brapi",
        url = "https://brapi.dev"
)
public interface BrapiClient {
    @GetMapping("/api/quote/{stockId}")
    public BrapiResponseDto getQuote(@RequestParam("token") String token, @RequestParam("stockId") String stockId);
}
