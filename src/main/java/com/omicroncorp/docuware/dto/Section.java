package com.omicroncorp.docuware.dto;

import java.util.List;

public record Section(List<String> SignatureStatus, Pages Pages, Thumbnails Thumbnails, List<Link> Links, String Id, String ContentType, boolean HaveMorePages, int PageCount, int FileSize, String OriginalFileName, String ContentModified, boolean HasTextAnnotation, boolean AnnotationsPreview) {
}
