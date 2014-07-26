package com.lylynx.hideout.db.mongo.script;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 23.07.14
 * Time: 01:29
 */
public class Script {

    private String id;
    private Date runDate;
    private String fileName;
    private String checksum;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Date getRunDate() {
        return runDate;
    }

    public void setRunDate(final Date runDate) {
        this.runDate = runDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(final String checksum) {
        this.checksum = checksum;
    }
}
