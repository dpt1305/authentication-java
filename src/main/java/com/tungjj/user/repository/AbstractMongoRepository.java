package com.tungjj.user.repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.tungjj.user.exception.BadSqlException;
import com.tungjj.user.log.AppLogger;
import com.tungjj.user.log.LoggerFactory;
import com.tungjj.user.log.LoggerType;

public abstract class AbstractMongoRepository {
    @Autowired
    @Qualifier("mongo_template")
    protected MongoTemplate authenticationTemplate;

    protected AppLogger APP_LOGGER = LoggerFactory.getLogger(LoggerType.APPLICATION);

    /**
     * Generate query for mongoDB
     * 
     * @param allParams params from controller
     * @param clazz     class on db
     * @param keySort
     * @param sortField
     * @param page
     * @param pageSize  size of page
     * @return Query create query for mongoDB
     */
    protected Query generateQueryMongoDB(Map<String, String> allParams, Class<?> clazz, String keySort,
            String sortField, int page, int pageSize) {
        Query query = new Query();

        Field[] fields = clazz.getDeclaredFields();
        Criteria criteria = new Criteria();

        for (Map.Entry<String, String> items : allParams.entrySet()) {
            for (Field field : fields) {
                if (field.getName().compareTo(items.getKey()) == 0) {
                    String[] values = items.getValue().split(",");
                    CheckType(criteria, field.getClass(), values, items);
                }
            }
        }
        query.addCriteria(criteria);
        if (keySort.trim().compareTo("") != 0 && sortField.trim().compareTo("ASC") != 0) {
            query.with(Sort.by(Sort.Direction.ASC, sortField));
        }
        if (keySort.trim().compareTo("") != 0 && sortField.trim().compareTo("DESC") != 0) {
            query.with(Sort.by(Sort.Direction.DESC, sortField));
        }
        if (page > 0 && pageSize > 0) {
            Pageable pageable = PageRequest.of(page - 1, pageSize);
            query.with(pageable);
        }
        return query;
    }

    /**
     * Create query for find in mongo
     * 
     * @param <T>
     * @param query
     * @param clazz
     * @return
     */
    protected <T> Optional<List<T>> replaceFind(Query query, Class<T> clazz) {
        try {
            List<T> result = authenticationTemplate.find(query, clazz);
            return Optional.of(result);
        } catch (IllegalArgumentException e) {
            APP_LOGGER.error(e.getMessage());
            return Optional.empty();
        } catch (NullPointerException e) {
            APP_LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Create query for findOne in mongo
     * 
     * @param <T>
     * @param query
     * @param clazz
     * @return
     */
    protected <T> Optional<T> replaceFindOne(Query query, Class<T> clazz) {
        try {
            T result = authenticationTemplate.findOne(query, clazz);
            return Optional.of(result);
        } catch (IllegalArgumentException e) {
            APP_LOGGER.error(e.getMessage());
            return Optional.empty();
        } catch (NullPointerException e) {
            APP_LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    protected void CheckType(Criteria criteria, Class<?> type, String[] values, Map.Entry<String, String> items) {
        if (type == ObjectId.class) {
            List<Criteria> multipleCriteria = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                try {
                    multipleCriteria.add(Criteria.where(items.getKey()).is(new ObjectId(values[i])));
                } catch (IllegalArgumentException e) {
                    APP_LOGGER.error(e.getMessage());
                    throw new BadSqlException("id must be objectId format!");
                }
            }
            criteria.orOperator(multipleCriteria);
        }
        if (type == Boolean.class) {
            List<Criteria> multipleCriteria = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                try {
                    boolean value = Boolean.parseBoolean(values[i]);
                    multipleCriteria.add(Criteria.where(items.getKey()).is(value));
                } catch (Exception e) {
                    APP_LOGGER.error("error parsing value boolean");
                }
            }
            criteria.orOperator(multipleCriteria);
        }
        if (type == int.class) {
            List<Criteria> multipleCriteria = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                try {
                    int value = Integer.parseInt(values[i]);
                    multipleCriteria.add(Criteria.where(items.getKey()).is(value));
                } catch (Exception e) {
                    APP_LOGGER.error("error parsing value int");
                }
            }
            criteria.orOperator(multipleCriteria);
        }
        if (type == String.class) {
            List<Criteria> multipleCriteria = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                multipleCriteria.add(Criteria.where(items.getKey()).is(values[i]));
            }
            criteria.orOperator(multipleCriteria);
        }
    }
}
