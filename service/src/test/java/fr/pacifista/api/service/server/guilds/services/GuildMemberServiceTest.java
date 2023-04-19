package fr.pacifista.api.service.guilds.services;

import fr.pacifista.api.client.guilds.dtos.GuildDTO;
import fr.pacifista.api.client.guilds.dtos.GuildMemberDTO;
import fr.pacifista.api.client.guilds.enums.GuildRole;
import fr.pacifista.api.service.server.guilds.services.GuildMemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GuildMemberServiceTest extends GuildServiceTestHandler {

    @Autowired
    GuildMemberService guildMemberService;

    @Test
    void createEntity() {
        final GuildDTO guildDTO = super.generateGuild();
        final GuildMemberDTO memberDTO = new GuildMemberDTO();
        memberDTO.setGuildId(guildDTO.getId());
        memberDTO.setRole(GuildRole.MEMBER);
        memberDTO.setPlayerUuid(UUID.randomUUID());

        assertDoesNotThrow(() -> {
            final GuildMemberDTO response = this.guildMemberService.create(memberDTO);
            assertEquals(memberDTO.getGuildId(), response.getGuildId());
            assertEquals(memberDTO.getRole(), response.getRole());
            assertEquals(memberDTO.getPlayerUuid(), response.getPlayerUuid());
        });
    }

    @Test
    void patchEntity() {
        final GuildDTO guildDTO = super.generateGuild();
        final GuildMemberDTO memberDTO = new GuildMemberDTO();
        memberDTO.setGuildId(guildDTO.getId());
        memberDTO.setRole(GuildRole.MEMBER);
        memberDTO.setPlayerUuid(UUID.randomUUID());

        assertDoesNotThrow(() -> {
            final GuildMemberDTO response = this.guildMemberService.create(memberDTO);
            response.setRole(GuildRole.OFFICER);
            final GuildMemberDTO patched = this.guildMemberService.update(response);
            assertEquals(response.getRole(), patched.getRole());
        });
    }

}
