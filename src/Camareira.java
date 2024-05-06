public class Camareira extends Thread {
    private boolean ocupado = false;

    public synchronized boolean isOcupado() {
        return ocupado;
    }

    public synchronized void limparQuarto(Quarto quarto) {
        synchronized(quarto) {
            ocupado = true;
            quarto.iniciarLimpeza(); // Marca que a limpeza está em andamento
            // Simula o tempo de limpeza
            System.out.println("Camareira está limpando o quarto " + quarto.getNumero());
            try {
                Thread.sleep(2000); // Simula o tempo de limpeza do quarto
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Camareira terminou de limpar o quarto " + quarto.getNumero());
            quarto.finalizarLimpeza(); // Marca que a limpeza foi concluída
            // Avisa o hóspede que a limpeza do quarto foi concluída
            quarto.notifyAll();
            ocupado = false;
        }
    }
}
