package com.pinitservicfes.intro.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@FieldNameConstants
public class BasicEntity {
    @Id
    protected String id;

    @LastModifiedDate
    protected long lastUpdate;

    @CreatedDate
    protected long creationDate;

    public void setId(final String id) {

        if (id != null && id.isEmpty()) {
            this.id = null;
        } else {
            this.id = id;
        }

    }
}
