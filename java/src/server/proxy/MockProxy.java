package server.proxy;

import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class MockProxy extends HttpsURLConnection implements IServer{

	protected MockProxy(URL url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getCipherSuite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Certificate[] getLocalCertificates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Certificate[] getServerCertificates()
			throws SSLPeerUnverifiedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean usingProxy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void connect() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
