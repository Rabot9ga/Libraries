package ru.sbt.util.pcaccessapi;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ru.sbt.util.pcaccessapi.dto.LoginData;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.StringJoiner;

import static ru.sbt.util.pcaccessapi.utils.Constants.JSON;
import static ru.sbt.util.pcaccessapi.utils.Constants.URI_LOGIN;

@Slf4j
@AllArgsConstructor
class PCAuthenticationInterceptor implements Interceptor {

    private final String url;
    private final String username;
    private final String password;
    private final LoginData loginData;

    @Override
    public Response intercept(Chain chain) throws IOException {
        log.trace("Intercept!");
// FIXME: 15.03.2018 Переписать логгирование

        if (!loginData.isLoginDataFilled()) {
            log.trace("loginData is not filled, call auth:");
            authAndSaveCookie(chain);
            return sendPreparedRq(chain);

        } else {
            Response response = sendPreparedRq(chain);

            if (!response.isSuccessful()) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    log.trace("response ");
                    authAndSaveCookie(chain);
                    return sendPreparedRq(chain);
                }
            }
            return response;
        }
    }

    private void authAndSaveCookie(Chain chain) throws IOException {
        Response authenticateRs = authenticate(chain);
        cookieParse(authenticateRs);
    }

    private Response sendPreparedRq(Chain chain) throws IOException {
        Request request = chain.request();
        log.trace("sendPreparedRq request: {}", request);

        Request newRequest = request.newBuilder()
                .addHeader("Cookie", cookieJoin())
                .addHeader("Content-Type", "application/json")
                .addHeader("Connection", "keep-alive")
                .build();
        log.trace("sendPreparedRq newRequest: {}", newRequest);

        Response response = chain.proceed(newRequest);
        log.trace("sendPreparedRq response: {}", response);
        return response;
    }

    private void cookieParse(Response authenticateRs) {
        List<String> headers = authenticateRs.headers("Set-Cookie");
        for (String header : headers) {
            if (header.contains("LWSSO_COOKIE_KEY"))
                loginData.setLwssoCookieKey(header.split(";")[0]);
            else if (header.contains("QCSession"))
                loginData.setQcSession(header.split(";")[0]);
        }
    }

    private String cookieJoin() {
        StringJoiner joiner = new StringJoiner(";");
        return joiner.add(loginData.getLwssoCookieKey())
                .add(loginData.getQcSession())
                .add("LoginClient=qc; ")
                .toString();
    }

    private Response authenticate(Chain chain) throws IOException {
        String credentials = Credentials.basic(username, password);

        String properUrl = url.endsWith("/") ? url : url + "/";

        Request authRequest = new Request.Builder()
                .url(properUrl + URI_LOGIN)
                //                .removeHeader("Content-Type")
                .addHeader("Accept", JSON)
                .addHeader("Authorization", credentials)
                .build();
        log.trace("+---> HTTP GET {}", authRequest.url());

        Response authResponse = chain.proceed(authRequest);
        log.trace("+<--- HTTP {} {}", authResponse.code(), authResponse.request().url());

        return authResponse;
    }

}