package com.atrebit.inventory_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.odoojava.api.Session;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
public class OdooConfig {
    @Value("${application.odoo.host}")
    private String odooHost;
    
    @Value("${application.odoo.port}")
    private int odooPort;

    @Value("${application.odoo.database}")
    private String odooDatabase;

    @Value("${application.odoo.username}")
    private String odooUsername;

    @Value("${application.odoo.password}")
    private String odooPassword;

    @Bean
    public Session session(){
        Session session = new Session(
                odooHost,
                odooPort,
                odooDatabase,
                odooUsername,
                odooPassword
        );
        try{    
            session.startSession();
        }
        catch(Exception ex){
            log.error(String.format("Error starting odoo session: %s", ex.getMessage()));
        }

        return session;
    }
}
