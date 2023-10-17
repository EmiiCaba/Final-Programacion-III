import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Battle {
    private List<Character> player1Characters;
    private List<Character> player2Characters;
    private List<String> battleLogs;
    private Random random;
    List<Character> characters;

    DibujoAscii dibujoAscii = new DibujoAscii();

    public Battle(List<Character> player1Characters, List<Character> player2Characters, List<Character> characters){
        if (player1Characters == null || player2Characters == null || characters == null) {
            throw new IllegalArgumentException("Las listas no pueden ser nulas.");
       }
            if (characters.size() < 6) {
                throw new IllegalArgumentException("No hay suficientes personajes para iniciar la batalla.");
        }

        this.characters = characters;
        this.player1Characters = player1Characters;
        this.player2Characters = player2Characters;
        this.battleLogs = new ArrayList<>();

        this.random = new Random();
    }
        public void start () {

            if (characters == null || characters.size() < 7) {
                System.out.println("No hay suficientes personajes para iniciar la batalla.");
                return;
            }
            List<Character> player1Characters = new ArrayList<>(characters.subList(0, 3));
            List<Character> player2Characters = new ArrayList<>(characters.subList(3, 6));


            //int randomIndex = random.nextInt(3);

            for (int i = 0; i < 3; i++) {
                Character player1 = player1Characters.get(i);
                Character player2 = player2Characters.get(i);


                System.out.println("\u001B[31m ------   Ronda de batalla: " + (i + 1) + "---------\u001B[0m");

                System.out.println(player1.getName(player2.getLevel() + 1) + " VS " + player2.getName(player2.getLevel() + 1));

                int attacks = 0;
                while (attacks < 7 && player1.isAlive() && player2.isAlive()) {
                    attacks++;
                    Character attacker = getRandomAttacker();
                    Character defender = (attacker == player1) ? player2 : player1;

                    int attackPower = attacker.calculateAttackPower();
                    int defensePower = defender.calculateDefensePower();

                    int effectiveness = random.nextInt(100) + 1;
                    int damage = calculateDamage(attackPower, defensePower, effectiveness, attacker.getRace());


                    defender.takeDamage(damage);

                    String log = attacker.getName(player2.getLevel() + 1) + " ataca a " + defender.getName(player2.getLevel() + 1) + " y le quita " +
                            damage + " de salud. " + defender.getName(player2.getLevel() + 1) + " queda con " + defender.getHealth() + " de salud.";
                    System.out.println(log);
                    battleLogs.add(log);

                    // Intercambia atacante y defensor
                    Character temp = attacker;
                    attacker = defender;
                    defender = temp;

                }

                if (!player1.isAlive() && !player2.isAlive()) {
                    System.out.println("¡Empataron !" + player1.getName() + "  y   " + player2.getName());
                } else if (player1.isAlive()) {
                    String winnerName = player1.getName(player1.getLevel() + 1);
                    System.out.println("¡Gana  el " + winnerName + "  " + player1.getName() + "!!!");
                    player1.setHealth(player1.getHealth() + 10);
                    printWinnerInfo(player1);

                } else {
                    String winnerName = player2.getName(player2.getLevel() + 1);
                    System.out.println("¡Gana el  " + winnerName + "   " + player2.getName() + "!!!");
                    player2.setHealth(player2.getHealth() + 10);
                    printWinnerInfo(player2);

                }
                System.out.println();
            }
        }
            private int calculateDamage (int attackPower, int defensePower, int effectiveness, String race) {
                double damageModifier = 1.0;

                if (race.equalsIgnoreCase("ELFO")) {
                    damageModifier = 1.05;
                } else if (race.equalsIgnoreCase("ORCO")) {
                    damageModifier = 1.1;
                }

                return (int) (((attackPower * effectiveness) - defensePower) / 500.0 * 100 * damageModifier);
            }



        private void printWinnerInfo(Character winner){
        String raceName = winner.getRace().equalsIgnoreCase("HUMAN") ? "HUMAN" :
                winner.getRace().equalsIgnoreCase("ELFO") ? "ELFO" : "ORCO";

            System.out.println("\u001B[36m Yo soy el " + raceName + " GANADOR DEL TRONO DE HIERRO :  \n" + winner.getName()+ "\u001B[0m");
            System.out.println(dibujoAscii.dibujoByRace(winner.getRace()));
        }


        private Character getRandomAttacker () {
            Random random = new Random();
            int randomIndex = random.nextInt(3); // Índice aleatorio (0, 1 o 2)

            // Obtener un personaje aleatorio de la lista
            return random.nextBoolean() ? player1Characters.get(randomIndex) : player2Characters.get(randomIndex);
        }


    public List<String> getBattleLogs() {
        return battleLogs;
    }

}



