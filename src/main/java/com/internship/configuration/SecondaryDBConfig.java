package com.internship.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "secondaryEntityManagerFactory",
    transactionManagerRef = "secondaryTransactionManager",
    basePackages = {"com.internship.tabulacore.repository"})
public class SecondaryDBConfig {

  private Environment env;

  public SecondaryDBConfig(Environment env) {
    this.env = env;
  }

  @Bean(name = "secondaryDataSource")
  @ConfigurationProperties(prefix = "spring.tabulacore")
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("tabulacore.datasource.driver-class-name"));
    dataSource.setUrl(env.getProperty("spring.tabulacore.datasource.url"));
    dataSource.setUsername(env.getProperty("spring.tabulacore.datasource.username"));
    dataSource.setPassword(env.getProperty("spring.tabulacore.datasource.password"));

    return dataSource;
  }

  @Bean(name = "secondaryEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier("secondaryDataSource") DataSource dataSource) {
    return builder.dataSource(dataSource).packages("com.internship.tabulacore.entity").build();
  }

  @Bean(name = "secondaryTransactionManager")
  public PlatformTransactionManager secondaryTransactionManager(
      @Qualifier("secondaryEntityManagerFactory")
          EntityManagerFactory secondaryEntityManagerFactory) {
    return new JpaTransactionManager(secondaryEntityManagerFactory);
  }
}
