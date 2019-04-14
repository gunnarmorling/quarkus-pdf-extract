/*
 * Copyright Gunnar Morling.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package dev.morling.quarkus.pdfextract;

import java.io.File;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class FormData {

    private File pdfFile;

    public File getPdfFile() {
        return pdfFile;
    }

    @FormParam("uploadedFile")
    @PartType("application/octet-stream")
    public void setPdfFile(File pdfFile) {
        this.pdfFile = pdfFile;
    }
}
