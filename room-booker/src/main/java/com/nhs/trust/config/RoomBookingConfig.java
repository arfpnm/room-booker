package com.nhs.trust.config;

/**
 * Created by arif.mohammed on 30/10/2015.
 */

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.nhs.trust"})
@EnableJpaRepositories(basePackages = "com.nhs.trust.repository", entityManagerFactoryRef = "entityManagerFactoryBean")
public class RoomBookingConfig extends WebMvcConfigurationSupport {

    private static Logger logger = LoggerFactory.getLogger(RoomBookingConfig.class);

    private static final String UK_NHS_TEWV_DOMAIN = "com.nhs.trust.domain";

    private static final String RB_PERSISTENCE_UNIT = "roomBookingPersistenceUnit";

    private static final String HIBERNATE_SHOW_SQL = "true";

    private static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

    private static final String HIBERNATE_DIALECT = "hibernate.dialect";

    private static final String DECRYPT_CODE = "tewvdbprop";

    @Autowired
    private Environment env;

    @Value("${datasource.driver}")
    private String dataSourceDriver;

    @Value("${datasource.url}")
    private String dataSourceUrl;

    @Value("${datasource.username}")
    private String dataSourceUser;

    @Value("${datasource.password}")
    private String dataSourcePassword;

    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddl;

    @Value("${hibernate.show_sql}")
    private String showSql;

   /**
    @Override 
         public void addInterceptors(InterceptorRegistry registry) { 
             registry.addInterceptor(new CORSInterceptor()); 
         } 
         **/
    
    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    /**
     * Configures the LocalContainerEntityManagerFactoryBean
     *
     * @return LocalContainerEntityManagerFactoryBean
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean
                .setPersistenceUnitName(RB_PERSISTENCE_UNIT);
        localContainerEntityManagerFactoryBean
                .setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        //localContainerEntityManagerFactoryBean
        //.setJpaVendorAdapter(getHibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean
                .setJpaProperties(additionalProperties());
        localContainerEntityManagerFactoryBean.setPackagesToScan(new String[]{UK_NHS_TEWV_DOMAIN});
        return localContainerEntityManagerFactoryBean;
    }

    /**
     * Populates the HibernateJpaVendorAdapter
     *
     * @return HibernateJpaVendorAdapter
     */
    @Bean
    public HibernateJpaVendorAdapter getHibernateJpaVendorAdapter() {
        final HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.HSQL);
        hibernateJpaVendorAdapter.setDatabasePlatform(dialect);
        return hibernateJpaVendorAdapter;
    }

    /**
     * Configures the JpaTransactionManager
     *
     * @return PlatformTransactionManager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager(
                this.entityManagerFactoryBean().getObject());
        return transactionManager;
    }

    /**
     * Database configuration
     *
     * @return DataSource
     */
    @Bean
    public DataSource dataSource() {

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(dataSourceDriver);
        basicDataSource.setUsername(dataSourceUser);
        basicDataSource.setPassword(dataSourcePassword);
        basicDataSource.setUrl(dataSourceUrl);

		/* if (logger.isDebugEnabled()) { 
            logger.debug("dataSource configuration [" + "Driver=" 
                    + basicDataSource.getDriverClassName() + ", " + "User=" 
                    + basicDataSource.getUsername() + ", " + "Password=" 
                    + basicDataSource.getPassword() + ", " + "dataSourceUrl=" 
                    + basicDataSource.getUrl() + "]");  
        } */

        return basicDataSource;
    }

    /**
     * This method is used to add hibernate properties.
     *
     * @return Properties
     */
    private Properties additionalProperties() {
        final Properties properties = new Properties();
        properties.setProperty(HIBERNATE_HBM2DDL_AUTO, hbm2ddl);
        properties.setProperty(HIBERNATE_DIALECT, dialect);
        properties.setProperty(HIBERNATE_SHOW_SQL, showSql);
        return properties;
    }

    /**
     * @return CommonsMultipartResolver
     * This bean is for file uploading
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setMaxUploadSize(50000000);
        return commonsMultipartResolver;

    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() throws IOException {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(Boolean.TRUE);
        //propertySourcesPlaceholderConfigurer.setLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:META-INF/props/default/*.properties"));
        propertySourcesPlaceholderConfigurer.setLocations(new PathMatchingResourcePatternResolver().getResources("file:/usr/local/etc/tewvservice.properties"));
        return propertySourcesPlaceholderConfigurer;
    }

    /**
     * @Value("${email.host}") private String host;
     * @Value("${email.from}") private String from;
     * @Value("${email.subject}") private String subject;
     */

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.mail.yahoo.com");
        javaMailSender.setPort(587);
        javaMailSender.setProtocol("smtp");
        javaMailSender.setUsername("someuser");
        javaMailSender.setPassword("somepasswprd");
        Properties prop = javaMailSender.getJavaMailProperties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.debug", "true");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return javaMailSender;
    }

    //Pre populated messages
    @Bean
    public SimpleMailMessage simpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("from");
        simpleMailMessage.setSubject("subject");
        return simpleMailMessage;
    }
    
 	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(clientHttpRequestFactory());
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        return factory;
    }


} 