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
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
		"uk.co.luciditysoftware.actsintown.api.security",
		"uk.co.luciditysoftware.actsintown.api.utilities",
		"uk.co.luciditysoftware.actsintown.data.repositories"
}, 
excludeFilters = @ComponentScan.Filter(Controller.class) )
@EnableTransactionManagement
@Import({ SecurityConfiguration.class })
@PropertySource("file:/serversettings/actsintown/config.properties")
public class RootContextConfiguration {
	
	@Autowired
	private Environment environment;
	
	@Bean(name = "dataSource")
	public DataSource dataSource() {
	    BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    dataSource.setUrl(environment.getProperty("dbUrl"));
	    dataSource.setUsername(environment.getProperty("dbUsername"));
	    dataSource.setPassword(environment.getProperty("dbPassword"));
	    return dataSource;
	}
	
	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory sessionFactory(DataSource dataSource) {
	    LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
	    sessionBuilder.setProperty("connection.release_mode", "on_close");
	    sessionBuilder.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
        sessionBuilder.setProperty("hibernate.show_sql", "false");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/Role.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/Permission.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/RolePermission.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/User.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/County.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/Town.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/Spot.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/UserType.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/UserUserType.hbm.xml");
        sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/Conversation.hbm.xml");
        sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/Message.hbm.xml");
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
	
    @Bean(name = "mailSender")
    public JavaMailSender javaMailService() {
            JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(environment.getProperty("emailHost"));
        javaMailSender.setPort(Integer.parseInt(environment.getProperty("emailPort")));
        javaMailSender.setUsername(environment.getProperty("emailUsername"));
        javaMailSender.setPassword(environment.getProperty("emailPassword"));
        javaMailSender.setProtocol(environment.getProperty("emailProtocol"));
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.debug", true);
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }
}
