/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.file;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.p03.makpsb.core.util.ResourceUtil;
import ru.p03.vmvp.utils.CommonUtils;

/**
 *
 * @author timofeevan
 */
public class MonthFileView implements Serializable{
    
    private static final String ENV_REPORT_DIRECTORY = "java:/comp/env/mpcore/jobReportDirectory";
    
    public static class FileView implements Serializable{
        private File realFile;
        private String downloadPath;
        private final String month;
        private final String day;
        private final String year;
        
        public String getAbsolutePath(){
            return realFile.getAbsolutePath();
        }
        
        public String getName(){
            return realFile.getName();
        }
        
        public String getDownloadPath(){
            return downloadPath;
        }
        
        public final void create(File f){
            realFile = f;
            downloadPath = "/download.do?filename=" + realFile.getAbsolutePath();
        }
        
        public FileView(File f, String year, String month, String day){
            create(f);
            this.year = year;
            this.month = month;
            this.day = day;         
        }
    }
    
    private final String month;
    private final String resourceName;
    private final List<FileView> files = new ArrayList<>();
    
    public MonthFileView(String month, String resourceName){
        this.month = month;
        this.resourceName = resourceName;
    }
    
    private void fill(){
        Object environment = ResourceUtil.inctance().getEnvironment(ENV_REPORT_DIRECTORY);
        if (environment != null){
            String env = environment.toString();
            String sname = resourceName;
            Integer year = CommonUtils.year(CommonUtils.now());
            String path = env + File.separator + sname + File.separator + year.toString() + File.separator + month;
            fillFiles(path, year);
            
        }
    }
    
    private void fillFiles(String spath, final Integer year){
        Path path= Paths.get(spath);
        try {
           Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                 if(!attrs.isDirectory()){
                      FileView fv = new FileView(file.toFile(), year.toString(), month, file.getParent().toFile().getName());
                      files.add(fv);
                 }
                 return FileVisitResult.CONTINUE;
             }
            });
        } catch (IOException ex) {
            Logger.getLogger(ResourceUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<FileView> getFiles(){
        return files;
    }
    
    public static MonthFileView getCurrentMonthFiles(String resourceName){
        Integer currmonth = CommonUtils.monthOfYear(CommonUtils.now());
        MonthFileView mfv = new MonthFileView(currmonth.toString(), resourceName);
        mfv.fill();
        return mfv;
    }
    
    public static MonthFileView getLastMonthFiles(String resourceName){
        Integer currmonth = CommonUtils.monthOfYear(CommonUtils.now());
        if (currmonth == 0){
            currmonth = 11;
        }
        MonthFileView mfv = new MonthFileView(currmonth.toString(), resourceName);
        mfv.fill();
        return mfv;
    }
}
