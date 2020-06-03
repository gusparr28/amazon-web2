package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Pool {
	
	private static final int min = 4;
	private static final int max = 20;
	private static final int grow = 4;
	private static int actConn = min;
	private PropertiesReader prop = PropertiesReader.getInstance();
	private static ArrayList<Connection> arrConn = new ArrayList<>(max);

	private Pool() {
		Runtime.getRuntime().addShutdownHook(new Closer());

		try {
			Class.forName(prop.getValue("db.driver.class"));
			for (int i = 0; i < min; i++)
				arrConn.add(DriverManager.getConnection(prop.getValue("db.conn.url"),
						prop.getValue("db.user"), prop.getValue("db.password")));
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int getAvailConn() {
		return arrConn.size();
	}

	public int getTotalConn() {
		return actConn;
	}

	public boolean grow() {
		if (actConn < max) {
			try {
				for (int i = 0; i < grow; i++) {
					arrConn.add(DriverManager.getConnection(prop.getValue("db.url"),
							prop.getValue("db.user"), prop.getValue("db.password")));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			actConn += grow;
			return true;
		}
		return false;
	}

	public Connection getConn() {
		return arrConn.remove(0);
	}

	public synchronized void returnConn(Connection con) {
		arrConn.add(con);
	}

	private static ArrayList<Connection> getArrConn() {
		return arrConn;
	}

	//Método propio del singleton
	public static Pool getPool() {
		return PoolSingletonHolder.pool;
	}

	//Clase propia del singleton
	private static class PoolSingletonHolder {
		private static final Pool pool = new Pool();
	}

	//Clase que se ejecuta cuando se cierra el thread principal
	static class Closer extends Thread {
		public void run() {
			ArrayList<Connection> conn = Pool.getArrConn();
			try {
				for (int i = 0; i < Pool.actConn; i++)
					if (conn.get(i) != null)
						conn.get(i).close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
