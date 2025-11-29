package com.sanjeeban.AdminService.service;


import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sanjeeban.AdminService.customException.ResidentNotFound;
import com.sanjeeban.AdminService.dto.ResidentDetailsDto;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    private ResidentService residentService;

    public PdfService(ResidentService residentService){
        this.residentService = residentService;
    }

    public ByteArrayInputStream generateNewResidentPdf(String uniqueId){

        String title = "RESIDENT DETAILS";

        ResidentDetailsDto residentDetails = new ResidentDetailsDto();
        residentDetails = residentService.getResidentDetails(uniqueId);

        if(residentDetails.getResidentUniqueId()==null){
            throw new ResidentNotFound("Resident does not exists");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, outputStream);  // output stream receives PDF data
            document.open();


            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Paragraph titlePara = new Paragraph("🏠 Resident Registration", titleFont);
            titlePara.setAlignment(Element.ALIGN_CENTER);
            titlePara.setSpacingAfter(20f);
            document.add(titlePara);


            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Paragraph header = new Paragraph("Resident Details", headerFont);
            header.setSpacingAfter(10f);
            document.add(header);


            PdfPTable table = new PdfPTable(2); // 2 columns: label + value
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(20f);

            Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            // Helper method to add rows
            table.addCell(new Phrase("Unique ID:", labelFont));
            table.addCell(new Phrase(residentDetails.getResidentUniqueId(), valueFont));

            table.addCell(new Phrase("Block:", labelFont));
            table.addCell(new Phrase(residentDetails.getBlockNumber(), valueFont));

            table.addCell(new Phrase("Floor:", labelFont));
            table.addCell(new Phrase(residentDetails.getFloorNumber(), valueFont));

            table.addCell(new Phrase("Apartment:", labelFont));
            table.addCell(new Phrase(residentDetails.getApartmentNumber(), valueFont));

            table.addCell(new Phrase("First Name:", labelFont));
            table.addCell(new Phrase(residentDetails.getFirstName(), valueFont));

            table.addCell(new Phrase("Last Name:", labelFont));
            table.addCell(new Phrase(residentDetails.getLastName(), valueFont));

            table.addCell(new Phrase("Family Number:", labelFont));
            table.addCell(new Phrase(residentDetails.getFamilyNumber(), valueFont));


            document.add(table);

            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10);
            Paragraph footer = new Paragraph("Generated on: " + new java.util.Date(), footerFont);
            footer.setAlignment(Element.ALIGN_RIGHT);
            document.add(footer);

        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }


        return new ByteArrayInputStream(outputStream.toByteArray());

    }


}
