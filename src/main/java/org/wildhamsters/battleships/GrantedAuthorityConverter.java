package org.wildhamsters.battleships;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

/**
 * @author Piotr Chowaniec
 */
@Converter
class GrantedAuthorityConverter implements AttributeConverter<GrantedAuthority, String> {

    @Override
    public String convertToDatabaseColumn(GrantedAuthority authority) {
        return Optional.ofNullable(authority)
                .map(GrantedAuthority::getAuthority)
                .orElse(null);
    }

    @Override
    public GrantedAuthority convertToEntityAttribute(String role) {
        return Optional.ofNullable(role)
                .map(SimpleGrantedAuthority::new)
                .orElse(null);
    }
}
