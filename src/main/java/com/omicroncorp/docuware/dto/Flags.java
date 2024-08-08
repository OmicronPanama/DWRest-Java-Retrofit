package com.omicroncorp.docuware.dto;

public record Flags(boolean IsCold, boolean IsDBRecord, boolean IsCheckedOut, boolean IsCopyRightProtected, boolean IsVoiceAvailable, boolean HasAppendedDocuments, boolean IsProtected, boolean IsDeleted, boolean IsEmail) {
}
