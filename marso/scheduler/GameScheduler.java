package marso.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import marso.model.GameDAO;

@Component
public class GameScheduler {

	GameDAO gameDao = new GameDAO();

	@Scheduled(cron="*/10 * * * * *")
	public void createGameFromUsersInLobby(){
		gameDao.createNewGame();
		System.out.println("createGameFromUsersInLobby executed at : "+ new java.util.Date());
		int i = 0;
	}
}
