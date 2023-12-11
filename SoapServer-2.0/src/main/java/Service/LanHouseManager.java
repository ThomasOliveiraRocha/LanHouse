package Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LanHouseManager {

    private List<Computador> computadores;
    private static LanHouseManager instance;
    private final Lock lock = new ReentrantLock();

    private LanHouseManager() {
        this.computadores = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Computador computador = new Computador();
            computador.setNumero(i);
            computador.setReservado(false);
            computador.setCliente(null);
            computadores.add(computador);
        }
    }

    public static LanHouseManager getInstance() {
        if (instance == null) {
            System.out.println("Criando nova instância de LanHouseManager");
            instance = new LanHouseManager();
        }
        return instance;
    }

    public List<Computador> getComputadores() {
        return computadores;
    }

   public String reservarComputador(int numeroComputador, String cliente) {
    Computador computador = getComputadorByNumero(numeroComputador);

    if (computador != null) {
        if (lock.tryLock()) {
            try {
                // Fase de Crescimento
                if (!computador.isReservado()) {
                    Thread.sleep(1000);
                    computador.setReservado(true);
                    computador.setCliente(cliente);
                    System.out.println("Computador " + numeroComputador + " reservado para " + cliente);
                    return "Computador reservado para " + cliente;
                } else {
                    System.out.println("Computador " + numeroComputador + " já está reservado pelo " + computador.getCliente());
                    return "Computador já está reservado.";
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Ocorreu um erro ao tentar reservar o computador " + numeroComputador);
                return "Ocorreu um erro ao tentar reservar o computador.";
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Não foi possível adquirir o bloqueio para o computador " + numeroComputador);
            return "Não foi possível reservar o computador.";
        }
    } else {
        System.out.println("Computador " + numeroComputador + " não encontrado.");
        return "Computador não encontrado.";
    }
}

   public String liberarComputador(int numeroComputador) {
        Computador computador = getComputadorByNumero(numeroComputador);

        if (computador != null) {
            if (lock.tryLock()) {
                try {
                    // Fase de Liberação
                    if (computador.isReservado()) {
                        computador.setReservado(false);
                        computador.setCliente(null);
                        System.out.println("Computador " + numeroComputador + " liberado com sucesso");
                        return "Computador liberado com sucesso";
                    } else {
                        System.out.println("Computador " + numeroComputador + " não está reservado.");
                        return "Computador não está reservado.";
                    }
                } finally {
                    lock.unlock();  // Mover o unlock para dentro do tryLock
                }
            } else {
                System.out.println("Não foi possível adquirir o bloqueio para o computador " + numeroComputador);
                return "Não foi possível liberar o computador.";
            }
        } else {
            System.out.println("Computador " + numeroComputador + " não encontrado.");
            return "Computador não encontrado.";
        }
    }

    public Computador getComputadorByNumero(int numeroComputador) {
        for (Computador computador : computadores) {
            if (computador.getNumero() == numeroComputador) {
                return computador;
            }
        }
        return null;
    }
}
