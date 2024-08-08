package com.omicroncorp.docuware.dto;

import java.util.List;

public record Document(
        String FileCabinetId,
        int Id,
        List<DocumentField> Fields,
        Flags Flags,
        Version Version,
        List<Link> Links,
        List<Section> Sections,
        String ContentType,
        boolean HaveMoreTotalPages,
        boolean HasTextAnnotation,
        boolean HasXmlDigitalSignatures,
        boolean AnnotationsPreview,
        int TotalPages,
        String Title,
        String LastModified,
        boolean LastModifiedSpecified,
        String CreatedAt,
        boolean CreatedAtSpecified,
        int FileSize,
        int SectionCount,
        String IntellixTrust,
        String VersionStatus) { }