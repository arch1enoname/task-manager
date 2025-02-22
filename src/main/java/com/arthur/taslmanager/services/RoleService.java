package com.arthur.taslmanager.services;

import com.arthur.taslmanager.entities.Role;

public interface RoleService {
    Role findByName(String name);
}
