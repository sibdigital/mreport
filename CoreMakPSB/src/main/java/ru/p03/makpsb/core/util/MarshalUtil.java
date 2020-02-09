/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.util;

import java.util.Arrays;
import ru.p03.makpsb.document.spi.CustomDocumentMarshallerImpl;
import ru.p03.makpsb.document.spi.DocumentMarshalerAggregator;
import ru.p03.makpsb.document.spi.DocumentMarshaller;
import ru.p03.makpsb.increm.model.RegDetectedSignatureDtlValues;

/**
 *
 * @author timofeevan
 */
public class MarshalUtil {
    
    private static MarshalUtil SINGLTON = null;
    private DocumentMarshalerAggregator marshalFactory = new DocumentMarshalerAggregator();
    
    public DocumentMarshalerAggregator getMarshalFactory() {
        return marshalFactory;
    }
    
    private MarshalUtil(){
        DocumentMarshaller regDetectedSignatureDtlValuesMrshr = new CustomDocumentMarshallerImpl(RegDetectedSignatureDtlValues.class, RegDetectedSignatureDtlValues.class.getName());
        
        getMarshalFactory().setMarshallers(Arrays.asList(regDetectedSignatureDtlValuesMrshr));
        getMarshalFactory().init();
    }
    
    public static MarshalUtil instance(){
        if (SINGLTON == null){
            SINGLTON = new MarshalUtil();
        }
        return SINGLTON;
    }
}
