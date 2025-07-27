package com.example.rss;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;
import org.hsqldb.lib.tar.DbBackupMain;
import org.hsqldb.lib.tar.TarMalformatException;
import org.hsqldb.util.DatabaseManagerSwing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.rss.db.DbOperations;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableFeignClients(basePackages = "com.example.rss")
public class Application {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    public static final String RESTORE_FOLDER = System.getProperty("user.home") + "/restoreFolder";
    /** Active or not hsqldb explorer */
    private static final boolean DEBUG_DB = true;

    @Value("${spring.datasource.url}")
    private String databaseSourceUrl;

    @Autowired
    void setEnvironment(Environment e) {
        LOG.info("Project {} is sterting...", e.getProperty("configuration.projectName"));
    }

    /**
     * Application jar launcher.
     *
     * @param args
     * @throws TarMalformatException
     * @throws IOException
     * @throws SQLException
     */
    public static void main(String[] args) throws IOException, TarMalformatException, InterruptedException {
        restoreHsqlDb();
        SpringApplication.run(Application.class, args);
        Thread.sleep(5000l);
        runDatabaseManager();

    }

    /**
     * Run hsqldb explorer.
     */
    private static void runDatabaseManager() {
        if (DEBUG_DB) {
            System.setProperty("java.awt.headless", "false");
            DatabaseManagerSwing.main(new String[] { "--url", "jdbc:hsqldb:file:~/restoreFolder/rss", "-noexit" });

        }
    }

    private static void restoreHsqlDb() throws IOException, TarMalformatException {
        if (DbOperations.baseDir.exists()) {
            LOG.info("Start Db restore from {}", DbOperations.baseDir.getAbsolutePath());
            File restoreFolderFile = new File(RESTORE_FOLDER);
            if (restoreFolderFile.exists()) {
                FileUtils.cleanDirectory(restoreFolderFile);
            } else {
                boolean isCreated = restoreFolderFile.mkdir();
                if (!isCreated) {
                    LOG.error("Dir can't be created. Backup failed from file: {}", restoreFolderFile.getAbsolutePath());
                    return;
                }
            }

            DbBackupMain.main(new String[] { "--extract", DbOperations.baseDir.getAbsolutePath(),
                    System.getProperty("user.home") + "/restoreFolder" });
        } else {
            LOG.info("No db restore is made. There is no file in {}", DbOperations.baseDir.getAbsolutePath());
        }
    }
}
