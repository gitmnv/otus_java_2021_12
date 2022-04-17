package ru.otus.demo;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DbServiceClientImpl;

import java.util.List;

public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
///
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        //для создания базового набора записей в бд.
        for (int i = 0; i < 20; i++) {
           dbServiceClient.saveClient(new Client("dbServiceFirst", new Address("address1"),
                    List.of(new Phone("222-333"))));
        }


        dbServiceClient.getClient(1L);
        dbServiceClient.getClient(4L);
        dbServiceClient.getClient(7L);
        dbServiceClient.getClient(10L);
        dbServiceClient.getClient(13L);
        dbServiceClient.getClient(16L);
        dbServiceClient.getClient(19L);
        dbServiceClient.getClient(22L);
        dbServiceClient.getClient(25L);
        dbServiceClient.getClient(28L);
        dbServiceClient.getClient(31L);
        dbServiceClient.getClient(34L);
        dbServiceClient.getClient(37L);
        dbServiceClient.getClient(40L);
        dbServiceClient.getClient(43L);
        dbServiceClient.getClient(46L);
        dbServiceClient.getClient(49L);
        dbServiceClient.getClient(52L);
        dbServiceClient.getClient(55L);
        dbServiceClient.getClient(58L);

        System.out.println("---------Cache---------");

        dbServiceClient.getClient(1L);
        dbServiceClient.getClient(4L);
        dbServiceClient.getClient(7L);
        dbServiceClient.getClient(10L);
        dbServiceClient.getClient(13L);
        dbServiceClient.getClient(16L);
        dbServiceClient.getClient(19L);
        dbServiceClient.getClient(22L);
        dbServiceClient.getClient(25L);
        dbServiceClient.getClient(28L);
        dbServiceClient.getClient(31L);
        dbServiceClient.getClient(34L);
        dbServiceClient.getClient(37L);
        dbServiceClient.getClient(40L);
        dbServiceClient.getClient(43L);
        dbServiceClient.getClient(46L);
        dbServiceClient.getClient(49L);
        dbServiceClient.getClient(52L);
        dbServiceClient.getClient(55L);
        dbServiceClient.getClient(58L);

    }
}
