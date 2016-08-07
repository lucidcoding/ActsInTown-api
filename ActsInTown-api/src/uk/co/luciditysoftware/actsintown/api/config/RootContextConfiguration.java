package uk.co.luciditysoftware.actsintown.api.config;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;


//Try this to get integrated security working: http://stackoverflow.com/questions/11707056/no-sqljdbc-auth-in-java-library-path

@Configuration
@ComponentScan(basePackages = {
		"uk.co.luciditysoftware.actsintown.api.controllers",
		"uk.co.luciditysoftware.actsintown.api.config",
		"uk.co.luciditysoftware.actsintown.api.filters",
		"uk.co.luciditysoftware.actsintown.data.repositories"
}, 
excludeFilters = @ComponentScan.Filter(Controller.class) )
@EnableTransactionManagement
@Import({ SecurityConfiguration.class })
//@Import({ ServerSecurityConfig.class, AuthServerOAuth2Config.class })
public class RootContextConfiguration {
	@Bean(name = "dataSource")
	public DataSource dataSource() {
	    BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    dataSource.setUrl("jdbc:sqlserver://localhost:1433;databaseName=ActsInTown;integratedSecurity=true;");
	    
	    //dataSource.setUrl("jdbc:sqlserver://localhost:1433;databaseName=ActsInTown;");
	    //dataSource.setUsername("");
	    //dataSource.setPassword("");
	    return dataSource;
	}
	
	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory sessionFactory(DataSource dataSource) {
	    LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
	    //sessionBuilder.addAnnotatedClasses(User.class);
	    sessionBuilder.setProperty("connection.release_mode", "on_close");
	    //sessionBuilder.setProperty("hibernate.current_session_context_class", "thread");
	    sessionBuilder.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
	    /*sessionBuilder.addResource("/uk/co/luciditysoftware/campervibe/data/mappings/Depot.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/campervibe/data/mappings/Vehicle.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/campervibe/data/mappings/Booking.hbm.xml");*/
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/Role.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/Permission.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/RolePermission.hbm.xml");
	    sessionBuilder.addResource("/uk/co/luciditysoftware/actsintown/data/mappings/User.hbm.xml");
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
}
