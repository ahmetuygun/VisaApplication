package com.krakozhia.visa.securityCheck.domain;


import java.util.Optional;

public interface SecurityCheckRepository {

    Long generateId();

    void save(SecurityCheck securityCheck);

    void sendForNiaSecurityCheck(SecurityCheck securityCheck);

    void sendForHomelandSecurityCheck(SecurityCheck securityCheck);

    void sendForInterpolSecurityCheck(SecurityCheck securityCheck);

    Optional<SecurityCheck> retreiveSecurityCheck(SecurityCheckId securityCheckId);
}
