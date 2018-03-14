package ru.sbt.util.pcaccessapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginData {
    private String lwssoCookieKey;
    private String qcSession;

    public boolean isLoginDataFilled() {
        return lwssoCookieKey != null && qcSession != null;
    }
}
