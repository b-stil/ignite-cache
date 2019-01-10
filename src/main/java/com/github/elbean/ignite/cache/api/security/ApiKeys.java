package com.github.elbean.ignite.cache.api.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "security")
public class ApiKeys {

    private List<ApiKey> apiKeys = new ArrayList<ApiKey>();

    public List<ApiKey> getApiKeys() {
        return this.apiKeys;
    }

    public String getApiKey(String user) {
        String temp = this.apiKeys.stream().filter(u -> user.equals(u.getUser())).findFirst().get().getEncryptedKey();
        return temp;
    }

    public static class ApiKey {

        private String user;
        private String encryptedKey;

        public String getUser() {
            return this.user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getEncryptedKey() {
            return this.encryptedKey;
        }

        public void setEncryptedKey(String encryptedKey) {
            this.encryptedKey = encryptedKey;
        }
    }
}
