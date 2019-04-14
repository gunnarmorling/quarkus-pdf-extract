/*
 * Copyright Gunnar Morling.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package dev.morling.quarkus.pdfextract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Path("/")
public class PdfExtractionResource {

    @POST
    @Path("extract")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response extractTextFromPdf(@MultipartForm FormData myEntity) {
        try {
            String text = getText(myEntity.getPdfFile());

            File temp = File.createTempFile("tempfile", ".tmp");

            try (PrintStream out = new PrintStream(new FileOutputStream(temp))) {
                out.print(text);
            }

            ResponseBuilder response = Response.ok(temp);
            response.header("Content-Disposition", "attachment;filename=" + "extracted.txt");
            return response.build();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String getText(File pdfFile) throws IOException {
        PDDocument doc = PDDocument.load(pdfFile);
        return new PDFLayoutTextStripper().getText(doc);
    }
}
