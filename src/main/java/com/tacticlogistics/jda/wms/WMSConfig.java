package com.tacticlogistics.jda.wms;

import java.math.BigInteger;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.tacticlogistics.jda.wms.db.entities.Control;
import com.tacticlogistics.jda.wms.db.entities.PurchaseOrder;
import com.tacticlogistics.jda.wms.db.entities.PurchaseOrderLine;

@Configuration
public class WMSConfig {

	@Bean
	public ConnectionSource wmsSource() throws SQLException {
		String databaseUrl = "jdbc:sqlserver://192.168.10.16:1433;databaseName=ttcwmsdev;";
		return new JdbcConnectionSource(databaseUrl, "ttcwmsdev", "ttcwmsdev");
	}

	@Bean
	@Qualifier("controlDao")
	public Dao<Control, BigInteger> controlDAO(ConnectionSource source) throws SQLException {
		return DaoManager.createDao(source, Control.class);
	}

	@Bean
	@Qualifier("controlDao")
	public Dao<PurchaseOrder, BigInteger> purchaseOrderDAO(ConnectionSource source) throws SQLException {
		return DaoManager.createDao(source, PurchaseOrder.class);
	}

	@Bean
	@Qualifier("controlDao")
	public Dao<PurchaseOrderLine, BigInteger> purchaseOrderLineDAO(ConnectionSource source) throws SQLException {
		return DaoManager.createDao(source, PurchaseOrderLine.class);
	}
}
