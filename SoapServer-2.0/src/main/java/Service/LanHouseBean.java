package Service;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;

@ManagedBean
@SessionScoped
public class LanHouseBean {

    private int numeroComputador;
    private String cliente;
    private LanHouseService lanHouseService;  // Adicione esta linha

    public LanHouseBean() throws Exception {
        lanHouseService = createLanHouseService();  // Adicione esta linha ao construtor
    }

    public List<Integer> getListaNumerosComputadores() {
        List<Integer> numeros = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            numeros.add(i);
        }
        return numeros;
    }

    public int getNumeroComputador() {
        return numeroComputador;
    }

    public void setNumeroComputador(int numeroComputador) {
        this.numeroComputador = numeroComputador;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    // Métodos de ação
    public String reservar() {
    try {
        Computador computador = lanHouseService.getComputadorByNumero(numeroComputador);

        if (computador != null) {
            String resultado = lanHouseService.reservarComputador(numeroComputador, cliente);

            if (resultado.startsWith("Computador reservado")) {
                displayInfoMessage("Sucesso", resultado);
                System.out.println("Computador " + numeroComputador + " reservado com sucesso");
            } else {
                displayErrorMessage("Erro ao Reservar", resultado);
                System.out.println("Erro ao reservar o computador " + numeroComputador + ": " + resultado);
            }
        } else {
            displayErrorMessage("Erro ao Reservar", "Computador " + numeroComputador + " não encontrado.");
        }
    } catch (Exception e) {
        displayErrorMessage("Erro ao Reservar", e.getMessage());
        e.printStackTrace();
    }
    return null; // Mantém na mesma página
}

     public String liberar() {
        try {
            String resultado = lanHouseService.liberarComputador(numeroComputador);
            displayInfoMessage("Sucesso", resultado);
            System.out.println("Computador " + numeroComputador + " liberado com sucesso");
        } catch (Exception e) {
            displayErrorMessage("Erro ao Liberar", e.getMessage());
            e.printStackTrace();
        }
        return null; // Mantém na mesma página
    }

    private LanHouseService createLanHouseService() throws Exception {
        URL url = new URL("http://localhost:8080/SoapServer-2.0/lanhouseservice?wsdl");
        LanHouseService lanHouseService = new LanHouseService();
        lanHouseService.setLanHouseManager(LanHouseManager.getInstance());  // Defina o LanHouseManager na instância do serviço
        return lanHouseService;
    }

    public List<Computador> getComputadores() {
        // Obtém a lista de computadores diretamente do LanHouseManager
        return LanHouseManager.getInstance().getComputadores();
    }

    private void displayInfoMessage(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));

    }

    private void displayErrorMessage(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
    }

}
