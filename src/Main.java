import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numGiocatori = chiediNumeroGiocatori(scanner);
        Partita partita = new Partita(numGiocatori);

        chiediNomiGiocatori(scanner, partita);

        partita.iniziaPartita();

        while (!partita.terminaPartita()) {
            System.out.println("Stato attuale dei giocatori:");
            partita.visualizzaStatoGiocatori(partita);

            Giocatore giocatoreCorrente = partita.getGiocatoreCorrente();
            System.out.println("È il turno di: " + giocatoreCorrente.getNomeGiocatore());
            partita.turno();
        }

        scanner.close();
    }

    private static int chiediNumeroGiocatori(Scanner scanner) {
        int numGiocatori;
        do {
            System.out.print("Inserisci il numero di giocatori (da 4 a 7): ");
            while (!scanner.hasNextInt()) {
                System.out.print("Inserisci un numero valido: ");
                scanner.next();
            }
            numGiocatori = scanner.nextInt();
        } while (numGiocatori < 4 || numGiocatori > 7);
        return numGiocatori;
    }

    private static void chiediNomiGiocatori(Scanner scanner, Partita partita) {
        for (int i = 0; i < partita.getGiocatori().size(); i++) {
            String ruolo = partita.getGiocatori().get(i).getRuoloGiocatore().toString();
            String nome;
            if (partita.getGiocatori().get(i).getRuoloGiocatore() == Ruolo.SCERIFFO) {
                System.out.print("Inserisci un nome per lo sceriffo " + ruolo + ": ");
            } else {
                System.out.print("Inserisci un nome per il giocatore " + ruolo + ": ");
            }
            System.out.flush();
            nome = scanner.next();
            partita.getGiocatori().get(i).setNome(nome);
        }
    }
}