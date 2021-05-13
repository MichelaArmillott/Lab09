package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map<Integer, Country> idMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if(!idMap.containsKey(rs.getInt("ccode"))) {
					Country c=new Country (rs.getInt("ccode"),rs.getString("StateAbb"),rs.getString("StateNme"));
					idMap.put(c.getId(),c);
				}
				//System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
			}
			
			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database0");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) {

		System.out.println("TODO -- BordersDAO -- getCountryPairs(int anno)");
		return new ArrayList<Border>();
	}

	public List <Country> getVertici(int anno, Map<Integer, Country> idMap) {
		String sql="SELECT c.CCode "
				+ "FROM country c,contiguity co "
				+ "WHERE c.CCode=co.state1no OR c.CCode=co.state2no AND co.year<= ? "
				+ "GROUP BY c.CCode ";
		List <Country>result =new ArrayList<>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,anno);
			ResultSet rs = st.executeQuery();
          

			while (rs.next()) {
			result.add(idMap.get(rs.getInt("CCode")));	
			}
			
			conn.close();
			return result;
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database1");
			throw new RuntimeException("Error Connection Database");
		}
		
	}

	public List<Border> getConfini(Map<Integer, Country> idMap,int anno) {
		String sql="SELECT c.state1no,c.state2no "
				+ "FROM contiguity c "
				+"WHERE c.conttype=1 AND c.year<=? "
				+ "GROUP BY c.state1no,c.state2no ";
		List<Border>result=new ArrayList<>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
          

			while (rs.next()) {
			result.add(new Border(idMap.get(rs.getInt("c.state1no")),idMap.get(rs.getInt("c.state2no")),0.0));	
			}
			
			conn.close();
			return result;
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database2");
			throw new RuntimeException("Error Connection Database");
		}
	
	}
}
