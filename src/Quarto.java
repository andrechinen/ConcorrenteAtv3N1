// Classe Quarto
public class Quarto {
    private int numero;
    private boolean ocupado;
    private boolean chaveNaRecepcao;
    private int idHospede;
    private Hospede[] hospedes;

    private boolean limpezaEmAndamento;
    private boolean foiLimpo = false;

    public Quarto(int numero) {
        this.numero = numero;
        this.ocupado = false;
        this.chaveNaRecepcao = true; // Inicialmente a chave está na recepção
        this.idHospede = -1; // Inicialmente nenhum hóspede está no quarto (-1 representa nenhum hóspede)
        this.limpezaEmAndamento = false; // Inicialmente a limpeza não está em andamento
        this.hospedes = new Hospede[4]; // Inicialize o array com o tamanho máximo de hóspedes por quarto
    }

    public int getNumero() {
        return numero;
    }

    public synchronized boolean isOcupado() {
        return ocupado;
    }

    public synchronized void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public synchronized void setLimpo(boolean foiLimpo) {
        this.foiLimpo = foiLimpo;
    }

    public synchronized boolean isLimpo() {
        return foiLimpo;
    }

    public synchronized void setHospede(Hospede hospede, int index) {
        hospedes[index] = hospede; 
    }

    public synchronized boolean isChaveNaRecepcao() {
        return chaveNaRecepcao;
    }

    public synchronized void setChaveNaRecepcao(boolean chaveNaRecepcao) {
        this.chaveNaRecepcao = chaveNaRecepcao;
    }

    public synchronized int getIdHospede() {
        return idHospede;
    }

    public synchronized void setIdHospede(int idHospede) {
        this.idHospede = idHospede;
    }

    // Método para fazer check-in de um hóspede no quarto
    public synchronized boolean checkIn(int idHospede) {
        if (!ocupado && chaveNaRecepcao) {
            ocupado = true;
            chaveNaRecepcao = false;
            this.idHospede = idHospede; // Define o id do hóspede que está ocupando o quarto
            return true;
        }
        return false;
    }

    // Método para fazer check-out de um hóspede do quarto
    public synchronized void checkOut(int numeroQuarto) {
        ocupado = false;
        chaveNaRecepcao = true;
        idHospede = -1; // Define que o quarto está vazio

        // Limpa o array 'hospedes' sem perder referências
        for (int i = 0; i < hospedes.length; i++) {
            hospedes[i] = null;
        }

        // Reduz o tamanho do array para 4
        hospedes = new Hospede[4];
    }

    public synchronized boolean isLimpezaEmAndamento() {
        return limpezaEmAndamento;
    }

    public synchronized void iniciarLimpeza() {
        this.limpezaEmAndamento = true;
    }

    public synchronized void finalizarLimpeza() {
        this.limpezaEmAndamento = false;
    }

}