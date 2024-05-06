import java.util.concurrent.Semaphore;

// Classe Hospede
public class Hospede extends Thread {
    private Semaphore[] semaforos;
    private int id;
    private Quarto quarto;
    private Recepcionista[] recepcionistas;
    private int numeroQuarto; // Adicionamos o número do quarto ocupado pelo hóspede

    public Hospede(int id, Recepcionista[] recepcionistas, Semaphore[] semaforos) {
        this.id = id;
        this.recepcionistas = recepcionistas;
        this.semaforos = semaforos;
    }

    public synchronized int getNumero() {
        return id;
    }

    public synchronized void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public synchronized void setSemaforos(Semaphore[] semaforos) {
        this.semaforos = semaforos;
    }

    public void tentarFazerCheckIn(Hospede[] hospedes) {
        int tentativas = 0; // Contador de tentativas de check-in

        Semaphore[] semaforosHosps = new Semaphore[3];
         for (int i = 0; i < 3; i++) {
            semaforosHosps[i] = new Semaphore(1);
         }

        while (tentativas < 2) { // Limite de 2 tentativas
            for (int i = 0; i < 5; i++) {
                if (!recepcionistas[i].isOcupado()) {
                    if(recepcionistas[i].fazerCheckIn(hospedes)) {
                        for (int j = 0; j < hospedes.length; j++) {
                            if(j > 3) {
                                hospedes[j].setSemaforos(semaforosHosps);
                            }
                            hospedes[j].numeroQuarto = hospedes[j].quarto.getNumero();
                            hospedes[j].start();
                        }
                        return;
                    }else {
                        break;
                    }
                }
            }

            System.out.println("\033[41mHóspede " + id + " não conseguiu fazer check-in. Saindo e voltando mais tarde...\033[0m");
            tentativas++; // Incrementa o contador de tentativas

            // Aguarda um tempo antes de tentar fazer o check-in novamente
            try {
                Thread.sleep((long) (Math.random() * 5000)); // Simula um tempo de espera antes de tentar novamente
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Se o hóspede tentou fazer check-in duas vezes sem sucesso, deixa uma reclamação e vai embora
        String aux = "";
        for (int j = 0; j < hospedes.length; j++) {
            aux += "[" + hospedes[j].getNumero() + "] ";
        }

        System.out.println("\033[41mHóspedes " + aux
                + "não conseguiram fazer check-in após duas tentativas. Deixando uma reclamação e indo embora.\033[0m");

    }

    @Override
    public void run() {

        if (numeroQuarto != -1) {
            // Simulando o hóspede no quarto
            try {
                Thread.sleep(5000); // Simula o tempo que o hóspede está dentro do quarto
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (semaforos[0].tryAcquire()) { // Tentar adquirir o semáforo sem bloquear
                System.out.println("\033[34mHóspede " + id + " saiu do quarto " + numeroQuarto + " e deixou a chave com a recepção.\033[0m");
                recepcionistas[(int) Math.random() * recepcionistas.length].deixarChave(numeroQuarto); // Deixa a chave com a recepção ao sair
            } else {
                try {
                    Thread.sleep((2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Simula o hóspede passeando
            try {
                Thread.sleep(5000); // Simula o tempo que o hóspede está fora do hotel
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (semaforos[1].tryAcquire()) { // Tentar adquirir o semáforo sem bloquear
                // Verifica se o quarto está sendo limpo e espera
                while (quarto.isLimpezaEmAndamento()) {
                    try {
                        System.out.println("Hóspede " + id + " está esperando a camareira limpar o quarto " + numeroQuarto + ".");
                        quarto.wait(); // Aguarda a limpeza do quarto
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                recepcionistas[(int) Math.random() * recepcionistas.length].pegarChave(numeroQuarto, id); // Pega a chave com a recepção ao retornar
                System.out.println("\033[36mHóspede " + id + " voltou para o quarto " + numeroQuarto + ".\033[0m");
            } else {
                try {
                    Thread.sleep(2000); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            // Simulando o hóspede dentro do quarto novamente
            try {
                Thread.sleep(5000); // Simula o tempo que o hóspede está dentro do quarto
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (semaforos[2].tryAcquire()) { // Tentar adquirir o semáforo sem bloquear
                System.out.println("\033[31mHóspede " + id + " decidiu ir embora.\033[0m");
                recepcionistas[(int) Math.random() * recepcionistas.length].irEmbora(quarto.getNumero()); // Notifica o recepcionista que o hóspede irá embora
                recepcionistas[(int) Math.random() * recepcionistas.length].deixarChave(numeroQuarto); // Deixa a chave com a recepção ao sair
            }
            
            return; // Sai do método run() após o check-out
        }

    }
}
