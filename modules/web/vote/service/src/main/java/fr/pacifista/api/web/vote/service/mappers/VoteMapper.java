package fr.pacifista.api.web.vote.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.vote.client.dtos.VoteDTO;
import fr.pacifista.api.web.vote.service.entities.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoteMapper extends ApiMapper<Vote, VoteDTO> {

    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "yearVote", ignore = true)
    @Mapping(target = "monthVote", ignore = true)
    Vote toEntity(VoteDTO dto);

}
