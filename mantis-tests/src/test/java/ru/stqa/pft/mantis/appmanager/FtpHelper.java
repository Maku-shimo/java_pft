package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;
import sun.net.ftp.FtpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FtpHelper {
    private final ApplicationManager app;
    private FTPClient ftp;

    public FtpHelper(ApplicationManager app) {
        this.app = app;
        ftp = new FTPClient();
    }

    public void upload(File file, String target, String backup) throws IOException {
        // соединяюсь с фтп сервером
        ftp.connect(app.getProperty("ftp.host"));
        ftp.login(app.getProperty("ftp.login"),app.getProperty("ftp.password"));

        // удаляю бэкап передаваемого файла
        ftp.deleteFile(backup);

        // переименую целевой файл в бэкап
        ftp.rename(target, backup);

        // передаю свой файл
        ftp.enterLocalPassiveMode();
        ftp.storeFile(target, new FileInputStream(file));

        // отключаюсь
        ftp.disconnect();

    }

    public void restore(String backup, String target) throws IOException {
        // соединяюст с сервером
        ftp.connect(app.getProperty("ftp.host"));
        ftp.login(app.getProperty("ftp.login"),app.getProperty("ftp.password"));

        // удаляю целевой файл
        ftp.deleteFile(target);

        // переименую бэкап в целевой
        ftp.rename(backup, target);

        // отключаюсь
        ftp.disconnect();
    }

}
