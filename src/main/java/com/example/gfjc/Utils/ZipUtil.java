package com.example.gfjc.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @title ZipUtil
 * @Author: ZKY
 * @CreateTime: 2024-04-01  22:00
 * @Description: TODO
 */
public class ZipUtil {
    public static void addToZip(ZipOutputStream zos, File file, String entryName) throws IOException {
        ZipEntry zipEntry = new ZipEntry(entryName);
        zos.putNextEntry(zipEntry);

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
        }

        zos.closeEntry();
    }
}
