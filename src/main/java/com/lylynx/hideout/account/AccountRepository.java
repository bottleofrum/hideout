package com.lylynx.hideout.account;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 29.06.14
 * Time: 16:59
 */
public interface AccountRepository extends MongoRepository<Account,String>{

    Account findOneByEmail(String email);

}
