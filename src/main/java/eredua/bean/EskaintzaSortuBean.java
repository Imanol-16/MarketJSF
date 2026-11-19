package eredua.bean;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;
import businessLogic.BLFacade;
import domain.Sale;

@Named("eskaintzaSortu")
@SessionScoped
public class EskaintzaSortuBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tituloa;
	private String deskripzioa;
	private int egoera;
	private float prezioa;
	private Date argitarapenEguna;
	private Part argazkia;

	public EskaintzaSortuBean() {
	}

	public String getTituloa() {
		return tituloa;
	}

	public void setTituloa(String tituloa) {
		this.tituloa = tituloa;
	}

	public String getDeskripzioa() {
		return deskripzioa;
	}

	public void setDeskripzioa(String deskripzioa) {
		this.deskripzioa = deskripzioa;
	}

	public int getEgoera() {
		return egoera;
	}

	public void setEgoera(int egoera) {
		this.egoera = egoera;
	}

	public float getPrezioa() {
		return prezioa;
	}

	public void setPrezioa(float prezioa) {
		this.prezioa = prezioa;
	}

	public Date getArgitarapenEguna() {
		return argitarapenEguna;
	}

	public void setArgitarapenEguna(Date argitarapenEguna) {
		this.argitarapenEguna = argitarapenEguna;
	}

	public Part getArgazkia() {
		return argazkia;
	}

	public void setArgazkia(Part argazkia) {
		this.argazkia = argazkia;
	}

	public String sortu() {
		try {
			File irudiaFile = null;
			if (argazkia != null && argazkia.getSize() > 0) {
				String fitxategiIzena = argazkia.getSubmittedFileName();
				irudiaFile = new File(System.getProperty("java.io.tmpdir"), fitxategiIzena);
				try (InputStream input = argazkia.getInputStream()) {
					Files.copy(input, irudiaFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				}
			}

			BLFacade facadeBL = FacadeBean.getBusinessLogic();
			Sale sale = facadeBL.createSale(tituloa, deskripzioa, egoera, prezioa, argitarapenEguna,
					"seller3@gmail.com", irudiaFile);

			if (sale != null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eskaintza sortuta"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ezin izan da eskaintza sortu"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Errorea: " + e.getMessage()));
		}
		return null;
	}
}