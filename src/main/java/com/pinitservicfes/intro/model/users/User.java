package com.pinitservicfes.intro.model.users;

import lombok.*;
import lombok.extern.java.Log;

import java.util.List;
import java.util.Map;

/**
 *
 */
@Log
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String userId;
    private List<String> roles;

    public Map<String, Object> toMap() {
        return Map.of("id", userId, "roles", roles);
    }

}
