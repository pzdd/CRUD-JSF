package br.com.geraldo.bd;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import br.com.geraldo.entity.Carro;
import br.com.geraldo.exception.ErroSistema;

public class CarDAO extends GenericDAO<Carro>{
	
	public List<Carro> findByModelo(String modelo){
		getSession().beginTransaction();
		criteria = session.createCriteria(Carro.class);
		criteria.add(Restrictions.like("modelo", modelo).ignoreCase());
		List<Carro> result = criteria.list();
		getSession().getTransaction().commit();
		getSession().close();
		return result;
	}
	
	public List<Carro> findByAno(String ano) throws ErroSistema{
		getSession().beginTransaction();
		criteria = getSession().createCriteria(Carro.class);
		try{
			criteria.add(Restrictions.eq("ano", Integer.parseInt(ano)));
		}catch(Exception e){
			throw new ErroSistema("erro ao converter p ano");
		}
		List<Carro> result = criteria.list();
		getSession().getTransaction().commit();
		getSession().close();
		return result;
	}

}
