package com.pinitservicfes.intro.model.users;

import java.util.List;

import com.pinitservicfes.intro.model.BasicEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBase extends BasicEntity {

    protected String username;
    protected String firstName;
    protected String lastName;
    protected long dateOfBirth;

    protected List<String> roles;
}
