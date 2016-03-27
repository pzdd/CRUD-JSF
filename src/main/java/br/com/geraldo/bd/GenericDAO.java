package br.com.geraldo.bd;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.geraldo.entity.Carro;

public abstract class GenericDAO<T> {
	
	Class<T> templateClass;
	Session session = Conexao.getSession();
	Criteria criteria = null;
	
	protected Session getSession(){
		if(this.session == null || !this.session.isOpen()){
			this.session = Conexao.getSession();
		}
		return this.session;
	}
	
	public void save(T t){
		getSession().beginTransaction();
		getSession().save(t);
		getSession().getTransaction().commit();
		getSession().close();
	}
	public void delete(T t){
		getSession().beginTransaction();
		getSession().delete(t);
		getSession().getTransaction().commit();
		getSession().close();
	}
	public void update(T t){
		getSession().beginTransaction();
		getSession().merge(t);
		getSession().getTransaction().commit();
		getSession().close();
		
	}
	public List<T> findAll(Class<T> classType){
		getSession().beginTransaction();
		criteria = getSession().createCriteria(classType);
		List<T> result = criteria.list();
		getSession().getTransaction().commit();
		getSession().close();
		return result;
	}
	public T findById(Long id,Class<T> classType){
		getSession().beginTransaction();
		criteria = getSession().createCriteria(classType);
		criteria.add(Restrictions.eq("id", id));
		criteria.setMaxResults(1);
		T result = (T) criteria.uniqueResult();
		return result;
	}

}
