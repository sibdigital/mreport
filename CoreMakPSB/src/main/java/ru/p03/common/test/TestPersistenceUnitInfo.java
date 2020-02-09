/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.common.test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

/**
 *
 * @author timofeevan
 */
public class TestPersistenceUnitInfo implements PersistenceUnitInfo {

    private final ClassLoader contextClassLoader;

    private final List<String> managedClassNames;

    private final String persistenceUnitName;

    private final boolean excludeUnlistedClasses;

    private final String persistenceProviderClassName;

    public TestPersistenceUnitInfo(ClassLoader contextClassLoader, List<String> managedClassNames,
            String persistenceUnitName, boolean excludeUnlistedClasses, String persistenceProviderClassName) {
        this.contextClassLoader = contextClassLoader;
        this.managedClassNames = managedClassNames;
        this.persistenceUnitName = persistenceUnitName;
        this.excludeUnlistedClasses = excludeUnlistedClasses;
        this.persistenceProviderClassName = persistenceProviderClassName;
    }

    @Override
    public String getPersistenceUnitName() {
        return persistenceUnitName;
    }

    @Override
    public String getPersistenceProviderClassName() {
        return persistenceProviderClassName;
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }

    @Override
    public DataSource getJtaDataSource() {
        return null;
    }

    @Override
    public DataSource getNonJtaDataSource() {
        return null;
    }

    @Override
    public List<String> getMappingFileNames() {
        return new ArrayList<>();
    }

    @Override
    public List<URL> getJarFileUrls() {
        try {
            return Collections.list(this.getClass().getClassLoader().getResources(""));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        try {
            ArrayList<URL> list = Collections.list(this.getClass().getClassLoader().getResources(""));
            for (URL url : list) {
                String toString = url.toString();
                if (toString.endsWith("/classes/")) {
                    return url;
                }
            }
            return Collections.list(this.getClass().getClassLoader().getResources("")).get(0);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        //return null;
    }

    @Override
    public List<String> getManagedClassNames() {
        return managedClassNames;
    }

    @Override
    public boolean excludeUnlistedClasses() {
        return excludeUnlistedClasses;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return null;
    }

    @Override
    public ValidationMode getValidationMode() {
        return null;
    }

    @Override
    public Properties getProperties() {
        return new Properties();
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return null;
    }

    @Override
    public void addTransformer(ClassTransformer transformer) {

    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return contextClassLoader;
    }

    @Override
    public ClassLoader getClassLoader() {
        return contextClassLoader;
    }
}
