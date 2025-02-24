package com.arthur.taslmanager.services.impls;

import com.arthur.taslmanager.entities.Role;
import com.arthur.taslmanager.exceptions.RoleNotFoundException;
import com.arthur.taslmanager.repositories.RoleRepository;
import com.arthur.taslmanager.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        Optional<Role> optionalRole = roleRepository.findByName(name);
        if (optionalRole.isPresent()) {
            return optionalRole.get();
        } else {
            throw new RoleNotFoundException("Role with name" + name + "doesn`t exist");
        }
    }
}
