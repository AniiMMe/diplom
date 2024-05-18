package com.example.diplom.report;

import com.example.diplom.entity.WriteOffProduct;
import com.example.diplom.reposiroty.WriteOffProductRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class ReportService {

    private WriteOffProductRepository productRepository;
    public byte[] generateReport() throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            PDFont font = PDType0Font.load(document, new File("target/classes/static/times.ttf"));
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                List<WriteOffProduct> products = productRepository.findAll();

                contentStream.beginText();
                contentStream.setFont(font, 16);
                contentStream.newLineAtOffset(page.getMediaBox().getWidth() / 2 - 100, page.getMediaBox().getHeight() - 20);
                contentStream.showText("Отчет списаниям продукции со складам");
                contentStream.endText();

                // Создание таблицы
                float margin = 30;
                float tableWidth = page.getMediaBox().getWidth() - 1 * margin;
                float tableHeight = page.getMediaBox().getHeight() - 3 * margin;
                float rowHeight = 20;
                float columnWidth = tableWidth / 4;

                contentStream.setLineWidth(1);

                // Заголовки
                contentStream.beginText();
                contentStream.setFont(font, 14);
                contentStream.newLineAtOffset(margin, page.getMediaBox().getHeight() - 4 * rowHeight);
                contentStream.showText("Наименование товара");
                contentStream.newLineAtOffset(columnWidth, 0);
                contentStream.showText("Артикул");
                contentStream.newLineAtOffset(columnWidth, 0);
                contentStream.showText("Дата");
                contentStream.newLineAtOffset(columnWidth, 0);
                contentStream.showText("Срок годности");
                contentStream.newLineAtOffset(columnWidth, 0);
                contentStream.showText("Стоимость");
                contentStream.newLineAtOffset(columnWidth, 0);
                contentStream.showText("Количество");
                contentStream.endText();

                contentStream.drawLine(margin, page.getMediaBox().getHeight() - 65 - rowHeight,
                        page.getMediaBox().getWidth() - margin, page.getMediaBox().getHeight() - 65 - rowHeight);

                // Заполнение таблицы
                float currentY = page.getMediaBox().getHeight() - 3 * margin - rowHeight;
                for (WriteOffProduct item : products) {
                    contentStream.beginText();
                    contentStream.setFont(font, 14);
                    contentStream.newLineAtOffset(margin, currentY);
                    contentStream.showText(item.getProductName());
                    contentStream.newLineAtOffset(columnWidth, 0);
                    contentStream.showText(item.getIdWriteOffProduct().toString());
                    contentStream.newLineAtOffset(columnWidth, 0);
                    contentStream.showText(item.getProductStartdata().toString());
                    contentStream.newLineAtOffset(columnWidth, 0);
                    contentStream.showText(item.getProductEnddata().toString());
                    contentStream.newLineAtOffset(columnWidth, 0);
                    contentStream.showText(item.getProductPrice().toString());
                    contentStream.newLineAtOffset(columnWidth, 0);
                    contentStream.showText(String.valueOf(item.getProductQuantity()));

                    contentStream.endText();

                    contentStream.drawLine(28, currentY +15 - rowHeight, page.getMediaBox().getWidth() - margin, currentY +15 - rowHeight);
                    currentY -= rowHeight;
                }

                contentStream.close();
                document.save(new File("report.pdf"));
                return Files.readAllBytes(new File("report.pdf").toPath());
            }
        }
    }


}

