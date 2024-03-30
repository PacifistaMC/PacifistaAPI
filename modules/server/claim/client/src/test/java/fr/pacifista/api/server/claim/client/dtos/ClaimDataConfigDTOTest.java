package fr.pacifista.api.server.claim.client.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClaimDataConfigDTOTest {

    @Test
    void testDataBlocksPrice() {
        final ClaimDataDTO claimDataDTO = new ClaimDataDTO();

        claimDataDTO.setLesserBoundaryCornerX(10.0);
        claimDataDTO.setLesserBoundaryCornerZ(10.0);
        claimDataDTO.setGreaterBoundaryCornerX(20.0);
        claimDataDTO.setGreaterBoundaryCornerZ(20.0);

        assertEquals(100.0, claimDataDTO.getClaimCost(1.0));
        assertEquals(200.0, claimDataDTO.getClaimCost(2.0));
    }

}
