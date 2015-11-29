package org.ggoncalves.robot.main;

import org.ggoncalves.robot.gui.frame.MouseRobotFrame;
import org.ggoncalves.robot.logic.config.Settings;
import org.ggoncalves.robot.util.ConsolePrinter;

/**
 * FEITO: - Mudar nome da main para MouseRobot - Mudar paradigma para Controller
 * e Frame (e CORE?) - Colocar botao colorido de start - Colocar botao colorido
 * de stop - Mudar entre botões - Reconhecer horário de início e fim nas
 * threads. - Colocar maven install - Criar testes unitários
 * 
 * FAZENDO: - Colocar Apache Commons para ler argumentos.
 * 
 * TODO: - Diminuir imagem do madagascar - Colocar versão do maven no titulo -
 * Incluir arquivo properties - Tela de preferências - Tela de sobre - Tela de
 * sobre no maven - i18n para espanhol, inglês e português - Colocar maven exec
 * - Colocar sons nos botões - Pesquisar efeito de fade no swing
 * 
 * @author ggoncalves
 *
 */
public class MouseRobotMain {

	private ArgumentValidator validator;
	private String[] args;

	public MouseRobotMain(String[] args) {
		this.args = args;
		run();
	}

	private final void run() {
		if (getValidator().isValid()) {
			initSettings();
			MouseRobotFrame robotFrame = new MouseRobotFrame();
			robotFrame.startApplication();
		}
		else {
			getValidator().printHelp();
		}

	}

	private void initSettings() {
		if (getValidator().hasSchedule()) {
			String[] startStopString = getValidator().getStartStopStrings();
			Settings.getInstance().setStartHour(startStopString[0]);
			Settings.getInstance().setStopHour(startStopString[1]);
		}
		ConsolePrinter.setVerbose(getValidator().isVerbose());
	}

	private ArgumentValidator getValidator() {
		if (validator == null) {
			validator = new ArgumentValidator(args);
		}
		return validator;
	}

	public static void main(String[] args) {
		// ApplicationRunner app = new ApplicationRunner(args);
		// app.run();
		new MouseRobotMain(args);
	}
	
  String getUsageMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append("MouseRobot: Ops!\n");
		sb.append("Você quer se remexer muito com horários programados não é?\n");
		sb.append("Informe a hora de início e hora fim da dança no formato hh:mm (12:00).\n");
		sb.append("Tipo assim: 09:00 18:00, ou 8:30 19:00.");
		return sb.toString();
	}
}
