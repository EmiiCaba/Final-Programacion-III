public class TextStart {

    public static void Text() {
               String phrase = "\" En un mundo misterioso y paralelo, en un tiempo que se extiende desde el año 1 hasta el año 1000, se desata una feroz batalla. Tres razas legendarias, los valientes Orcos, los ágiles Elfos y los resistentes Humanos, se preparan para luchar por la supremacía en un conflicto que definirá el destino de su reino. Cada raza, con sus propias habilidades y características únicas, se alza en armas con un solo objetivo: demostrar que son la raza más fuerte y digna de gobernar este mundo paralelo. Las leyendas se forjarán, los héroes surgirán y los destinos se entrelazarán en una batalla épica que trasciende el tiempo y el espacio. ¿Quién prevalecerá en esta lucha titánica? ¿Los brutales Orcos, los elegantes Elfos o los versátiles Humanos? ¡La respuesta está en manos de los guerreros más fuertes y los estrategas más astutos! Prepárate para adentrarte en un mundo lleno de magia, aventuras y desafíos, donde solo los más valientes y poderosos sobrevivirán. ¡La batalla entre las razas ha comenzado, y la gloria eterna aguarda a aquellos que demuestren ser dignos de ella! ¿Estás listo para unirte a la lucha y escribir tu propia leyenda en este mundo paralelo? ¡Que comience la batalla!\"";
        String[] lines = phrase.split(" ");
        int lineLength = 20; // Longitud máxima de palabras por línea
        int wordCount = 0; // Contador de palabras en la línea actual

        for (String line : lines) {
            if (wordCount == lineLength) {
                System.out.println(); // Imprimir nueva línea
                wordCount = 0; // Reiniciar contador de palabras
            }

            System.out.print(line + " ");
            wordCount++;
        }

    }
}








