package fr.pacifista.api.server.claim.client.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClaimDataConfigDTOTest {

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

    @Test
    void testClaimIsInsideAnother() {
        final ClaimDataDTO claimDataDTO = new ClaimDataDTO();
        final ClaimDataDTO parent = new ClaimDataDTO();

        claimDataDTO.setLesserBoundaryCornerX(10.0);
        claimDataDTO.setLesserBoundaryCornerZ(10.0);
        claimDataDTO.setGreaterBoundaryCornerX(20.0);
        claimDataDTO.setGreaterBoundaryCornerZ(20.0);

        parent.setLesserBoundaryCornerX(5.0);
        parent.setLesserBoundaryCornerZ(5.0);
        parent.setGreaterBoundaryCornerX(25.0);
        parent.setGreaterBoundaryCornerZ(25.0);

        assertTrue(claimDataDTO.isClaimInside(parent));
        assertFalse(parent.isClaimInside(claimDataDTO));
    }

    @Test
    void testClaimOverlapAnother() {
        final ClaimDataDTO claimDataDTO = new ClaimDataDTO();
        final ClaimDataDTO parent = new ClaimDataDTO();

        claimDataDTO.setLesserBoundaryCornerX(10.0);
        claimDataDTO.setLesserBoundaryCornerZ(10.0);
        claimDataDTO.setGreaterBoundaryCornerX(20.0);
        claimDataDTO.setGreaterBoundaryCornerZ(20.0);

        parent.setLesserBoundaryCornerX(15.0);
        parent.setLesserBoundaryCornerZ(15.0);
        parent.setGreaterBoundaryCornerX(25.0);
        parent.setGreaterBoundaryCornerZ(25.0);

        assertTrue(claimDataDTO.isClaimOverlap(parent));
        assertTrue(parent.isClaimOverlap(claimDataDTO));
    }

}
