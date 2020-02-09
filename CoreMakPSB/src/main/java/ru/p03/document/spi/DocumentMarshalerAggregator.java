package ru.p03.document.spi;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author alxtmf
 */
public class DocumentMarshalerAggregator extends AbstractDocumentMarshalerAggregator<String> implements DocumentMarshalFactory {

    @Override
    public String getDocumentTypeCode(Long documentType) {
        if (documentType == null){
            throw new RuntimeException("Cannot find code for documentType == NULL");
        }
        return getDocumentTypeCode(documentType.toString());
    }

    @Override
    public String getDocumentTypeCode(String documentType) {
        for (String docType : getTypes()) {
            if (docType!= null && docType.equals(documentType)) {
                return docType;
            }
        }
        throw new RuntimeException("Cannot find code for type " + documentType
                + " Check code in document type classifier.");
    }

}
