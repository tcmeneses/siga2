package br.com.caelum.vraptor.util.jpa;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.Annotation;
/**
 * Anotação criada para ser colocada antes de funcionalidades que não fazem oeprações de DML(INSERT, UPDATE OU DELETE)
 * tendo como objetivo não abrir transações
 * 
 * @author João Luis
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoOpenTransaction {

}