/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.core.util;

/**
 *
 * @author altmf
 */
public class UserUtilTest {

//    public UserUtilTest() {
//    }
//
//    EntityManagerFactory emf;
//    UserInfoJpaController uijp;
//    RoleJpaController rjc;
//    Map hm = new HashMap();
//    String pu = "CoreMakPSBPU_TEST";
//
//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    @Before
//    public void setUp() {
//        String dbUrl = "jdbc:db2://10.3.30.230:50000/MP_CORE";
//
//        hm = new HashMap();
//        hm.put("javax.persistence.jdbc.url", dbUrl);
//        hm.put("javax.persistence.jdbc.user", "db2admin");
//        hm.put("javax.persistence.jdbc.driver", "com.ibm.db2.jcc.DB2Driver");
//        hm.put("javax.persistence.jdbc.password", "db2admin");
//    }
//
//    /**
//     * Test of roles method, of class UserUtil.
//     */
//    @Test
//    @Ignore
//    public void testRoles_0args() {
//        System.out.println("roles");
//
//        //SEPersistenceUnitInfo
//        org.eclipse.persistence.jpa.PersistenceProvider provider = new org.eclipse.persistence.jpa.PersistenceProvider(); //org.eclipse.persistence.jpa.PersistenceProvider
//        PersistenceUnitInfo persistenceUnitInfo = persistenceUnitInfo();
//        EntityManagerFactory createContainerEntityManagerFactory = provider.createContainerEntityManagerFactory(persistenceUnitInfo, hm);
//        //emf = Persistence.createEntityManagerFactory(pu, hm);
//        //org.eclipse.persistence.internal.jpa.EntityManagerFactoryImpl emfimpl = (org.eclipse.persistence.internal.jpa.EntityManagerFactoryImpl) emf;
//
//        //createContainerEntityManagerFactory.getMetamodel().getManagedTypes().addAll(emf.getMetamodel().getManagedTypes());
//        rjc = new RoleJpaController(createContainerEntityManagerFactory);
//        List<Role> result = rjc.findRoleEntities();
//
//        if (result == null || result.isEmpty()) {
//            fail("The test case is a prototype.");
//        }
//    }
//
//    @Test
//    public void testresource_asString() {
//        String resource_asString = ResourceUtil.inctance()
//                .resource_asString(Class.class, "/ru/p03/makpsb/core/entity/sql/user_info.sql");
//    }
//
//    private static PersistenceUnitInfo persistenceUnitInfo() {
//
//        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
//
//        return new PersistenceUnitInfo() {
//
//            private ClassLoader contextClassLoader = loader;
//
//            @Override
//            public String getPersistenceUnitName() {
//                return "testUnit";
//            }
//
//            @Override
//            public String getPersistenceProviderClassName() {
//                return "org.eclipse.persistence.jpa.PersistenceProvider";
//            }
//
//            @Override
//            public PersistenceUnitTransactionType getTransactionType() {
//                return PersistenceUnitTransactionType.RESOURCE_LOCAL;
//            }
//
//            @Override
//            public DataSource getJtaDataSource() {
//                return null;
//            }
//
//            @Override
//            public DataSource getNonJtaDataSource() {
//                return null;
//            }
//
//            @Override
//            public List<String> getMappingFileNames() {
//                return new ArrayList<>();
//            }
//
//            @Override
//            public List<URL> getJarFileUrls() {
//                try {
//                    return Collections.list(this.getClass().getClassLoader().getResources(""));
//                } catch (IOException e) {
//                    throw new UncheckedIOException(e);
//                }
//            }
//
//            @Override
//            public URL getPersistenceUnitRootUrl() {
//                try {
//                    ArrayList<URL> list = Collections.list(this.getClass().getClassLoader().getResources(""));
//                    for (URL url : list) {
//                        String toString = url.toString();
//                        if (toString.endsWith("/classes/")) {
//                            return url;
//                        }
//                    }
//                    return Collections.list(this.getClass().getClassLoader().getResources("")).get(0);
//                } catch (IOException e) {
//                    throw new UncheckedIOException(e);
//                }
//                //return null;
//            }
//
//            @Override
//            public List<String> getManagedClassNames() {
//                return new ArrayList<String>();//Arrays.asList("ru.p03.makpsb.core.entity.service.Role");
//            }
//
//            @Override
//            public boolean excludeUnlistedClasses() {
//                return true;
//            }
//
//            @Override
//            public SharedCacheMode getSharedCacheMode() {
//                return null;
//            }
//
//            @Override
//            public ValidationMode getValidationMode() {
//                return null;
//            }
//
//            @Override
//            public Properties getProperties() {
//                return new Properties();
//            }
//
//            @Override
//            public String getPersistenceXMLSchemaVersion() {
//                return null;
//            }
//
//            @Override
//            public void addTransformer(ClassTransformer transformer) {
//
//            }
//
//            @Override
//            public ClassLoader getNewTempClassLoader() {
//                return contextClassLoader;
//            }
//
//            @Override
//            public ClassLoader getClassLoader() {
//                return contextClassLoader;
//            }
//
//        };
//    }

}
