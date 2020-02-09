package ru.p03.makpsb.core.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//import org.apache.commons. apache.tools.zip.ZipEntry;
//import org.apache.tools.zip.ZipOutputStream;
// 
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    /*public static Boolean zipDirArchivate (File srcDir, String add){
        File fd = new File(vmvp.Vmvp.archivePath);
        if (!fd.exists()){
            fd.mkdir();
        }
        File dest = new File(vmvp.Vmvp.archivePath + File.separator + srcDir.getName() + "_" + add + ".zip");
        return zipDirArchivate(srcDir, dest);
    }*/
    public static void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                File f = new File(dir, children[i]);
                deleteDirectory(f);
            }
            dir.delete();
        } else {
            dir.delete();
        }
    }

    //@SuppressWarnings("finally")
    public static Boolean zipDirArchivate(File srcDir, File destFile) {
        ZipOutputStream out = null;
        Boolean result = false;
        try {
            out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(destFile)));
            zipDir(srcDir, "", out);
            out.close();
            result = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ZipUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (final IOException ex) {
                Logger.getLogger(ZipUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            deleteDirectory(srcDir);
            return result;
        }
    }

    private static void zipFile(File srcFile, String destPath, ZipOutputStream out) throws IOException {
        byte buf[] = new byte[1024];
        InputStream in = new BufferedInputStream(new FileInputStream(srcFile));
        out.putNextEntry(new ZipEntry(concatPathAndFilename(destPath, srcFile.getName(), File.separator)));
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.closeEntry();
        in.close();
    }

    private static void zipDir(File srcDir, String destPath, ZipOutputStream out) throws IOException {
        for (File file : srcDir.listFiles()) {
            if (file.isDirectory()) {
                zipDir(file, concatPathAndFilename(destPath, file.getName(), File.separator), out);
            } else {
                zipFile(file, destPath, out);
            }
        }
    }

    private static String concatPathAndFilename(String path, String filename, String separator) {
        if (path == null) {
            return filename;
        }
        String trimmedPath = path.trim();
        if (trimmedPath.length() == 0) {
            return filename;
        }
        String trimmedFilename = filename.trim();
        if (trimmedPath.endsWith(separator)) {
            return trimmedPath + trimmedFilename;
        } else {
            return trimmedPath + separator + trimmedFilename;
        }
    }
}
