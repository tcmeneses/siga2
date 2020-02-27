package br.gov.jfrj.siga.hibernate;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 * @author Rodrigo Ramalho hodrigohamalho@gmail.com
 *
 */
@ApplicationScoped
public class ExStarter {

	public static EntityManagerFactory emf;

	public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
		emf = Persistence.createEntityManagerFactory("default");
	}
	
	public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
		emf = null;
	}
}