package com.spring.WeatherWear.board.config;

import io.github.cdimascio.dotenv.Dotenv;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public AmazonS3Client amazonS3Client() {

        String accessKey = dotenv.get("AWS_ACCESS_KEY_ID");
        String secretKey = dotenv.get("AWS_SECRET_ACCESS_KEY");
        String region = dotenv.get("AWS_REGION");

        if (accessKey == null || accessKey.isEmpty() ||
                secretKey == null || secretKey.isEmpty() ||
                region == null || region.isEmpty()) {
            throw new IllegalStateException("AWS credentials or region not configured properly");
        }

        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}