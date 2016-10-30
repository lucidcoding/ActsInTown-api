package uk.co.luciditysoftware.actsintown.api.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = {
		"uk.co.luciditysoftware.actsintown.api.controllers",
		"uk.co.luciditysoftware.actsintown.api.config",
		"uk.co.luciditysoftware.actsintown.api.filters",
		"uk.co.luciditysoftware.actsintown.api.mappers",
		"uk.co.luciditysoftware.actsintown.api.services",
		"uk.co.luciditysoftware.actsintown.data.repositories"
}, 
excludeFilters = @ComponentScan.Filter(Controller.class) )
@EnableTransactionManagement
@Import({ SecurityConfiguration.class })
public class RootContextConfiguration {
	@Bean(name = "dataSource")
	public DataSource dataSource() {
	    BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    dataSource.setUrl("jdbc:mysql://localhost:3306/actsintown");
	    dataSource.setUsername("root");
	    dataSource.setPassword("");
	    return dataSource;
	}
	
	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory sessionFactory(DataSource dataSource) {
	    LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
	    sessionBuilder.setProperty("connection.release_mode", "on_close");
	    sessionBuilder.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/Role.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/Permission.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/RolePermission.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/User.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/County.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/Town.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/Spot.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/UserType.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/UserUserType.hbm.xml");
	    return sessionBuilder.buildSessionFactory();
	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager transactionManager(
	        SessionFactory sessionFactory) {

		HibernateTransactionManager tm = new HibernateTransactionManager(
	            sessionFactory);
	    return tm;
	}
	
	@Autowired
	@Bean(name = "modelMapper")
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		modelMapper
			.getConfiguration()
			.setFieldMatchingEnabled(true)
			.setMethodAccessLevel(AccessLevel.PRIVATE);
		
		return modelMapper;
	}
	
	//http://www.programcreek.com/java-api-examples/index.php?api=org.springframework.mail.javamail.JavaMailSender
	//http://stackoverflow.com/questions/24097131/spring-4-mail-configuration-via-java-config
	//https://www.siteground.com/kb/google_free_smtp_server/
	//http://stackoverflow.com/questions/2016190/how-to-configure-spring-javamailsenderimpl-for-gmail
	//http://stackoverflow.com/questions/17786132/how-to-implements-an-async-email-service-in-spring
	@Bean(name = "mailSender")
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);//587//25?
        javaMailSender.setUsername("actsintown@gmail.com");
        javaMailSender.setPassword("NotMuchUse");
        javaMailSender.setProtocol("smtp");
        Properties properties = new Properties();
        //properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.debug", true);
        /*properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.quitwait", false);
        properties.put("mail.smtp.ssl.trust", "*");*/
        

        /*properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", false);
        properties.put("mail.smtp.quitwait", false);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", false);
        properties.put("mail.debug", true);*/
        
        
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }
}
