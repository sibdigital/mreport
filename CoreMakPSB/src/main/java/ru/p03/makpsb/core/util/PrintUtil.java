/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ru.p03.vmvp.utils.FormatUtils;

/**
 *
 * @author timofeevan
 */
public class PrintUtil {
    
    public static class Header{
        protected String name;
        protected int width;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
        
        public Header (String name, int width){
            setName(name);
            setWidth(width);
        }
        
        public static String[] names(Header ... headers){
            String[] arr = new String[headers.length];
            int cnt = 0;
            for (Header h : headers) {
                arr[cnt] = h.getName();
                cnt++;
            }
            return arr;
        }
        
    }
    
    private PrintUtil(){
        
    }
    
    public static PrintUtil inctance(){
        return new PrintUtil();
    }
    
    private int columnWidth(String columnHeader){
        int scale = 256 * 4;
        int length = columnHeader.length();
        return length*scale;
    }
    
    public boolean simple_HeadepPrint(Workbook wb, Sheet sheet, int beginRowIndex, String[] headers){
        boolean result = true;
        Row headerRow = sheet.createRow(beginRowIndex);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            Object valueAt = headers[i];
            String columnHeader = FormatUtils.noNull(valueAt);
            int columnWidth = columnWidth(columnHeader);
            cell.setCellValue(columnHeader);
            cell.setCellStyle(headerStyle(wb));
            sheet.setColumnWidth(i, columnWidth);
        }
        return result;
    }
    
    public boolean simple_HeadepPrint(Workbook wb, Sheet sheet, int beginRowIndex, Header[] headers){
        boolean result = true;
        Row headerRow = sheet.createRow(beginRowIndex);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            Object valueAt = headers[i].getName();
            String columnHeader = FormatUtils.noNull(valueAt);
            int columnWidth = headers[i].getWidth();//columnWidth(columnHeader);
            cell.setCellValue(columnHeader);
            cell.setCellStyle(headerStyle(wb));
            sheet.setColumnWidth(i, columnWidth);
        }
        return result;
    }
    
    //POI 3.17
//    private CellStyle headerStyle(Workbook wb){
//        CellStyle style = wb.createCellStyle();//Create style
//        Font font = wb.createFont();//Create font
//        font.setBold(true);//Make font bold
//        style.setFont(font);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        return style;
//    }
    
    //POI 3.10.1
    private CellStyle headerStyle(Workbook wb){
        CellStyle style = wb.createCellStyle();//Create style
        Font font = wb.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
        style.setFont(font);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        return style;
    }
    
    public boolean save(Workbook wb, String fileName){
        boolean result = false;
        try{
            try (FileOutputStream out = new FileOutputStream(fileName)){
                wb.write(out);
                result = true;
            }   
        }catch (FileNotFoundException ex) { 
                Logger.getLogger(PrintUtil.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
            Logger.getLogger(PrintUtil.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return result;
    }
    
    public boolean simple_Print(Object[][] objects, String[] headers, String fileName, String sheetName){
        boolean result = true;
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet(sheetName);
        if (createFile(fileName) == true){       
            result &= simple_HeadepPrint(wb, sheet, 0, headers);
            result &= simple_Print(objects, wb, sheet);
            result &= save(wb, fileName);
        }else{
            Logger.getLogger(PrintUtil.class.getName()).log(Level.SEVERE, "cannot create file {0}", fileName);
        }
        return result;
    }
    
    public boolean simple_Print(List<Object[]> objects, String[] headers, String fileName, String sheetName){
        boolean result = true;
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet(sheetName);
        if (createFile(fileName) == true){       
            result &= simple_HeadepPrint(wb, sheet, 0, headers);
            result &= simple_Print(objects, wb, sheet);
            result &= save(wb, fileName);
        }else{
            Logger.getLogger(PrintUtil.class.getName()).log(Level.SEVERE, "cannot create file {0}", fileName);
        }
        return result;
    }
    
    public boolean simple_Print(Object[][] objects, Header[] headers, String fileName, String sheetName){
        boolean result = true;
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet(sheetName);
        if (createFile(fileName) == true){       
            result &= simple_HeadepPrint(wb, sheet, 0, headers);
            result &= simple_Print(objects, wb, sheet);
            result &= save(wb, fileName);
        }else{
            Logger.getLogger(PrintUtil.class.getName()).log(Level.SEVERE, "cannot create file {0}", fileName);
        }
        return result;
    }
    
    private boolean createFile(String fileName){
        File f = null;
        boolean b = true;
        try {
            f = new File(fileName);
            b = f.createNewFile();
        } catch (IOException ex) {            
            Logger.getLogger(PrintUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f != null && b;
    }
    
    public boolean simple_Print(Object[][] objects, Workbook wb, Sheet sheet){
        boolean result = true;
        int verticalSmesh = 1;
        for (int i = 0; i < objects.length; i++) {
            Row row = sheet.createRow(i + verticalSmesh);
            for (int j = 0; j < objects[i].length; j++) {
                Cell cell = row.createCell(j);
                Object valueAt = objects[i][j];
                if (valueAt instanceof Date){
                    cell.setCellValue(FormatUtils.formatAsDDMMYYY((Date)valueAt));
                }else if (valueAt instanceof BigDecimal){
                    cell.setCellValue(FormatUtils.formatAsRKK((BigDecimal)valueAt));
                }else{
                    cell.setCellValue(FormatUtils.noNull(valueAt));
                }
            }
        } 
        return result;
    }
    
    public boolean simple_Print(List<Object[]> objects, Workbook wb, Sheet sheet){
        boolean result = true;
        int verticalSmesh = 1;
        for (int i = 0; i < objects.size(); i++) {
            Row row = sheet.createRow(i + verticalSmesh);
            for (int j = 0; j < objects.get(i).length; j++) {
                Cell cell = row.createCell(j);
                Object valueAt = objects.get(i)[j];
                if (valueAt instanceof Date){
                    cell.setCellValue(FormatUtils.formatAsDDMMYYY((Date)valueAt));
                }else if (valueAt instanceof BigDecimal){
                    cell.setCellValue(FormatUtils.formatAsRKK((BigDecimal)valueAt));
                }else{
                    cell.setCellValue(FormatUtils.noNull(valueAt));
                }
            }
        } 
        return result;
    }
}
