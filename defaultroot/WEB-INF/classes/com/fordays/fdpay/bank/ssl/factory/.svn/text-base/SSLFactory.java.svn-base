package com.fordays.fdpay.bank.ssl.factory;

import javax.net.ssl.SSLSocketFactory;
import com.neza.exception.AppException;

public interface SSLFactory {

	public SSLSocketFactory getSSLSocketFactory_SUN(String keystore,
			String keypass, String truststore, String trustpass)
			throws AppException;

	public SSLSocketFactory getSSLSocketFactory_IBM(String keystore,
			String keypass, String truststore, String trustpass)
			throws AppException;

}
