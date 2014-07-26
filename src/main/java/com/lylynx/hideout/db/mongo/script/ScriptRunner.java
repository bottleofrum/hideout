package com.lylynx.hideout.db.mongo.script;

import com.google.common.base.Charsets;
import com.lylynx.hideout.config.Constants;
import com.mongodb.DB;
import org.apache.commons.io.IOUtils;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 23.07.14
 * Time: 01:26
 */
public class ScriptRunner {

    public static final String HIDEOUT_DB_PATH = "hideout/db/";
    private final ScriptRunnerRepository scriptRunnerRepository;
    private final MongoDbFactory mongoDbFactory;

    public ScriptRunner(final ScriptRunnerRepository scriptRunnerRepository, MongoDbFactory mongoDbFactory) {

        this.scriptRunnerRepository = scriptRunnerRepository;
        this.mongoDbFactory = mongoDbFactory;
    }

    public void init() throws IOException, NoSuchAlgorithmException {

        final List<String> files =
                IOUtils.readLines(ScriptRunner.class.getClassLoader().getResourceAsStream(HIDEOUT_DB_PATH), Charsets.UTF_8.toString());
        for (String file : files) {
            try (InputStream is = ScriptRunner.class.getClassLoader().getResourceAsStream(HIDEOUT_DB_PATH+file)) {
                final byte[] fileAsBytes = IOUtils.toByteArray(is);
                final String checksum = DigestUtils.md5DigestAsHex(fileAsBytes);
                final Script script = scriptRunnerRepository.findByChecksum(checksum);
                if(null == script) {
                    System.out.println("Running "+ file);
                    final DB db = mongoDbFactory.getDb();
                    String host = getHost(db);
                    int port = getPort(db);
                    final Object result = db.eval(new String(fileAsBytes), host, port, Constants.MONGO_DATABASE);
                    persistScriptInfo(file, checksum);
                }
            }
        }

    }

    private int getPort(final DB db) {
        return db.getMongo().getAddress().getPort();
    }

    private String getHost(final DB db) {
        return db.getMongo().getAddress().getHost();
    }

    private void persistScriptInfo(final String file, final String checksum) {
        Script script = new Script();
        script.setFileName(file);
        script.setChecksum(checksum);
        script.setRunDate(new Date());

        scriptRunnerRepository.save(script);
    }

}
