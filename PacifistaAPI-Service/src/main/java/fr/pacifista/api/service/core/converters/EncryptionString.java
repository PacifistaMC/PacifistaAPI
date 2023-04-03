package fr.pacifista.api.service.core.converters;

import fr.funixgaming.api.core.utils.encryption.ApiConverter;
import fr.funixgaming.api.core.utils.encryption.Encryption;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Converter
@RequiredArgsConstructor
public class EncryptionString implements ApiConverter<String> {

    private final Encryption encryption;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return encryption.convertToDatabase(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return encryption.convertToEntity(dbData);
    }

}
