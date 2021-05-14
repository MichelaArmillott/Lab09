package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	private SimpleGraph<Country,DefaultEdge>grafo;
	private BordersDAO dao;
	private Map<Integer,Country>idMap;

	public Model() {
		dao=new BordersDAO();
		idMap=new HashMap<Integer,Country>();
		dao.loadAllCountries(idMap);
		}
	
	public void creaGrafo(int anno){
		grafo=new SimpleGraph<>(DefaultEdge.class);
		//aggiungo i vertici
		Graphs.addAllVertices(grafo, dao.getVertici(anno,idMap));
		//aggiungo gli archi
		for(Border b:dao.getConfini(idMap,anno)) {
			if(grafo.containsVertex(b.getC1())&& grafo.containsVertex(b.getC2())) {//questo for per controllare che i vertici appartengano al grafo
				DefaultEdge e=grafo.getEdge(b.getC1(), b.getC2());
				//se non è presente nè il confine 1-2 nè 2-1,aggiungo,altrimenti non faccio nulla
				if(e==null) {
					Graphs.addEdgeWithVertices(grafo, b.getC1(),b.getC2());
				}
			}
		}
	}
	//i seguenti due metodi servono solo per fare dei controlli nel momento della stampa
			public int getNVertici() {
				if(grafo != null)
					return grafo.vertexSet().size();
				
				return 0;
			}
			
			public int getNArchi() {
				if(grafo != null)
					return grafo.edgeSet().size();
				
				return 0;
			}
			//per contare i vicini di ogni stato(grado del vertice)
			public Map<Country,Integer> nVicini(){
				if(grafo==null)
					return null;
				Map<Country,Integer>stampa=new HashMap<>();
				for(Country c:grafo.vertexSet())
					stampa.put(c, grafo.degreeOf(c));
				return stampa;
					
			}
			//per contare il numero di componenti connesse
			public int nComponentiConnesse() {
				if(grafo==null)
					return 0;
				ConnectivityInspector<Country,DefaultEdge> ci=new ConnectivityInspector<>(grafo);
				return ci.connectedSets().size();
			}
			
			//vertici per la comboBox
			public Set <Country>getCountry(){
				return grafo.vertexSet();
			}

			public List<Country> trovaRaggiungibili(Country partenza) {
				List<Country>stampa=new ArrayList<>();
				BreadthFirstIterator<Country, DefaultEdge> bfv = 
						new BreadthFirstIterator<>(this.grafo, partenza) ;
				
				while(bfv.hasNext()) {
					Country c=bfv.next() ; 
					stampa.add(c);
					c = bfv.getParent(c) ;
				}
				return stampa;
			}
}
