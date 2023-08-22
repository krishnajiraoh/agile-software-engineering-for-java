package com.sap.cc.ascii;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.sap.cc.InvalidRequestException;

@Service
public class AsciiArtServiceClient {

    @Value("${service.ascii.username}")
    private String asciiServiceUsername = "";

    @Value("${service.ascii.password}")
    private String asciiServicePassword = "";

    private final WebClient webClient;
    private final AsciiArtServiceUrlProvider asciiArtServiceUrlProvider;

    public AsciiArtServiceClient(WebClient webClient, AsciiArtServiceUrlProvider asciiArtServiceUrlProvider) {
        this.webClient = webClient;
        this.asciiArtServiceUrlProvider = asciiArtServiceUrlProvider;
    }

    public String getAsciiString(AsciiArtRequest asciiArtRequest) {
        String text;
        try{
            text = webClient.post().uri(asciiArtServiceUrlProvider.getServiceUrl())
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(asciiServiceUsername, asciiServicePassword))
                    .bodyValue(asciiArtRequest)
                    .retrieve()
                    .bodyToMono(AsciiArtResponse.class)
                    .block().getBeautifiedText();
        }
        catch (WebClientResponseException.BadRequest e){
            throw new InvalidRequestException("Invalid request: Request cannot be null or empty.", new Throwable());
        }

        return text;
    }
}