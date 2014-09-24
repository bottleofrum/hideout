package com.lylynx.hideout.spring.security.access;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 23.07.14
 * Time: 23:21
 */
public interface WebAccessRuleRepository extends MongoRepository<WebAccessRule, String> {
}
