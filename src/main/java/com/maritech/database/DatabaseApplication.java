package com.maritech.database;

import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

@SpringBootApplication
@Log
public class DatabaseApplication  {


	public static void main(String[] args) {
		SpringApplication.run(DatabaseApplication.class, args);
	}


}
