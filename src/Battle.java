import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Battle {
    private List<Character> player1Characters;
    private List<Character> player2Characters;
    private List<String> battleLogs;
    private Random random;
    private List<Character> winners;

    public Battle(List<Character> player1Characters, List<Character> player2Characters, List<Character> characters) {
        if (player1Characters == null || player2Characters == null) {
            throw new IllegalArgumentException("Las listas no pueden ser nulas.");
        }
        if (player1Characters.size() != player2Characters.size()) {
            throw new IllegalArgumentException("Ambos jugadores deben tener la misma cantidad de personajes.");
        }

        this.player1Characters = player1Characters;
        this.player2Characters = player2Characters;
        this.battleLogs = new ArrayList<>();
        this.random = new Random();
        this.winners = new ArrayList<>();
    }

    public void start() {
        int numRounds = player1Characters.size();

        for (int round = 0; round < numRounds; round++) {
            Character player1 = player1Characters.get(round);
            Character player2 = player2Characters.get(round);

            System.out.println("\u001B[31m ------   Ronda de batalla: " + (round + 1) + "---------\u001B[0m");
            System.out.println(player1.getName() + " VS " + player2.getName());

            Character winner = fight(player1, player2);
            winners.add(winner);

            System.out.println("\u001B[32m¡Ganaste sobreviviste   " + winner.getName() + " en la ronda " + (round + 1) + "!\u001B[0m");
            System.out.println();

            if (round == numRounds - 1) {
                System.out.println("\u001B[34m¡Felicitaciones  El ganador Final  es " + winner.getName() + "! , las fuerzas mágicas del universo luz te abrazan!\u001B[0m");
                System.out.println("___________________________________________");
            }
        }
    }

    private Character fight(Character player1, Character player2) {
        int attacks = 0;
        while (attacks < 3 && player1.isAlive() && player2.isAlive()) {
            attacks++;
            Character attacker = getRandomAttacker();
            Character defender = (attacker == player1) ? player2 : player1;

            int attackPower = attacker.calculateAttackPower();
            int defensePower = defender.calculateDefensePower();

            int effectiveness = random.nextInt(100) + 1;
            int damage = calculateDamage(attackPower, defensePower, effectiveness);

            defender.takeDamage(damage);

            String log = attacker.getName() + " ataca a " + defender.getName() + " y le quita " +
                    damage + " de salud. " + defender.getName() + " queda con " + defender.getHealth() + " de salud.";
            battleLogs.add(log);
            System.out.println(log);
        }

        return player1.isAlive() ? player1 : player2;
    }

    private int calculateDamage(int attackPower, int defensePower, int effectiveness) {
        double damage = (attackPower / defensePower) * effectiveness;
        return (int) Math.round(damage);
    }

    private Character getRandomAttacker() {
        return random.nextBoolean() ? player1Characters.get(random.nextInt(player1Characters.size())) :
                player2Characters.get(random.nextInt(player2Characters.size()));
    }

    public List<String> getBattleLogs() {
        return battleLogs;
    }

    public List<Character> getWinners() {
        return winners;
    }
}





