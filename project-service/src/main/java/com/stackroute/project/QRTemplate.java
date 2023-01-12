package com.stackroute.project;

import com.google.common.base.Charsets;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class QRTemplate
{
    private String template;

    public QRTemplate(String customTemplate)
    {

        try
        {
            this.template = loadTemplate(customTemplate);
        }

        catch (Exception e)
        {
            this.template = "Empty";
        }

    }

    private String loadTemplate(String customTemplate) throws Exception
    {

        String content;
        URL url = com.google.common.io.Resources.getResource(customTemplate);
        try
        {
            return com.google.common.io.Resources.toString(url, Charsets.UTF_8);
            /*ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(customTemplate).getFile());
            content = new String(Files.readAllBytes(file.toPath()));*/
        }

        catch (IOException e)
        {
            throw new Exception("Could not read template  = " + customTemplate);
        }
        //return content;

    }

    public String getTemplate(Map<String, String> replacements)
    {

        String cTemplate = this.template;
        //Replace the String
        for (Map.Entry<String, String> entry : replacements.entrySet())
        {
            cTemplate = cTemplate.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return cTemplate;
    }
}
