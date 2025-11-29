package com.sanjeeban.AdminService.controller;


import com.sanjeeban.AdminService.service.PdfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    private PdfService pdfService;

    @Autowired
    public PdfController(PdfService pdfService){
        this.pdfService = pdfService;
    }

    @Operation(summary = "Download Resident PDF", description = "Generates and downloads resident details as a PDF")
    @ApiResponse(responseCode = "200", description = "PDF generated successfully", content = @Content(mediaType = "application/pdf", schema = @Schema(type = "string", format = "binary")))
    @GetMapping(path="/getResidentDetails")
    public ResponseEntity<InputStreamResource> createPdf(@RequestParam(name="UniqueId") String uniqueId){

        ByteArrayInputStream pdf = pdfService.generateNewResidentPdf(uniqueId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition","inline;file=resident.pdf");

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));


    }


}
