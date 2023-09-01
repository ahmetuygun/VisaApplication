package com.krakozhia.visa.configuration;

import com.atomikos.jms.AtomikosConnectionFactoryBean;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import org.apache.activemq.artemis.jms.client.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class JMSConfiguration {

    @Value("${jms.url}")
    String jmsUrl;

    @Bean
    public JmsTemplate jmsTemplate() throws Throwable {
        return new JmsTemplate(connectionFactory());
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public ConnectionFactory connectionFactory() throws JMSException {
        ActiveMQXAConnectionFactory activeMQXAConnectionFactory = new
                ActiveMQXAConnectionFactory();
        activeMQXAConnectionFactory.setBrokerURL(jmsUrl);
        AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
        atomikosConnectionFactoryBean.setUniqueResourceName("xamq");
        atomikosConnectionFactoryBean.setLocalTransactionMode(false);
        atomikosConnectionFactoryBean.setXaConnectionFactory(activeMQXAConnectionFactory);
        return atomikosConnectionFactoryBean;
    }


}
