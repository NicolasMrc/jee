package com.efrei.view;

import com.efrei.model.Etudiant;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CFFFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Nico on 08/12/2017.
 */
public class PDFBuilder extends AbstractITextPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
                                    PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
        Etudiant etudiant = (Etudiant) model.get("etudiant");

        doc.add(new Paragraph("Recommended books for Spring framework"));

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {3.0f, 2.0f, 2.0f, 2.0f, 1.0f});
        table.setSpacingBefore(10);

        // define font for table header row
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(BaseColor.WHITE);

        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.BLUE);
        cell.setPadding(5);

        // write table header
        cell.setPhrase(new Phrase("Prenom", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nom", font));
        table.addCell(cell);

        table.addCell(etudiant.getPrenom());
        table.addCell(etudiant.getNom());

        doc.add(table);

    }

}
