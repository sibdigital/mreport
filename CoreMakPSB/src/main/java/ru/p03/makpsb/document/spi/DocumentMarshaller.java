package ru.p03.makpsb.document.spi;

import java.util.Date;

public interface DocumentMarshaller {
	boolean supports(String docTypeCode);
	
	<T> String marshal(T document);
	
	<T> T unmarshal(String xml, Date operationDate);
	
	<T> T unmarshal(String xml);
}
