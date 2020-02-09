/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.ui;

import javax.servlet.jsp.PageContext;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

/**
 *
 * @author timofeevan
 */
public class YesNoDecorator implements DisplaytagColumnDecorator {

    @Override
    public Object decorate(Object o, PageContext pc, MediaTypeEnum mte) throws DecoratorException {
        Integer st = (Integer) o;
        return st == 1 ? "Да" : "Нет";
    }
}
