package com.gmi.nordborglab.browser.server.transaction.manager;

import java.util.Properties;

import javax.transaction.TransactionManager;

import net.sf.ehcache.transaction.manager.TransactionManagerLookup;
import net.sf.ehcache.transaction.xa.EhcacheXAResource;

public class JpaTransactionManagerLookup implements TransactionManagerLookup {
	
	private TransactionManager transactionManager;

	@Override
	public TransactionManager getTransactionManager() {
		return transactionManager;
	}
	
	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager; 
	}

	@Override
	public void register(EhcacheXAResource resource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregister(EhcacheXAResource resource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub

	}

}
