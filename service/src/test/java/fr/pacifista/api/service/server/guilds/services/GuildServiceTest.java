package fr.pacifista.api.service.guilds.services;

import fr.funixgaming.api.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.client.core.enums.ServerType;
import fr.pacifista.api.client.guilds.dtos.GuildDTO;
import fr.pacifista.api.client.guilds.dtos.GuildHomeDTO;
import fr.pacifista.api.client.guilds.dtos.GuildMemberDTO;
import fr.pacifista.api.client.guilds.enums.GuildRole;
import fr.pacifista.api.service.server.guilds.services.GuildExperienceService;
import fr.pacifista.api.service.server.guilds.services.GuildHomeService;
import fr.pacifista.api.service.server.guilds.services.GuildMemberService;
import fr.pacifista.api.service.server.guilds.services.GuildService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuildServiceTest {
    
    @Autowired
    GuildService guildService;

    @Autowired
    GuildMemberService memberService;

    @Autowired
    GuildHomeService homeService;

    @Autowired
    GuildExperienceService experienceService;
    
    @Test
    void createEntity() {
        final GuildDTO guildDTO = new GuildDTO();
        guildDTO.setName(UUID.randomUUID().toString());
        guildDTO.setDescription(UUID.randomUUID().toString());
        guildDTO.setMoney(0.0);
        
        assertDoesNotThrow(() -> {
            final GuildDTO createdGuildDTO = guildService.create(guildDTO);
            assertEquals(guildDTO.getName(), createdGuildDTO.getName());
            assertEquals(guildDTO.getDescription(), createdGuildDTO.getDescription());
            assertEquals(guildDTO.getMoney(), createdGuildDTO.getMoney());
            assertNotNull(createdGuildDTO.getExperience());
            assertNotNull(createdGuildDTO.getExperience().getLevel());
            assertNotNull(createdGuildDTO.getExperience().getGuildId());
        });
    }
    
    @Test
    void patchEntity() {
        final GuildDTO guildDTO = new GuildDTO();
        guildDTO.setName(UUID.randomUUID().toString());
        guildDTO.setDescription(UUID.randomUUID().toString());
        guildDTO.setMoney(0.0);
        
        assertDoesNotThrow(() -> {
            final GuildDTO createdGuildDTO = guildService.create(guildDTO);
            createdGuildDTO.setName("dddkdkdkdkdk");
            
            final GuildDTO patchedGuildDTO = guildService.update(createdGuildDTO);
            assertEquals(createdGuildDTO.getName(), patchedGuildDTO.getName());
        });
    }
    
    @Test
    void deleteEntity() {
        final GuildDTO guildDTO = new GuildDTO();
        guildDTO.setName(UUID.randomUUID().toString());
        guildDTO.setDescription(UUID.randomUUID().toString());
        guildDTO.setMoney(0.0);
        
        assertDoesNotThrow(() -> {
            final GuildDTO createdGuildDTO = guildService.create(guildDTO);
            guildService.delete(createdGuildDTO.getId().toString());
            
            assertThrowsExactly(ApiNotFoundException.class, () -> {
                guildService.findById(createdGuildDTO.getId().toString());
                experienceService.findById(createdGuildDTO.getExperience().getId().toString());
            });
        });
    }
    
    @Test
    void deleteGuildWithMembers() {
        final GuildDTO guildDTO = new GuildDTO();
        guildDTO.setName(UUID.randomUUID().toString());
        guildDTO.setDescription(UUID.randomUUID().toString());
        guildDTO.setMoney(0.0);
        
        assertDoesNotThrow(() -> {
            final GuildDTO createdGuildDTO = guildService.create(guildDTO);
            final GuildMemberDTO memberDTO = new GuildMemberDTO();
            memberDTO.setGuildId(createdGuildDTO.getId());
            memberDTO.setPlayerUuid(UUID.randomUUID());
            memberDTO.setRole(GuildRole.MEMBER);

            final GuildMemberDTO memberDTOCreated = memberService.create(memberDTO);
            guildService.delete(createdGuildDTO.getId().toString());

            assertThrowsExactly(ApiNotFoundException.class, () -> {
                guildService.findById(createdGuildDTO.getId().toString());
                memberService.findById(memberDTOCreated.getId().toString());
            });
        });
    }

    @Test
    void deleteGuildWithHomes() {
        final GuildDTO guildDTO = new GuildDTO();
        guildDTO.setName(UUID.randomUUID().toString());
        guildDTO.setDescription(UUID.randomUUID().toString());
        guildDTO.setMoney(0.0);

        assertDoesNotThrow(() -> {
            final GuildDTO createdGuildDTO = guildService.create(guildDTO);
            final GuildHomeDTO homeDTO = new GuildHomeDTO();
            homeDTO.setGuildId(createdGuildDTO.getId());
            homeDTO.setPublicAccess(true);
            homeDTO.setWorldUuid(UUID.randomUUID());
            homeDTO.setX(0d);
            homeDTO.setY(0d);
            homeDTO.setZ(0d);
            homeDTO.setYaw(0f);
            homeDTO.setPitch(0f);
            homeDTO.setServerType(ServerType.CREATIVE);

            final GuildHomeDTO createdHome = homeService.create(homeDTO);

            guildService.delete(createdGuildDTO.getId().toString());

            assertThrowsExactly(ApiNotFoundException.class, () -> {
                guildService.findById(createdGuildDTO.getId().toString());
                homeService.findById(createdHome.getId().toString());
            });
        });
    }
    
}
