package com.omicroncorp.docuware.dto;

public record DocumentField(boolean SystemField, String FieldName, String FieldLabel, boolean IsNull, boolean ReadOnly,
                            Object Item, String ItemElementName) {
}
