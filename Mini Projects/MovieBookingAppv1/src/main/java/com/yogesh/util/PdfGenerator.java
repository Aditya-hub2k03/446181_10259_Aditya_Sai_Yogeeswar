package com.yogesh.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class PdfGenerator {

    public static byte[] generateTicketPdf(String bookingDetails) {
        Document document = new Document();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter.getInstance(document, baos);
            document.open();

            document.add(new Paragraph("Movie Ticket"));
            document.add(new Paragraph("------------------"));
            document.add(new Paragraph(bookingDetails));

            document.close();
            return baos.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException("Failed to generate ticket PDF", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during PDF generation", e);
        }
    }
}
