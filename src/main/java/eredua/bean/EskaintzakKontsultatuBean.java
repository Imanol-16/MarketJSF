package eredua.bean;

import java.io.Serializable;
import java.util.List;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import businessLogic.BLFacade;
import domain.Sale;

@Named("eskaintzakKontsultatu")
@SessionScoped
public class EskaintzakKontsultatuBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String bilaketaTestua;
	private List<Sale> eskaintzak;
	private Sale aukeratutakoEskaintza;

	public EskaintzakKontsultatuBean() {
	}

	public String getBilaketaTestua() {
		return bilaketaTestua;
	}

	public void setBilaketaTestua(String bilaketaTestua) {
		this.bilaketaTestua = bilaketaTestua;
	}

	public List<Sale> getEskaintzak() {
		return eskaintzak;
	}

	public void setEskaintzak(List<Sale> eskaintzak) {
		this.eskaintzak = eskaintzak;
	}

	public Sale getAukeratutakoEskaintza() {
		return aukeratutakoEskaintza;
	}

	public void setAukeratutakoEskaintza(Sale aukeratutakoEskaintza) {
		this.aukeratutakoEskaintza = aukeratutakoEskaintza;
	}

	public String bilatu() {
		try {
			this.aukeratutakoEskaintza = null; 
			BLFacade facadeBL = FacadeBean.getBusinessLogic();
			eskaintzak = facadeBL.getSales(bilaketaTestua);

			if (eskaintzak == null || eskaintzak.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ez da eskaintzarik aurkitu"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Errorea eskaintzak bilatzean: " + e.getMessage()));
		}
		return null;
	}

	public String ikusi(Sale eskaintza) {
		this.aukeratutakoEskaintza = eskaintza;
		return "ikusi";
	}

	public String getEgoeraTestua() {
		if (aukeratutakoEskaintza == null) {
			return "";
		}
		switch (aukeratutakoEskaintza.getStatus()) {
		case 0:
			return "Berria";
		case 1:
			return "Oso ona";
		case 2:
			return "Ona";
		case 3:
			return "Nahikoa";
		case 4:
			return "Hondatuta";
		default:
			return String.valueOf(aukeratutakoEskaintza.getStatus());
		}
	}
}