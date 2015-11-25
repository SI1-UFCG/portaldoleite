import models.*;
import models.dao.GenericDAOImpl;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;

import java.util.ArrayList;
import java.util.List;

public class Global extends GlobalSettings {

	private static GenericDAOImpl dao = new GenericDAOImpl();
	private List<Disciplina> disciplinas = new ArrayList<>();

	@Override
	public void onStart(Application app) {
		Logger.info("Aplicação inicializada...");

		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				if (dao.findAllByClassName(Disciplina.class.getName()).size() == 0) {
					criaDisciplinaTemas();
				}
			}
		});
	}

	@Override
	public void onStop(Application app) {
		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				Logger.info("Aplicação finalizando...");
				disciplinas = dao.findAllByClassName("Disciplina");

				for (Disciplina disciplina : disciplinas) {
					dao.removeById(Disciplina.class, disciplina.getId());
				}
			}
		});
	}

	private void criaDisciplinaTemas() {
		List<User> users = new ArrayList<User>();
		for (int i = 1; i < 11; i++) {
			User user = new User("dev" + i + "@gmail.com", "12345", "dev" + i);
			user.setNome("Sistema");
			users.add(user);
		}

		Disciplina si1 = criaSi1(users);
		Disciplina eda = criaEda(users);
		Disciplina leda = criaLeda(users);

		dao.persist(si1);
		dao.persist(eda);
		dao.persist(leda);
		dao.flush();
	}

	private Disciplina criaSi1(List<User> users) {
		Disciplina si1 = new Disciplina("Sistemas de Informação 1");
		si1.addTema(new Tema("Análise x Design"));
		si1.addTema(new Tema("Orientação a objetos"));
		si1.addTema(new Tema("GRASP"));
		si1.addTema(new Tema("GoF"));
		si1.addTema(new Tema("Arquitetura"));
		si1.addTema(new Tema("Play"));

		Tema javascript = new Tema("JavaScript");
		Dica conselhoJs = new DicaConselho("É uma boa usar AngularJS.");
		conselhoJs.incrementaConcordancias();
		conselhoJs.addUsuarioQueVotou(users.get(0).getLogin());
		conselhoJs.incrementaDiscordancias();
		conselhoJs.addUsuarioQueVotou(users.get(1).getLogin());
		javascript.addDica(conselhoJs);
		conselhoJs.setTema(javascript);
		conselhoJs.setUser(users.get(2).getLogin());
		si1.addTema(javascript);

		si1.addTema(new Tema("HTML / CSS / Bootstrap"));
		si1.addTema(new Tema("Heroku"));
		si1.addTema(new Tema("Labs"));
		si1.addTema(new Tema("Minitestes"));

		Tema projeto = new Tema("Projeto");
		Dica conselhoProjeto = new DicaConselho("Coloque seu projeto no Heroku o quanto antes!");
		conselhoProjeto.incrementaConcordancias();
		conselhoProjeto.addUsuarioQueVotou(users.get(3).getLogin());
		conselhoProjeto.incrementaDiscordancias();
		conselhoProjeto.addUsuarioQueVotou(users.get(4).getLogin());
		projeto.addDica(conselhoProjeto);
		conselhoProjeto.setTema(projeto);
		conselhoProjeto.setUser(users.get(5).getLogin());
		si1.addTema(projeto);

		return si1;
	}

	private Disciplina criaEda(List<User> users) {
		Disciplina eda = new Disciplina("Estrutura de Dados e Algoritmos");
		eda.addTema(new Tema("Algoritmos"));

		Tema estruturas = new Tema("Estrutura de Dados");
		Dica conselhoMonitor = new DicaConselho("Os monitores ajudam muito.");
		conselhoMonitor.incrementaConcordancias();
		conselhoMonitor.addUsuarioQueVotou(users.get(6).getLogin());
		conselhoMonitor.incrementaDiscordancias();
		conselhoMonitor.addUsuarioQueVotou(users.get(7).getLogin());
		estruturas.addDica(conselhoMonitor);
		conselhoMonitor.setTema(estruturas);
		conselhoMonitor.setUser(users.get(8).getLogin());
		eda.addTema(estruturas);

		eda.addTema(new Tema("Árvores"));

		Tema livros = new Tema("Livros");
		Dica conselhoLivro = new DicaConselho("Leia os capítulos do livro do Cormen que o professor indicar.");
		conselhoLivro.incrementaConcordancias();
		conselhoLivro.addUsuarioQueVotou(users.get(9).getLogin());
		conselhoLivro.incrementaDiscordancias();
		conselhoLivro.addUsuarioQueVotou(users.get(0).getLogin());
		estruturas.addDica(conselhoLivro);
		conselhoLivro.setTema(livros);
		conselhoLivro.setUser(users.get(1).getLogin());
		eda.addTema(livros);

		return eda;
	}

	private Disciplina criaLeda(List<User> users) {
		Disciplina leda = new Disciplina("Laboratório de Estrutura de Dados e Algoritmos");

		Tema implementacao = new Tema("Implementação");
		Dica conselhoImpl = new DicaConselho("Faça todos os laboratórios sem colar código.");
		conselhoImpl.incrementaConcordancias();
		conselhoImpl.addUsuarioQueVotou(users.get(2).getLogin());
		conselhoImpl.incrementaDiscordancias();
		conselhoImpl.addUsuarioQueVotou(users.get(3).getLogin());
		implementacao.addDica(conselhoImpl);
		conselhoImpl.setTema(implementacao);
		conselhoImpl.setUser(users.get(4).getLogin());
		leda.addTema(implementacao);



		return leda;
	}
}
