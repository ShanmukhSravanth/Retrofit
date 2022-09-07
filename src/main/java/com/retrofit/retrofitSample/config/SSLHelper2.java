package com.retrofit.retrofitSample.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SSLHelper2 {

	public static HostnameVerifier getHostnameVerifier() {
		final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		return hostnameVerifier;
	}

	public static SSLSocketFactory getSocketFactory() {
		final TrustManager[] certs = new TrustManager[] { new X509TrustManager() {

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkServerTrusted(final X509Certificate[] chain, final String authType)
					throws CertificateException {
			}

			@Override
			public void checkClientTrusted(final X509Certificate[] chain, final String authType)
					throws CertificateException {
			}
		} };

		SSLContext ctx = null;
		try {
			ctx = SSLContext.getInstance("TLS");
			ctx.init(null, certs, new SecureRandom());
			return ctx.getSocketFactory();
		} catch (final java.security.GeneralSecurityException ex) {
		}
		return null;
	}

	public static SSLContext getSSLConfig(String path)  {
		try {
//		Context context
// Loading CAs from an InputStream
		CertificateFactory cf = null;
		cf = CertificateFactory.getInstance("X.509");

		 java.security.cert.Certificate ca;
// I'm using Java7. If you used Java6 close it manually with finally.
		try (InputStream cert = new FileInputStream(path)) {
			ca = cf.generateCertificate(cert);
		}

// Creating a KeyStore containing our trusted CAs
		String keyStoreType = KeyStore.getDefaultType();
		KeyStore keyStore = KeyStore.getInstance(keyStoreType);
		keyStore.load(null, null);
		keyStore.setCertificateEntry("ca", ca);

// Creating a TrustManager that trusts the CAs in our KeyStore.
		String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
		tmf.init(keyStore);

// Creating an SSLSocketFactory that uses our TrustManager
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, tmf.getTrustManagers(), null);

		return sslContext;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
