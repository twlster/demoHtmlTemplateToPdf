package com.example.demo;

import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
public class Controller {

    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping("/")
    public void createPDF()
        throws
        IOException,
        DocumentException {
        String outputFolder=System.getProperty("user.home") + File.separator + "thymeleaf.pdf";
        //Path temp = Files.createTempFile("thymeleaf", ".pdf");

        try (OutputStream outputStream=new FileOutputStream(outputFolder)) {
            ITextRenderer renderer=new ITextRenderer();
            renderer.setDocumentFromString(parseThymeleafTemplate());
            renderer.layout();
            renderer.createPDF(outputStream);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    private String parseThymeleafTemplate() {


        Context context = new Context();
        context.setVariable("to", System.getProperty("user.name"));
        context.setVariable("pie","Sencillo");
        context.setVariable("header","HTML template to PDF");

        return templateEngine.process("thymeleaf_template", context);
    }
}
