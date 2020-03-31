package com.springboot.todojdbc.repository;

import com.springboot.todojdbc.domain.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ToDoRepository implements CommonRepository<ToDo> {
    private static final String SQL_INSERT =
            "INSERT INTO `todo`(id, description, created, modified, completed)" +
            "VALUES(:id, :description, :created, :modified, :completed)";

    private static final String SQL_QUERY_FIND_ALL =
            "SELECT `id`, `description`, `created`, `modified`, `completed` " + "FROM `todo`";

    private static final String SQL_QUERY_FIND_BY_ID =
            SQL_QUERY_FIND_ALL + " WHERE `id` = :id";

    private static final String SQL_UPDATE =
            "UPDATE `todo` SET `description` = :description, `modified` = :modified, `completed` = :completed WHERE `id` = :id";

    private static final String SQL_DELETE =
            "DELETE FROM `todo` WHERE `id` = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ToDoRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<ToDo> toDoRowMapper = (ResultSet rs, int rowNum) -> {
        ToDo toDo = new ToDo();
        toDo.setId(rs.getString("id"));
        toDo.setDescription(rs.getString("description"));
        toDo.setModified(rs.getTimestamp("modified").toLocalDateTime());
        toDo.setCreated(rs.getTimestamp("created").toLocalDateTime());
        toDo.setCompleted(rs.getBoolean("completed"));
        return toDo;
    };

    @Override
    public ToDo save(final ToDo domain) {
        ToDo result = findById(domain.getId());
        if(result != null){
            result.setDescription(domain.getDescription());
            result.setCompleted(domain.isCompleted());
            result.setModified(LocalDateTime.now());
            return upsert(result, SQL_UPDATE);
        }
        return upsert(domain,SQL_INSERT);
    }

    private ToDo upsert(final ToDo toDo, final String sql){
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("id",toDo.getId());
        namedParameters.put("description",toDo.getDescription());
        namedParameters.put("created",java.sql.Timestamp.valueOf(toDo.getCreated()));
        namedParameters.put("modified",java.sql.Timestamp.valueOf(toDo.getModified()));
        namedParameters.put("completed",toDo.isCompleted());

        this.jdbcTemplate.update(sql,namedParameters);

        return findById(toDo.getId());
    }

    @Override
    public Iterable<ToDo> save(Collection<ToDo> domains) {
        domains.forEach( this::save);
        return findAll();
    }

    @Override
    public void delete(final ToDo domain) {
        Map<String, String> namedParameters = Collections.singletonMap("id", domain.getId());
        this.jdbcTemplate.update(SQL_DELETE,namedParameters);
    }

    @Override
    public ToDo findById(String id) {
        try {
            Map<String, String> namedParameters = Collections.singletonMap("id", id);
            return this.jdbcTemplate.queryForObject(SQL_QUERY_FIND_BY_ID, namedParameters, toDoRowMapper);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Iterable<ToDo> findAll() {
        return this.jdbcTemplate.query(SQL_QUERY_FIND_ALL, toDoRowMapper);
    }
}
