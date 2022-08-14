package com.pinitservicfes.intro;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.pinitservicfes.intro.model.BasicEntity;

import java.util.List;
import java.util.stream.Stream;

public class MongoDBUtils {

    public static Query fieldQuery(String field, String value) {
        return Query.query(Criteria.where(field).is(value));
    }

    public static Query id(String id) {
        return fieldQuery(BasicEntity.Fields.id, id);
    }

    public static Criteria idCriteria(String id) {
        return Criteria.where(BasicEntity.Fields.id).is(id);
    }

    public static Update set(String key, Object value) {
        return new Update().set(key, value);
    }

    public static List<Criteria> or(String key, List<?> values) {
        return values.stream().map(value -> new Criteria(key).is(value)).toList();
    }

    public static Criteria orCriteria(String key, Object... values) {
        return new Criteria().orOperator(or(key, values));
    }

    public static Criteria orCriteria(String key, List<?> values) {
        return new Criteria().orOperator(or(key, values));
    }

    public static List<Criteria> or(String key, Object... values) {
        return Stream.of(values).map(value -> new Criteria(key).is(value)).toList();
    }

    public static List<Criteria> or(String key, Stream stream) {
        return stream.map(value -> new Criteria(key).is(value)).toList();
    }

}
