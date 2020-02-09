/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.increm.model;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.mappings.xdb.DirectToXMLTypeMapping;

/**
 *
 * @author timofeevan
 */
public class XMLDataCustomizerRegDetectedSignatureDtlValues implements DescriptorCustomizer {

    @Override
    public void customize(ClassDescriptor descriptor) throws Exception {
        descriptor.removeMappingForAttributeName("dtlData");
        DirectToXMLTypeMapping mapping1 = new DirectToXMLTypeMapping();
        mapping1.setAttributeName("dtlData"); //name of the atribute on the Entity Bean
        mapping1.setFieldName("DTL_DATA"); //name of the data base column
        descriptor.addMapping(mapping1);
        
        descriptor.removeMappingForAttributeName("dtlXslt");
        DirectToXMLTypeMapping mapping2 = new DirectToXMLTypeMapping();
        mapping2.setAttributeName("dtlXslt"); //name of the atribute on the Entity Bean
        mapping2.setFieldName("DTL_XSLT"); //name of the data base column
        descriptor.addMapping(mapping2);
    }

}
