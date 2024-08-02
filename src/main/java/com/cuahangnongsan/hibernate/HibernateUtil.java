package com.cuahangnongsan.hibernate;

import com.cuahangnongsan.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

public class HibernateUtil {
    private final static SessionFactory FACTORY;
    static {
        Configuration conf = new Configuration();
        Properties pros = new Properties();
        pros.put(Environment.DIALECT,"org.hibernate.dialect.MySQLDialect");
        pros.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
        pros.put(Environment.URL,"jdbc:mysql://localhost:3309/cuahangnongsan");
        pros.put(Environment.USER,"root");
        pros.put(Environment.PASS,"190103");

        conf.setProperties(pros);
        conf.addAnnotatedClass(Category.class);
        conf.addAnnotatedClass(Invoice.class);
        conf.addAnnotatedClass(InvoiceDetail.class);
        conf.addAnnotatedClass(Permission.class);
        conf.addAnnotatedClass(Product.class);
        conf.addAnnotatedClass(Role.class);
        conf.addAnnotatedClass(User.class);

        ServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .applySettings(conf.getProperties()).build();
        FACTORY = conf.buildSessionFactory(registry);

    }

    @Bean
    public static SessionFactory getFACTORY() {
        return FACTORY;
    }
}
