// Classe Recepcionista
public class Recepcionista extends Thread {
    private Quarto[] quartos;
    private int[] chaves; // Usaremos um array de inteiros para representar o ID do hóspede com a chave de cada quarto
    private boolean ocupado = false;
    private Camareira[] camareiras;

    public Recepcionista(Quarto[] quartos, Camareira[] camareiras) {
        this.quartos = quartos;
        this.camareiras = camareiras;
        this.chaves = new int[10]; // 10 chaves, uma para cada quarto
        for (int i = 0; i < 10; i++) {
            chaves[i] = -1; // Inicialmente todas as chaves estão na recepção
        }
    }

    public synchronized boolean fazerCheckIn(Hospede[] hospedes) {
        setOcupado(true);
        try {
            Thread.sleep((long) (Math.random() * 3000)); // Simular um tempo de fazer check-in
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int quartosNecessarios = 1;
        if(hospedes.length > 4) {
            quartosNecessarios = 2;
        }
        int quartosDisponiveis = 0;
        for (int i = 0; i < quartos.length; i++) {
            if (!quartos[i].isOcupado() && chaves[i] == -1 && !quartos[i].isLimpo()) { // Verifica se o quarto está vago e se a chave está na recepção
                quartosDisponiveis += 1;
            }
        }

        boolean firstQuarto = false;
        if(quartosDisponiveis >= quartosNecessarios) {
            for (int i = 0; i < quartos.length; i++) {
                if(quartosNecessarios > 0) {
                    if (!quartos[i].isOcupado() && chaves[i] == -1 && !quartos[i].isLimpo()) { // Verifica se o quarto está vago e se a chave está na recepção
                        for (int j = 0; j < 4; j++) {
                            if(j >= 0 && j < 4 && !firstQuarto) {
                                hospedes[j].setQuarto(quartos[i]);
                                quartos[i].setHospede(hospedes[j], j);
                                System.out.println("\033[32mRecepcionista alocou hóspede " + hospedes[j].getNumero() + " no quarto " + quartos[i].getNumero() + "\033[0m");
                            }
                            else if(j >= 0 && j < hospedes.length - 4 && firstQuarto) {
                                hospedes[j + 4].setQuarto(quartos[i]);
                                quartos[i].setHospede(hospedes[j + 4], j);
                                System.out.println("\033[32mRecepcionista alocou hóspede " + hospedes[j + 4].getNumero() + " no quarto " + quartos[i].getNumero() + "\033[0m");
                            } 
                            else {
                                break;
                            }
                            
                            if(j == 0) {
                                chaves[i] = hospedes[j].getNumero(); // Guarda o ID do hóspede que tem a chave do quarto
                                quartos[i].setIdHospede(hospedes[j].getNumero());
                            }
                            if(j == 3) {
                                firstQuarto = true;
                            }
                        }
                        quartos[i].setOcupado(true);
                        quartosNecessarios -= 1;
                        if (quartosNecessarios == 0) {
                            setOcupado(false);
                            return true;
                        }
                    }
                }else {
                    setOcupado(false);
                    return true;
                }
            }
        }
        System.out.println("\033[41mNão há quartos disponíveis\033[0m");
        setOcupado(false);
        return false;
    }

    // Método para o hóspede deixar a chave na recepção ao sair
    public synchronized void deixarChave(int numeroQuarto) {
        chaves[numeroQuarto] = -1; // Marca que a chave está na recepção
        quartos[numeroQuarto].setLimpo(true);
        for (int i = 0; i < camareiras.length; i++) {
            if(!camareiras[i].isOcupado()) {
                camareiras[i].limparQuarto(quartos[numeroQuarto]);
                return;
            }
        }
    }

    // Método para o hóspede pegar a chave na recepção ao voltar
    public synchronized void pegarChave(int numeroQuarto, int idHospede) {
        chaves[numeroQuarto] = idHospede; // Marca que o hóspede tem a chave do quarto
        System.out.println("\033[36mHóspede pegou a chave na recepção.\033[0m");
    }

    public synchronized void irEmbora(int numeroQuarto) {
        for (Quarto quarto : quartos) {
            if (quarto.getNumero() == numeroQuarto) {
                quarto.checkOut(numeroQuarto); // Libera o quarto
                System.out.println("\033[31mHóspedes foram embora e liberou o quarto " + quarto.getNumero() + "\033[0m");
                return;
            }
        }
    }

    public synchronized boolean isOcupado() {
        return ocupado;
    }

    public synchronized void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
}
