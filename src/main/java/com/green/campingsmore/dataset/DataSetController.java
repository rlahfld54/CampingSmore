package com.example.campingsmore.dataset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dataset")
public class DataSetController {
    private final KaKaoApi kaKaoApi;
    private final NaverApi naverApi;

    @Autowired
    public DataSetController(KaKaoApi kaKaoApi, NaverApi naverApi) {
        this.kaKaoApi = kaKaoApi;
        this.naverApi = naverApi;
    }

    @GetMapping("/kakao")
    public String makeDataset(){
        return kaKaoApi.search("캠핑장");
    }

    @GetMapping("/naver")
    public String searchNaver(@RequestParam String text){
        return naverApi.search(text);
    }
}
