package cn.dlysxx.www.common.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

/**
 * Utility Class for compressing/decompressing.
 *
 * @author shuai
 **/
public class ZipUtil {

    /**
     * Compress files into one single zip file.
     *
     * @param fileName zip filename
     * @param files    files to be compressed
     * @return zip file object
     * @throws IOException IOException
     */
    public static File compressToZip(String fileName, List<File> files) throws IOException {
        File zipFile = File.createTempFile(fileName, "zip");
        ZipArchiveOutputStream os = new ZipArchiveOutputStream(zipFile);
        for (File f : files) {
            ZipArchiveEntry entry = new ZipArchiveEntry(f, f.getName());
            os.putArchiveEntry(entry);
            IOUtils.copy(new FileInputStream(f), os);
        }
        os.closeArchiveEntry();
        os.finish();
        return zipFile;
    }

    /**
     * Decompress zip file.
     *
     * @param input zip file stream
     * @param files decompressed file list
     * @throws IOException IOException
     */
    public static void decompressFromZip(InputStream input, List<File> files) throws IOException {
        ZipArchiveInputStream archive = new ZipArchiveInputStream(new BufferedInputStream(input));
        ZipArchiveEntry entry;
        while ((entry = archive.getNextZipEntry()) != null) {
            String fileName = entry.getName();
            System.out.println(fileName);
            File file = File.createTempFile(fileName.substring(0, fileName.lastIndexOf(".")),
                fileName.substring(fileName.lastIndexOf(".")));
            IOUtils.copy(archive, new FileOutputStream(file));
            files.add(file);
        }
    }
}
