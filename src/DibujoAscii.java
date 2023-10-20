public class DibujoAscii {
    public String dibujoByRace(String race) {
        switch (race.toUpperCase()) {
            case "HUMAN":
                return dibujoHumano();
            case "ELFO":
                return dibujoElfo();
            case "ORCO":
                return dibujoOrco();
            default:
                return "No se encontró un dibujo para la raza especificada.";
        }
    }




    public static String dibujoOrco() {
        String dibuO = " \u001B[34m   ,     ,   \n" +
                       "   / \\. / \\  \n" +
                       "  /   \\/   \\\n" +
                       " |  o  : o  |\n" +
                       " |  .  : .  |\n" +
                       "  \\   /\\   /\n" +
                       "   \\_/ \\_ /   ";
        return dibuO;
    }

   public static String dibujoElfo() {

        String dibuE =  "\u001B[32m     |    |  \n" +
                        "    /     \\   \n" +
                        "   /_______\\ \n" +
                        "   |  o o  |  \n" +
                        "   |   ^   |  \n" +
                        "   |   -   |  \n" +
                        "    \\_____/  ";
        return dibuE;
        }

   public static String dibujoHumano(){

        String dibuH = "\u001B[33m    O  \n" +
                       "   /|\\  \n" +
                       "   / \\  ";
        return dibuH;
    }




}






