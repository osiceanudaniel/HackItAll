package ro.hackitall.encode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;


@SpringBootApplication
@EnableJpaRepositories("ro.hackitall.encode.dal.repo")
@EntityScan("ro.hackitall.encode.dal.model")
@EnableScheduling
public class EncodeApplication {

	public static void main(String[] args) {

		//UAT https
		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
				(hostname, sslSession) -> true
		);

		TrustManager[] trustAllCerts = new TrustManager[] {
				new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return new X509Certificate[0];
					}
					public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
						// not need to implement
					}
					public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
						// not need to implement
					}
				}
		};

		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}

		SpringApplication.run(EncodeApplication.class, args);
	}
}
