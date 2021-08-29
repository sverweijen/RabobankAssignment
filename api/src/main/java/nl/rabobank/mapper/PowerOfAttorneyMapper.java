package nl.rabobank.mapper;

import nl.rabobank.model.PowerOfAttorneyRequest;
import nl.rabobank.mongo.model.AccountDoc;
import nl.rabobank.mongo.model.PowerOfAttorneyDoc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PowerOfAttorneyMapper {

  @Mapping(target = "account", source = "account")
  @Mapping(target = "id", expression = "java(null)")
  PowerOfAttorneyDoc mapToPowerOfAttorney(PowerOfAttorneyRequest powerOfAttorneyRequest, AccountDoc account);
}
