package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	GenesDao dao; 
	Graph<String, DefaultWeightedEdge> grafo;
	List <String> vertici; 
	private List<String> migliore;
	
	public Model() {
		this.dao = new GenesDao(); 
		this.grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

	} 
	
	public String creaGrafo() {
		vertici= dao.getVertici();
		Graphs.addAllVertices(grafo, vertici); 
		for(String v1: vertici) {
			for (String v2: vertici) {
				if(v1!=v2) {
					int peso=dao.getPeso(v1, v2);
					if (peso>0)
						Graphs.addEdgeWithVertices(grafo, v1, v2, peso);
				}
			}
		}
		return "Grafo creato: "+ grafo.vertexSet().size()+" vertici,"+ grafo.edgeSet().size()+" archi"; 
	}

	public List<String> getVertici() {
		return vertici;
	}
	
	public Map<String, Integer> getConnessioni(String v1){
		List <String> vicini= Graphs.neighborListOf(grafo, v1); 
		Map <String, Integer> destinazionePeso= new HashMap<>();
		
		for(String v: vicini) {
			DefaultWeightedEdge e=grafo.getEdge(v1, v); //dati sorgente e destinaz,. restituisce arco
			int peso= (int) grafo.getEdgeWeight(e); // dato un arco restituisce il peso
		destinazionePeso.put(v, peso); 
		}
		return destinazionePeso; 
	}

	public List<String> calcolaPercorso(String sorg)
	{
		migliore = new LinkedList<String>();
		List<String> parziale = new LinkedList<>();
		parziale.add(sorg);
		cercaRicorsiva(parziale);
		return migliore;
	}

	private void cercaRicorsiva(List<String> parziale) {
		 
				//condizione di terminazione
				
					int pesoParziale = pesoTot(parziale);
					if(pesoParziale > pesoTot(migliore))//la strada piú lunga é la migliore
					{
						migliore = new LinkedList<>(parziale);
					}
					
		
				
				for(String v:Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1))) //scorro sui vicini dell'ultimo nodo sulla lista
				{
					if(!parziale.contains(v))
					{
						parziale.add(v);
						cercaRicorsiva(parziale);
						parziale.remove(parziale.size()-1);
					}
					
				}
		
	}

	private int pesoTot(List<String> parziale) {
		
		int peso = 0;
		//calcolare il peso degli archi
		for (int i=0; i<parziale.size()-1; i++) {
				DefaultWeightedEdge e=grafo.getEdge(parziale.get(i), parziale.get(i+1));
				peso += grafo.getEdgeWeight(e); 
				
		}
		System.out.println(peso); 
		return peso; 
		
		
	}
	
	

}