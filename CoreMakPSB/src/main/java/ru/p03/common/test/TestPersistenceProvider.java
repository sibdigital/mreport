/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.common.test;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import org.eclipse.persistence.jpa.PersistenceProvider;

/**
 *
 * @author timofeevan
 */
public class TestPersistenceProvider {

    final PersistenceUnitInfo persistenceUnitInfo;
    final PersistenceProvider provider = new PersistenceProvider();
    private final ClassLoader contextClassLoader;

    private final List<String> managedClassNames = new ArrayList<>();

    private final String persistenceUnitName;

    private final boolean excludeUnlistedClasses;

    private final String persistenceProviderClassName = "org.eclipse.persistence.jpa.PersistenceProvider";

    private TestConfig config;

    public TestPersistenceProvider(TestConfig config, String persistenceUnitName) {
        contextClassLoader = Thread.currentThread().getContextClassLoader();
        this.persistenceUnitName = persistenceUnitName;
        this.config = config;
        excludeUnlistedClasses = true;
        persistenceUnitInfo = new TestPersistenceUnitInfo(contextClassLoader, managedClassNames, this.persistenceUnitName, excludeUnlistedClasses, persistenceProviderClassName);
    }
    
    public TestPersistenceProvider(TestConfig config, String persistenceUnitName, List<String> managedClassNames) {
        contextClassLoader = Thread.currentThread().getContextClassLoader();
        this.persistenceUnitName = persistenceUnitName;
        this.config = config;
        this.managedClassNames.addAll(managedClassNames);
        excludeUnlistedClasses = true;
        persistenceUnitInfo = new TestPersistenceUnitInfo(contextClassLoader, managedClassNames, this.persistenceUnitName, excludeUnlistedClasses, persistenceProviderClassName);
    }
    
    public TestPersistenceProvider(TestConfig config, String persistenceUnitName, List<String> managedClassNames, boolean excludeUnlistedClasses) {
        contextClassLoader = Thread.currentThread().getContextClassLoader();
        this.persistenceUnitName = persistenceUnitName;
        this.config = config;
        this.managedClassNames.addAll(managedClassNames);
        this.excludeUnlistedClasses = excludeUnlistedClasses;
        persistenceUnitInfo = new TestPersistenceUnitInfo(contextClassLoader, managedClassNames, this.persistenceUnitName, excludeUnlistedClasses, persistenceProviderClassName);
    }

    public EntityManagerFactory getEntityManagerFactory() {
        EntityManagerFactory cemf = provider.createContainerEntityManagerFactory(persistenceUnitInfo, 
                config.asMap());
        return cemf;
    }
}
