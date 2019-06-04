package com.neo.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by xzm on 2019/6/4.
 */
@Configuration
@PropertySource(value = "classpath:elasticsearch.properties")
@EnableElasticsearchRepositories(basePackages = "com.neo.repository")
public class ElasticsearchConfiguration {
    @Resource
    Environment environment;

    @Bean
    public Client client() {
        Client client = null;
        try {
            Settings.Builder settings = Settings.builder().put("client.transport.sniff", false);
            settings.put("cluster.name", "docker-cluster");
            client = new PreBuiltTransportClient(settings.build())
                    .addTransportAddress(new InetSocketTransportAddress(
                            InetAddress.getByName(environment.getProperty("elasticsearch.host")),
                            Integer.parseInt(environment.getProperty("elasticsearch.port"))));


        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

//        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
//        TransportAddress address = null;
//        try {
//            address = new InetSocketTransportAddress(InetAddress.getByName(environment.getProperty("elasticsearch.host")), Integer.parseInt(environment.getProperty("elasticsearch.port")));
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        client.addTransportAddress(address);
        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(client());
    }

}
