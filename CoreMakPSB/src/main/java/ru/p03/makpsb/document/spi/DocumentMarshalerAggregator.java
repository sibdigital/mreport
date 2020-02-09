package ru.p03.makpsb.document.spi;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pavel.Piskun
 */
public class DocumentMarshalerAggregator implements DocumentMarshalFactory {

    private List<DocumentMarshaller> marshallers = new ArrayList<>();


    public void init() {
        //setTypes(getClassifierRepository().getAll(ClsDocType.class));
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

    private String getDocumentTypeCode(Long documentType) {
//        for (ClsDocType docType : getTypes()) {
//            if (docType.getCode() != null && docType.getId().equals(documentType)) {
//                return docType.getCode();
//            }
//        }
        throw new RuntimeException("Cannot find code for type " + documentType
                + " Check code in NOTAR document type classifier.");
    }

    public List<DocumentMarshaller> getMarshallers() {
        return marshallers;
    }

    public void setMarshallers(List<DocumentMarshaller> marshallers) {
        this.marshallers = marshallers;
    }

//    public List<ClsDocType> getTypes() {
//        return types;
//    }
//
//    public void setTypes(List<ClsDocType> types) {
//        this.types = types;
//    }
//
//    public ClassifierRepository getClassifierRepository() {
//        return classifierRepository;
//    }
//
//    public void setClassifierRepository(ClassifierRepository classifierRepository) {
//        this.classifierRepository = classifierRepository;
//    }
}
