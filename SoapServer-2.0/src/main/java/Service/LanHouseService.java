package Service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public class LanHouseService {

    private LanHouseManager lanHouseManager;

    public LanHouseService() {
        lanHouseManager = LanHouseManager.getInstance();
    }

    @WebMethod
    public String reservarComputador(int numeroComputador, String cliente) {
        return lanHouseManager.reservarComputador(numeroComputador, cliente);
    }

    @WebMethod
    public String liberarComputador(int numeroComputador) {
        return lanHouseManager.liberarComputador(numeroComputador);
    }

    @WebMethod
    public List<Computador> getComputadoresReservados() {
        return lanHouseManager.getComputadores();
    }

    public void setLanHouseManager(LanHouseManager lanHouseManager) {
        this.lanHouseManager = lanHouseManager;
    }

    public Computador getComputadorByNumero(int numeroComputador) {
        return lanHouseManager.getComputadorByNumero(numeroComputador);
    }
}
