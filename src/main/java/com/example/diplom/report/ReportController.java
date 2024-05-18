package com.example.diplom.report;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Controller
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/preview")
    public String previewReport() {
        return "preview"; // Возвращает имя представления (шаблон Thymeleaf, JSP и т. д.)
    }

    @GetMapping("/generate-report")
    @ResponseBody
    public ResponseEntity<Resource> generateReport() throws IOException {
        byte[] reportBytes = reportService.generateReport();
        ByteArrayResource resource = new ByteArrayResource(reportBytes);
        return ResponseEntity.ok()
                .header("fileName", "src")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new ByteArrayInputStream(reportBytes)));
    }
}
