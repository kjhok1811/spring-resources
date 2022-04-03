package com.example.springresources;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import java.io.IOException;
import java.util.Arrays;

@SpringBootTest
class SpringResourcesApplicationTests {
    private PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver;

    @BeforeEach
    void setUp() {
        pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
    }

    @Test
    @DisplayName("ClassPathResource 클래스로 클래스패스에 위치한 application.properties 리소스 가져오기")
    void resource1() {
        ClassPathResource classPathResource = new ClassPathResource("application.properties");
        Assertions.assertThat(classPathResource.getFilename()).isEqualTo("application.properties");

        Resource resource = pathMatchingResourcePatternResolver.getResource("application.properties");
        Assertions.assertThat(resource.getFilename()).isEqualTo("application.properties");
    }

    @Test
    @DisplayName("text 디렉토리 하위에 있는 모든 .txt 리소스 가져오기")
    void resource2() throws IOException {
        Resource[] resources = pathMatchingResourcePatternResolver.getResources("text/*.txt");
        String[] fileNames = Arrays.stream(resources).map(Resource::getFilename).toArray(String[]::new);

        Assertions.assertThat(fileNames).contains("a.txt", "b.txt");
    }

    @Test
    @DisplayName("test 디렉토리의 모든 하위 디렉토리의 .txt 리소스 가져오기")
    void resource3() throws IOException {
        Resource[] resources = pathMatchingResourcePatternResolver.getResources("text/**/*.txt");
        String[] fileNames = Arrays.stream(resources).map(Resource::getFilename).toArray(String[]::new);

        Assertions.assertThat(fileNames).contains("a.txt", "b.txt", "c.txt", "d.txt");
    }

    @Test
    @DisplayName("json 디렉토리 하위에 있는 디렉토리중 'b'로 시작하는 디렉토리명의 모든 .json 리소스 가져오기")
    void resource4() throws IOException {
        Resource[] resources = pathMatchingResourcePatternResolver.getResources("json/b*/*.json");
        String[] fileNames = Arrays.stream(resources).map(Resource::getFilename).toArray(String[]::new);

        Assertions.assertThat(fileNames).contains("book.json", "bookstore.json");
    }

    @Test
    @DisplayName("json 디렉토리 하위에 있는 디렉토리중 'r'로 끝나는 디렉토리명의 모든 .json 리소스 가져오기")
    void resource5() throws IOException {
        Resource[] resources = pathMatchingResourcePatternResolver.getResources("json/*r/*.json");
        String[] fileNames = Arrays.stream(resources).map(Resource::getFilename).toArray(String[]::new);

        Assertions.assertThat(fileNames).contains("member.json");
    }
}
