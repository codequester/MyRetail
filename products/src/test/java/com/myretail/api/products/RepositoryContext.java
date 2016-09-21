package com.myretail.api.products;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

/**
 *  @author Shankar Govindarajan
 */

@Configuration
@EnableMongoRepositories
@ComponentScan(excludeFilters = {
		@Filter(type=FilterType.ASSIGNABLE_TYPE,classes=Application.class),
		@Filter(type=FilterType.ANNOTATION,classes=Service.class)
	})

public class RepositoryContext {

	MongodExecutable mongodExecutable;
	

	  public @Bean MongoClient mongoClient() throws Exception {
			MongodStarter starter = MongodStarter.getDefaultInstance();
		    IMongodConfig mongodConfig = new MongodConfigBuilder()
		    									.version(Version.Main.PRODUCTION)
		    									.net(new Net(28901, Network.localhostIsIPv6()))
		    									.build();
		    MongodExecutable mongodExecutable  = starter.prepare(mongodConfig);
	        mongodExecutable.start();
	        return new MongoClient("localhost", 28901);
	  }

	  public @Bean MongoTemplate mongoTemplate() throws Exception {
		  return new MongoTemplate(mongoClient(), "test");
	  }	
	
	  @PostConstruct
	  public void init() throws Exception {
		  MongoDatabase db = mongoClient().getDatabase("test");
		  db.getCollection("product").insertOne(TestUtils.buildTestDocument("100", "12.35", "USD"));
		  db.getCollection("product").insertOne(TestUtils.buildTestDocument("101", "13.35", "USD"));
		  db.getCollection("product").insertOne(TestUtils.buildTestDocument("102", "123.35", "USD"));
	  }
	
	  @PreDestroy
	  public void destroy() throws Exception	{
		  mongoClient().close();			  
		  if (mongodExecutable != null)
			  mongodExecutable.stop();
	  }
}
