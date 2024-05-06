import java.util.concurrent.Semaphore;

public class Hotel {
    public static void main(String[] args) {
        // Criando os quartos
        Quarto[] quartos = new Quarto[10];
        for (int i = 0; i < 10; i++) {
            quartos[i] = new Quarto(i);
        }

        // Criando as camareiras
        Camareira[] camareiras = new Camareira[10];
        for (int i = 0; i < 10; i++) {
            camareiras[i] = new Camareira();
        }

        // Criando os recepcionistas
        Recepcionista[] recepcionistas = new Recepcionista[5];
        for (int i = 0; i < 5; i++) {
            recepcionistas[i] = new Recepcionista(quartos, camareiras);
        }

        // Criando os hóspedes
        int[] allNumbersOfHospedes = {4, 5, 6, 7, 8};
        int idCount = 0;
        int limit = 51;
        while (idCount < limit) {
            //Criando Semaforos
            Semaphore[] semaforos = new Semaphore[3];
            for (int i = 0; i < 3; i++) {
                semaforos[i] = new Semaphore(1);
            }
            int numberOfHospedes = allNumbersOfHospedes[(int) (Math.random() * allNumbersOfHospedes.length)]; // Aleatorizar a quantidade de hospedes de 4 a 8
            Hospede[] arrayHospedes = new Hospede[numberOfHospedes]; // Juntando o grupo de hospedes em um array
            for (int i = 0; i < numberOfHospedes; i++) {
                arrayHospedes[i] = new Hospede(idCount, recepcionistas, semaforos);
                idCount += 1;
            }
            System.out.println("\033[42m Chegaram " + arrayHospedes.length + " novos hóspedes\033[0m");
            arrayHospedes[(int) Math.random() * numberOfHospedes].tentarFazerCheckIn(arrayHospedes);
            try {
                Thread.sleep(4000); // Espera um tempo aleatório entre a criação de cada hóspede
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
