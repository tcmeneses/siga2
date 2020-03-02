package br.com.caelum.vraptor.jpa;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Specializes
@RequestScoped
public class EntityManagerCreatorCustom extends EntityManagerCreator {
	private final EntityManagerFactory factory;

	/**
	 * @deprecated CDI eyes only.
	 */
	protected EntityManagerCreatorCustom() {
		super(null);
		this.factory = null;
	}

	@Inject
	public EntityManagerCreatorCustom(EntityManagerFactory factory) {
		this.factory = factory;
	}

	@Override
	@Produces
	@Dependent
	public EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

	@Override
	public void destroy(@Disposes EntityManager entityManager) {
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}
}
