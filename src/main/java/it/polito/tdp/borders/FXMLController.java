
package it.polito.tdp.borders;

import java.net.URL;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML
    private ComboBox<Country> cmbBox;
    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	txtResult.clear();
    	String sAnno=txtAnno.getText();
    	if(sAnno.length()==0) {
    		txtResult.setText("non hai inserito l'anno");
    		return;
    	}
    	int anno=0;
    	try {
    		anno=Integer.parseInt(sAnno);
    	}catch(NumberFormatException e) {
    		txtResult.setText("deve eseere un  numero");
    		return;
    		}
    	if(anno<1816|| anno>2006) {
    		txtResult.setText("l'anno deve essere compreso tra 1816 e 2006");
    		return;
    	}
    	model.creaGrafo(anno);
    	cmbBox.getItems().addAll(model.getCountry());
    	txtResult.appendText("grafo creato! \n");
    	txtResult.appendText("gli archi sono "+model.getNArchi()+"\n");
    	txtResult.appendText("i vertici sono "+model.getNVertici()+"\n");
    	txtResult.appendText("il numero di componenti connesse è : "+model.nComponentiConnesse()+"\n");
    	Map<Country,Integer>countries=model.nVicini();
    	for(Country c:countries.keySet())
    		txtResult.appendText(c+" : "+countries.get(c)+"\n");
    }

    
    @FXML
    void doRaggiungibili(ActionEvent event) {
    	txtResult.clear();
    	Country partenza=cmbBox.getValue();
    	if(partenza==null) {
    		txtResult.setText("devi scegliere uno stato");
    		return ;
    	}
    	List<Country>stampa=model.trovaRaggiungibili(partenza);
    	if(stampa==null) {
    		txtResult.setText("non ci sono stati raggiungibili");
    		return;
    	}
    	txtResult.appendText("ecco fli stati raggiungibili : \n"+stampa.size()+"\n");
    	for(Country c:stampa) {
    		txtResult.appendText(c+"\n");
    	}
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbBox != null : "fx:id=\"cmbBox\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;}
    	
}
