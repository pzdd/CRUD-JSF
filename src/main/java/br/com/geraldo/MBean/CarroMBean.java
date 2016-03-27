package br.com.geraldo.MBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.geraldo.bd.CarDAO;
import br.com.geraldo.bd.CarroDAO;
import br.com.geraldo.bd.GenericDAO;
import br.com.geraldo.entity.Carro;
import br.com.geraldo.exception.ErroSistema;

@ManagedBean
@SessionScoped
public class CarroMBean {
	
	private Carro carro = new Carro();
	private List<Carro> carros = new ArrayList<Carro>();
	//private CarroDAO carDAO = new CarroDAO();
	private CarDAO carDAO = new CarDAO();
	private String termo;
	//private GenericDAO DAO = new GenericDAO();
	
	public CarroMBean(){
		try{
		carros = carDAO.findAll(Carro.class);
		//carros = DAO.findAll(Carro.class);
		}catch(Exception ex){
			adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void adicionar(){
		try{
			if(validate(carro)){
				carros.add(carro);
				carDAO.save(carro);
				carro = new Carro();
				adicionarMensagem("Salvo", "Carro salvo com sucesso!!!", FacesMessage.SEVERITY_INFO);
			}
		}catch(Exception ex){
			adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		
	}
	public void salvar(){
		try{
			if(validate(carro)){
				carDAO.update(carro);
				adicionarMensagem("Alterar", "Carro alterado com sucesso!!!", FacesMessage.SEVERITY_INFO);
			}else{
				carros = carDAO.findAll(Carro.class);
			}
		}catch(Exception ex){
			adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	public void listar(){
		try{
			carros = carDAO.findAll(Carro.class);
			if(carros == null || carros.size()<=0){
				adicionarMensagem("Listar", "Não foram encontrados resultados para sua busca", FacesMessage.SEVERITY_WARN);
			}
		}catch(Exception ex){
			adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	public Carro editar(){
		return carro;
	}
	public void remover(){
		try{
			carDAO.delete(carro);
			carros.remove(carro);
			adicionarMensagem("Remover", "Removido com sucesso", FacesMessage.SEVERITY_INFO);
		}catch(Exception ex){
			adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	public String buscar(){
		return "/search.xhtml";
	}
	public void pesquisar(){
		try{
			if(termo != null && !termo.isEmpty()){
				//pesquisa por ano
				carros = carDAO.findByAno(termo);
				if(carros == null){
					//pesquisa por modelo
					carros = carDAO.findByModelo(termo);
				}
			}else{
				//retorna um erro
				adicionarMensagem("INFO", "Informe um termo válido", FacesMessage.SEVERITY_ERROR);
			}
		}catch(Exception ex){
			adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public boolean validate(Carro car){
		if(car.getAno() == null){
			adicionarMensagem("INFO", "Informe um ano válido", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(car.getCor() == null || car.getCor().isEmpty()){
			adicionarMensagem("INFO", "Informe uma cor válida", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(car.getMarca() == null || car.getMarca().isEmpty()){
			adicionarMensagem("INFO", "Informe uma marca válida", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(car.getModelo() == null || car.getModelo().isEmpty()){
			adicionarMensagem("INFO", "Informe um modelo válido", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		return true;
	}
	
	public void adicionarMensagem(String sumario,String detalhe, FacesMessage.Severity tipoErro){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(tipoErro, sumario, detalhe);
		context.addMessage(null, message);
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public List<Carro> getCarros() {
		return carros;
	}

	public void setCarros(List<Carro> carros) {
		this.carros = carros;
	}
	public String getTermo() {
		return termo;
	}
	public void setTermo(String termo) {
		this.termo = termo;
	}
	

}
