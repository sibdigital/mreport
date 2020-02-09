/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.core.report;

import java.util.Map;

/**
 *
 * @author timofeevan
 */
public interface IReport {

    String getAsString(String key);

    /**
     * @return папка, где находится/будет находиться файл отчета
     */
    String getDirectory();

    Map<String, Object> getProperties();

    Object getProperty(String key);

    /**
     * @return имя файла отчета без полного пути
     */
    String getSimpleFileName();

    /**
     * @return the templateDirectory
     */
    @Deprecated
    String getTemplateDirectory();

    @Deprecated
    boolean isSuccess();

    /**
     * Функция создающая работу по расписанию для выполненияч Quartz
     *
     *  @param   params  параметры выполнения работы по расписанию, н.п дата, час, минута
     */
    void job(Map<String, Object> params);

    /**
     * Выполняет отчет
     *
     * @return Истина, если операции выполнены успешно, иначе ложь
     */
    boolean make();

    /**
     * Формирует и записывает файл отчета
     *
     * @return Истина, если файл сформирован и записан, иначе ложь
     */
    boolean makeReportFile();

    /**
     * Возвращает полное имя файла сформированного отчета
     * как directory + \\ + simpleFileName
     * @return   Возвращает полное имя файла сформированного отчета
     */
    String reportFileName();

    void setProperties(Map<String, Object> properies);

    Object setProperty(String key, Object value);

    /**
     * Устанавливает папку, куда будет сохранен отчет и имя файла в этой папке
     * под которым будет сохранен отчет.
     * @param   directory       Абсолютный путь к папке, в которой должен сохраняться отчет.
     *                          Указывается без завершающих слэшей
     *                          Например: C:\\makpsb_report\\example_report\\2016
     * @param   simpleFileName  Имя файла, в который будет сохранен отчет
     *                          Указывается без полного пути
     *                          Например: result_11_11_2016.xls
     */
    void setReportPath(String directory, String simpleFileName);

    /**
     * @param reportDirectory the templateDirectory to set
     */
    @Deprecated
    void setTemplateDirectory(String reportDirectory);
    
}
