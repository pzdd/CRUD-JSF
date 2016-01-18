package br.com.geraldo.bd;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Conexao {
	public static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
}
