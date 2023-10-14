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




    public String dibujoOrco() {
        String dibuO = "    ,     ,   \n" +
                       "   / \\. / \\  \n" +
                       "  /   \\/   \\\n" +
                       " |  o  : o  |\n" +
                       " |  .  : .  |\n" +
                       "  \\   /\\   /\n" +
                       "   \\_/ \\_ /   ";
        return dibuO;
    }

   public String dibujoElfo () {

        String dibuE =  "     |    |  \n" +
                        "    /     \\   \n" +
                        "   /_______\\ \n" +
                        "   |  o o  |  \n" +
                        "   |   ^   |  \n" +
                        "   |   -   |  \n" +
                        "    \\_____/  ";
        return dibuE;
        }

   public String dibujoHumano(){

        String dibuH = "    O  \n" +
                       "   /|\\  \n" +
                       "   / \\  ";
        return dibuH;
    }




}






