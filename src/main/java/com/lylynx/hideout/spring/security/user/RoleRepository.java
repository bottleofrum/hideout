package com.lylynx.hideout.spring.security.user;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 28.07.14
 * Time: 22:11
 */
public interface RoleRepository extends MongoRepository<HideoutGrantedAuthority, String> {
}
