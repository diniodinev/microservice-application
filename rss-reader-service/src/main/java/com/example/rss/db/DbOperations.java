package com.example.rss.db;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.rss.config.RssConfiguration;
import com.example.rss.web.resources.TemporalFormat;

@Component
public class DbOperations {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(RssConfiguration.class);
    public static final File baseDir = new File("src/main/resources/hsql/Backup.tgz");

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public void mainBackupAndRestore() throws IOException {
        File sourceFile = new File(baseDir.getAbsolutePath());
        File targetFile = new File(baseDir.getParent(),
                "OLD_BACKUPS/" +
                new SimpleDateFormat(TemporalFormat.FILE_TIMESTAMP).format(new Date()));

        if (sourceFile.exists()) {
            LOG.debug("Copiing old backup from %s to %s", sourceFile, targetFile);
            FileUtils.copyFileToDirectory(sourceFile, targetFile, true);
            FileUtils.forceDelete(sourceFile);
        }

        jdbcTemplate.execute("BACKUP DATABASE TO '" + sourceFile.getAbsolutePath() + "' BLOCKING");
    }
}
