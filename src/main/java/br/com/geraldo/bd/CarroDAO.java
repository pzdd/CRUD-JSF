package br.com.geraldo.bd;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.geraldo.entity.Carro;
import br.com.geraldo.exception.ErroSistema;

public class CarroDAO {
	
	public void add(Carro obj) throws ErroSistema {
		try{
			Session session = Conexao.sessionFactory.openSession();
			session.beginTransaction();
			session.save(obj);
			session.getTransaction().commit();
			session.close();
		}catch(Exception ex){
			throw new ErroSistema("Erro ao adicionar");
		}
	}
	
	public void update(Carro obj) throws ErroSistema {
		try{
			Session session = Conexao.sessionFactory.openSession();
			session.beginTransaction();
			session.merge(obj);
			session.getTransaction().commit();
			session.close();
		}catch(Exception ex){
			throw new ErroSistema("Erro ao alterar");
		}
	}
	public void remover(Carro obj) throws ErroSistema {
		try{
			Session session = Conexao.sessionFactory.openSession();
			session.beginTransaction();
			session.delete(obj);
			session.getTransaction().commit();
			session.close();
		}catch(Exception ex){
			throw new ErroSistema("Erro ao remover");
		}
	}
	
	public List<Carro> lista() throws ErroSistema {
		try{
			Session session = Conexao.sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Carro.class);
			@SuppressWarnings("unchecked")
			List<Carro> carros = (List<Carro>) criteria.list();
			session.getTransaction().commit();
			session.close();
			return carros;
		}catch(Exception ex){
			throw new ErroSistema("Erro ao remover");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Carro> findByModelo(String id) throws ErroSistema{
		try{
			Session session = Conexao.sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Carro.class);
			criteria.add(Restrictions.like("modelo", id).ignoreCase());
			List<Carro> carros = criteria.list();
			session.getTransaction().commit();
			session.close();
		return carros;
		}catch(Exception ex){
			throw new ErroSistema("Erro ao pesquisar por modelo");
		}
		
	}
	@SuppressWarnings("unchecked")
	public List<Carro> findById(int u) throws ErroSistema{
		try{
			Session session = Conexao.sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Carro.class);
			criteria.add(Restrictions.eq("id", u));
			List<Carro> carros = criteria.list();
			session.getTransaction().commit();
			session.close();
			return carros;
		}catch(Exception ex){
			throw new ErroSistema("Erro ao pesquisar por id");
		}
	}
	@SuppressWarnings("unchecked")
	public List<Carro> findByAno(String anoStr) throws ErroSistema{
		Session session = Conexao.sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Carro.class);
		try{
			criteria.add(Restrictions.eq("ano", Integer.parseInt(anoStr)));
		}catch(Exception e){
			throw new ErroSistema("Erro ao pesquisar por ano");
		}
		List<Carro> carros = (List<Carro>) criteria.list();
		session.getTransaction().commit();
		session.close();
		return carros;
	}

}
