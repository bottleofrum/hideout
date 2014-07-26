package com.lylynx.hideout.db.mongo.script;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 23.07.14
 * Time: 01:28
 */
public interface ScriptRunnerRepository extends MongoRepository<Script, String> {

    public Script findByChecksum(String checksum);

}
