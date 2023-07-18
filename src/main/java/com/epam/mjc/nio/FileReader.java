package com.epam.mjc.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FileReader {
    Logger logger = LoggerFactory.getLogger("com.epam.mjc.nio.FileReader");

    public Profile getDataFromFile(File file) {

        List<String> content = new ArrayList<>();
        Charset charset = StandardCharsets.UTF_8;

        try (Stream<String> stream = Files.lines(file.toPath(), charset)) {
            content = stream.collect(Collectors.toList());
        } catch (IOException e) {
            logger.warn("Something went wrong with IO!");
        }

        ContentParser contentParser = new ContentParser();
        Map<String, String> contentMap = contentParser.parseContent(content);

        Profile profile = new Profile();
        profile.setName(contentMap.get("name"));
        profile.setEmail(contentMap.get("email"));

        try {
            profile.setAge(Integer.parseInt(contentMap.get("age")));
            profile.setPhone(Long.parseLong(contentMap.get("phone")));
        } catch (NumberFormatException e) {
            logger.warn("Failed to parse numeric attributes for Profile!");
        }

        return profile;
    }
}