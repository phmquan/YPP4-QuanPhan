package vn.ypp4.quanphan.mvc.view;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MyViewResolver {
    private final String prefix;
    private final String suffix;

    public MyViewResolver(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public void render(ModelAndView mv, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (mv == null || mv.getViewName() == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No view specified");
            return;
        }
        
        // Add model attributes to request
        mv.getModel().forEach(req::setAttribute);
        
        // Build the classpath resource path (remove leading slash from prefix)
        String classpathPrefix = prefix.startsWith("/") ? prefix.substring(1) : prefix;
        String resourcePath = classpathPrefix + mv.getViewName() + suffix;
        System.out.println("Resolving view: " + mv.getViewName() + " to classpath resource: " + resourcePath);
        
        try {
            // Load template from classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
            if (inputStream == null) {
                System.err.println("Template not found in classpath: " + resourcePath);
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Template not found: " + resourcePath);
                return;
            }
            
            // Read template content
            String templateContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            inputStream.close();
            
            // Simple template processing - replace ${variableName} with model values
            String processedContent = processTemplate(templateContent, mv);
            
            // Write response
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write(processedContent);
            
        } catch (IOException e) {
            System.err.println("Error reading template: " + resourcePath + " - " + e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing template: " + e.getMessage());
        }
    }
    
    private String processTemplate(String template, ModelAndView mv) {
        String result = template;
        
        // Simple template variable replacement
        for (String key : mv.getModel().keySet()) {
            Object value = mv.getModel().get(key);
            String placeholder = "${" + key + "}";
            result = result.replace(placeholder, value != null ? value.toString() : "");
        }
        
        return result;
    }
}
