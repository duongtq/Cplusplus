/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/

package com.predator.springbootjournal.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.predator.springbootjournal.domain.Journal;
import com.predator.springbootjournal.repository.JournalRepository;

@Service
public class JournalService {
    private static final Logger log = LoggerFactory.getLogger(JournalService.class);

    @Autowired
    JournalRepository journalRepository;

    public void insertData() throws ParseException {
        log.info(">> Table creation");
        log.info(">> Insert into table");
        journalRepository.save(new Journal("Predator", "Fatal", "09-03-1998"));
        journalRepository.save(new Journal("Alien", "Agile", "01-01-1968"));
        journalRepository.save(new Journal("Iron Man", "Geek", "08-04-2018"));
        journalRepository.save(new Journal("Captain Frost", "Muscle", "22-5-2050"));
    }

    public List<Journal> findAll() {
        return journalRepository.findAll();
    }
}
