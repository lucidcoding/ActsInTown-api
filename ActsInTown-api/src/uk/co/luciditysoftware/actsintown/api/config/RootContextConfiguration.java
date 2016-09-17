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
}
