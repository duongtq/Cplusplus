package com.predator.springbootjournal;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.predator.springbootjournal.service.JournalService;

@SpringBootApplication
public class SpringBootJournalApplication implements CommandLineRunner {
	private final static Logger log = LoggerFactory.getLogger(SpringBootJournalApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringBootJournalApplication.class, args);
	}

	@Autowired
	JournalService service;

	@Override
	public void run(String... args) throws ParseException {
		log.info(">> Inserting data...");
		service.insertData();
		log.info(">> Find all data...");
		service.findAll().forEach(entry -> log.info(entry.toString()));
	}
}
