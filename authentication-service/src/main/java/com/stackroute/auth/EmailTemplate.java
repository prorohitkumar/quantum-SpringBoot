package com.stackroute.auth;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;
import com.google.common.base.Charsets;

public class EmailTemplate {

    private String template;

    public EmailTemplate(String customTemplate) {

        try {
            this.template = loadTemplate(customTemplate);
        } catch (Exception e) {
            this.template = "Empty";
        }

    }

    private String loadTemplate(String customTemplate) throws Exception {

        String content;
        URL url = com.google.common.io.Resources.getResource(customTemplate);
        try {
            return com.google.common.io.Resources.toString(url, Charsets.UTF_8);
            /*ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(customTemplate).getFile());
            content = new String(Files.readAllBytes(file.toPath()));*/
        } catch (IOException e) {
            throw new Exception("Could not read template  = " + customTemplate);
        }
        //return content;

    }

    public String getTemplate(Map<String, String> replacements) {

        String cTemplate = this.template;
        //Replace the String
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            cTemplate = cTemplate.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return cTemplate;
    }
}

