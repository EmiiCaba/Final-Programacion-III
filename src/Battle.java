import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Battle {
    private Character player1Character;
    private Character player2Character;
    private List<String> battleLogs;
    private Random random;

    public Battle(Character player1Character, Character player2Character) {
        this.player1Character = player1Character;
        this.player2Character = player2Character;
        this.battleLogs = new ArrayList<>();
        this.random = new Random();
    }
    private static final String ORCO_ASCII =
            "    ,      ,   \n" +
                    "   / \\  . / \\  \n" +
                    "  /   \\/   \\\n" +
                    " |  o   :  o  |\n" +
                    " |  .   :   .  |\n" +
                    "  \\   /\\   /\n" +
                    "   \\_/  `-'";

    private static final String ELFO_ASCII =
            "     |    |  \n" +
                    "    /     \\   \n" +
                    "   /_______\\ \n" +
                    "   |  o o  |  \n" +
                    "   |   ^   |  \n" +
                    "   |   -   |  \n" +
                    "    \\_____/  ";

    private static final String HUMANO_ASCII =
            "    O\n" +
                    "   /|\\  \n" +
                    "   / \\  ";
    public void start() {


        System.out.println("Ronda de batalla:");
        System.out.println(player1Character.getName(player2Character.getLevel() + 1) + " VS " + player2Character.getName(player2Character.getLevel() + 1));

        Character attacker = getRandomAttacker();
        Character defender = (attacker == player1Character) ? player2Character : player1Character;

        int attacks = 0;
        while (attacks < 7 && attacker.isAlive() && defender.isAlive()) {
            attacks++;
            int attackPower = attacker.calculateAttackPower();
            int defensePower = defender.calculateDefensePower();

            int effectiveness = random.nextInt(100) + 1; // Random number between 1 and 100
            int damage = ((attackPower * effectiveness) - defensePower) / 500;

            if (attacker.getRace().equals("human")) {
                damage = (int) (damage * 1.0);
            } else if (attacker.getRace().equals("elf")) {
                damage = (int) (damage * 1.05);
            } else if (attacker.getRace().equals("orc")) {
                damage = (int) (damage * 1.1);
            }

            defender.takeDamage(damage);

            String log = attacker.getName(player2Character.getLevel() + 1) + " ataca a " + defender.getName(player2Character.getLevel() + 1) + " y le quita " +
                    damage + " de salud. " + defender.getName(player2Character.getLevel() + 1) + " queda con " + defender.getHealth() + " de salud.";
            battleLogs.add(log);

            Character temp = attacker;
            attacker = defender;
            defender = temp;
        }

        if (!player1Character.isAlive() && !player2Character.isAlive()) {
            System.out.println("¡Empate!");
        } else if (player1Character.isAlive()) {
            String winnerName = player1Character.getName(player1Character.getLevel() + 1);
            System.out.println("¡Gana " + winnerName + "!  ");
            player1Character.setHealth(player1Character.getHealth() + 10);

            // Imprimir el dibujo del personaje ganador
            if (player1Character.getRace().equals("orc")) {
                System.out.println("Dibujo del Orco:\n" + ORCO_ASCII);
            } else if (player1Character.getRace().equals("elf")) {
                System.out.println("Dibujo del Elfo:\n" + ELFO_ASCII);
            } else if (player1Character.getRace().equals("human")) {
                System.out.println("Dibujo del Humano:\n" + HUMANO_ASCII);
            }
        } else {
            String winnerName = player2Character.getName(player2Character.getLevel() + 1);
            System.out.println("¡Gana " + winnerName + "!  ");
            player2Character.setHealth(player2Character.getHealth() + 10);

            // Imprimir el dibujo del personaje ganador
            if (player2Character.getRace().equals("orc")) {
                System.out.println("Dibujo del Orco:\n" + ORCO_ASCII);
            } else if (player2Character.getRace().equals("elf")) {
                System.out.println("Dibujo del Elfo:\n" + ELFO_ASCII);
            } else if (player2Character.getRace().equals("human")) {
                System.out.println("Dibujo del Humano:\n" + HUMANO_ASCII);
            }
        }
        System.out.println();
    }



    private Character getRandomAttacker() {
        return random.nextBoolean() ? player1Character : player2Character;
    }

    public List<String> getBattleLogs() {
        return battleLogs;
    }


    }
