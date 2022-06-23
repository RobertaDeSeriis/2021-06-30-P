package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<String> getVertici(){
		String sql = "SELECT distinct Localization "
				+ "FROM classification "
				+ "ORDER BY Localization ";
		List<String> result = new ArrayList<String>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				result.add(res.getString("Localization"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}

	//conviene solo per pochi archi, per tanti non va bene
	public int getPeso(String v1, String v2){
		String sql = "SELECT COUNT(DISTINCT i.`Type`) as peso "
				+ "FROM classification c1, classification c2, interactions i "
				+ "WHERE c1.Localization <> c2.Localization  "
				+ "	AND ((c1.GeneID=i.GeneID1 AND c2.GeneID=i.GeneID2) OR  (c2.GeneID=i.GeneID1 AND c1.GeneID=i.GeneID2)) "
				+ " 	and c1.Localization=? and c2.Localization=? ";
		
		int peso=0; 
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, v1);
			st.setString(2, v2);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				peso=(res.getInt("peso"));
			}
			res.close();
			st.close();
			conn.close();
			return peso;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}

	
}
