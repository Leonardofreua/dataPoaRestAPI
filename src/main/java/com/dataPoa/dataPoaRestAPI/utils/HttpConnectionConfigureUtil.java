package com.dataPoa.dataPoaRestAPI.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class HttpConnectionConfigureUtil {

    /**
     * Executa a conexão com o HTTP de acordo com as configurações definidas.
     *
     * @param urlAddress url de acesso ao serviço
     * @return os dados referente a conexão extabelecida com o serviço
     * @throws IOException
     */
    public HttpURLConnection executeHttpConnection(String urlAddress) throws IOException {
        URL url = new URL(urlAddress);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        this.setParamsToConnection(conn);

        return conn;
    }

    /**
     * define algumas configurações para a conexão via Http.
     *
     * @param conn {@link HttpURLConnection}
     * @throws ProtocolException
     */
    private void setParamsToConnection(HttpURLConnection conn) throws ProtocolException {
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
    }
}
