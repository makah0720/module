package com.example.erp.spend.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ReportExporter {
    public static byte[] toPdf(String title, List<String> headers, List<List<String>> rows) {
        try {
            Document document = new Document(PageSize.A4.rotate());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph(title));
            PdfPTable table = new PdfPTable(headers.size());
            headers.forEach(h -> table.addCell(h));
            for (List<String> row : rows) { row.forEach(table::addCell); }
            document.add(table);
            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toXlsx(String sheetName, List<String> headers, List<List<String>> rows) {
        try (XSSFWorkbook wb = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = wb.createSheet(sheetName);
            int r = 0;
            Row header = sheet.createRow(r++);
            for (int i = 0; i < headers.size(); i++) header.createCell(i).setCellValue(headers.get(i));
            for (List<String> row : rows) {
                Row sr = sheet.createRow(r++);
                for (int i = 0; i < row.size(); i++) sr.createCell(i).setCellValue(row.get(i));
            }
            wb.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

