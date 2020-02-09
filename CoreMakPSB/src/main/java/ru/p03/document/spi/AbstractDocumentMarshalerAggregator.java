package ru.p03.document.spi;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author alxtmf
 */
public abstract class AbstractDocumentMarshalerAggregator<DocTypeable> implements DocumentMarshalFactory {

    private List<DocumentMarshaller> marshallers = new ArrayList<>();
    private List<? extends DocTypeable> types;
  

    public void init(List<? extends DocTypeable> types) {
        setTypes(types);
    }

    public <T> String marshal(T document, Long docType) {
        return getMarshaller(docType).marshal(document);
    }

    public <T> String marshal(T document, String docTypeCode) {
        return getMarshaller(docTypeCode).marshal(document);
    }

    public <T> T unmarshal(String xml, Long docType) {
        return getMarshaller(docType).unmarshal(xml);
    }

    public <T> T unmarshal(String xml, String docTypeCode) {
        return getMarshaller(docTypeCode).unmarshal(xml);
    }

    private DocumentMarshaller getMarshaller(Long docType) {
        String documentTypeCode = getDocumentTypeCode(docType);
        return getMarshaller(documentTypeCode);
    }

    private DocumentMarshaller getMarshaller(String docTypeCode) {
        for (DocumentMarshaller marshaller : getMarshallers()) {
            if (marshaller.supports(docTypeCode)) {
                return marshaller;
            }
        }
        throw new RuntimeException("Incompatible document type: " + docTypeCode);
    }

    public abstract String getDocumentTypeCode(Long documentType);
    
    public abstract String getDocumentTypeCode(String documentType);

    public List<DocumentMarshaller> getMarshallers() {
        return marshallers;
    }

    public void setMarshallers(List<DocumentMarshaller> marshallers) {
        this.marshallers = marshallers;
    }

    public List<? extends DocTypeable> getTypes() {
        return types;
    }

    public void setTypes(List<? extends DocTypeable> types) {
        this.types = types;
    }

}
