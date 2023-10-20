import java.util.*;

public class Game {

       public void start() {
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;

            while (!exit) {
                Menu.showMenu();
                int option = Menu.getUserChoice(scanner);
                scanner.nextLine(); // Consumir el salto de línea

                switch (option) {
                    case 1:
                        startRandomGame();
                        break;
                    case 2:
                        startManualGame();
                        break;
                    case 3:
                        Logs.readLogs();
                        break;
                    case 4:
                        Logs.deleteLogsFile();
                        break;
                    case 5:
                        exit = true;
                        Menu.showGoodbyeMessage();
                        break;
                    default:
                        Menu.showInvalidOptionMessage();
                        break;
                }
            }

            System.out.println("------------------------------------------------------------");
        }



    private void startRandomGame() {

        List<Character> characters = Character.generateRandomCharacters();

        if (characters.size() < 7) {
            System.out.println("No hay suficientes personajes para iniciar el juego.");
            return;
        }

        List<Character> player1Characters = new ArrayList<>(characters.subList(0, 3));
        List<Character> player2Characters = new ArrayList<>(characters.subList(3, 6));



        // Imprime información de los personajes antes de la batalla
        Character.printCharacterInfo( player1Characters,  player2Characters);

        Battle battle = new Battle(player1Characters, player2Characters,characters);
        battle.start();

        Logs.saveLogs(battle.getBattleLogs());
    }



    private void startManualGame() {
        Scanner scanner = new Scanner(System.in);
        int characterCount = 0;
        List<Character> player1Characters =Character.createPlayerCharacters(scanner, "Primer");

        characterCount += player1Characters.size();

        if (characterCount < 3) {
            System.out.println("No hay suficientes personajes para el jugador 1.");
            return;
        }

        List<Character> player2Characters = Character.createPlayerCharacters(scanner, "Segundo");

        characterCount += player2Characters.size();

        if (characterCount < 3) {
            System.out.println("No hay suficientes personajes para el jugador 2.");
            return;
        }


        List<Character> allCharacters = new ArrayList<>();
        allCharacters.addAll(player1Characters);
        allCharacters.addAll(player2Characters);

        Battle battle = new Battle(player1Characters, player2Characters, allCharacters);
        battle.start();

        Logs.saveLogs(battle.getBattleLogs());
    }


}
