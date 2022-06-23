package it.polito.tdp.genes;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnStatistiche;

    @FXML
    private Button btnRicerca;

    @FXML
    private ComboBox<String> boxLocalizzazione;

    @FXML
    private TextArea txtResult;

    @FXML
    void doRicerca(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	if (boxLocalizzazione.getValue()!= null) {
    		for(String v: model.calcolaPercorso(this.boxLocalizzazione.getValue())) {
    			this.txtResult.appendText(v+"\n");
    		}
    	}
    }

    @FXML
    void doStatistiche(ActionEvent event) {
    	txtResult.clear();
    	
    	if (boxLocalizzazione.getValue()!= null) {
    		txtResult.appendText( "Adiacenti a: "+boxLocalizzazione.getValue());
    		Map <String, Integer> map= model.getConnessioni(boxLocalizzazione.getValue());
    		for (String v: map.keySet()) {
    			txtResult.appendText("\n" +v+"  "+ map.get(v));
    		}
    			
    	}
    }

    @FXML
    void initialize() {
        assert btnStatistiche != null : "fx:id=\"btnStatistiche\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxLocalizzazione != null : "fx:id=\"boxLocalizzazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		txtResult.appendText(model.creaGrafo());
		boxLocalizzazione.getItems().addAll(model.getVertici());
	}
}
