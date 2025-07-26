package com.example.rss.controller;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.rss.db.DbOperations;

@Controller
public class DbBackupController {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(DbBackupController.class);

    private static final String BACKUP_FAILED = "Backup failed !!!";
    private static final String BACKUP_SUCCESS = "Backup is successfully accomplished.";

    private static final String BACKUP_DELETED_FAILED = "Backup deletion failed !!!";
    private static final String BACKUP_DELETED_SUCCESS = "Backup deletion is successfully accomplished.";
    private static final String BACKUP_MISSING = "NOthing to delete. Backup missing.";

    @Autowired
    private DbOperations dbOperations;

    @RequestMapping(value = "/dobackup", method = RequestMethod.GET)
    @ResponseBody
    public String backup() {
        try {
            dbOperations.mainBackupAndRestore();
            return BACKUP_SUCCESS;
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return BACKUP_FAILED;
        }
    }

    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    @ResponseBody
    public String clear() {
        if (dbOperations.baseDir.exists()) {
            try {
                FileUtils.forceDelete(dbOperations.baseDir);
                return BACKUP_DELETED_SUCCESS;
            } catch (IOException e) {
                LOG.error(e.getMessage());
                return BACKUP_DELETED_FAILED;
            }
        }
        LOG.info(BACKUP_MISSING);
        return BACKUP_MISSING;
    }

}
