package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.*;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.enums.PageType;
import com.example.kinocms_user.repository.ShareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.kinocms_user.util.TestDataUtil.getElements;
import static com.example.kinocms_user.util.TestDataUtil.loadMarks;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class ShareServiceImpTest {

    @Mock
    private ShareRepository shareRepository;
    @InjectMocks
    private ShareServiceImp shareService;
    private List<Share> expectedShares;

    @BeforeEach
    void setUp() {
        expectedShares = loadShares();
    }

    @Test
    void getAll() {
        when(shareRepository.findAll()).thenReturn(expectedShares);
        List<Share> shares = shareService.getAll();
        assertNotNull(shares, "Collection with shares should be not null");
        assertEquals(expectedShares.size(), shares.size(), "Sizes list of shares should be match");
    }

    @Test
    void getById() {
        Long expectedId = 1L;
        Share expectedShare = loadShares().stream()
                .filter(s -> s.getId().equals(expectedId))
                .findFirst()
                .orElse(null);
        when(shareRepository.findById(expectedId)).thenReturn(Optional.ofNullable(expectedShare));
        Optional<Share> shareById = shareService.getById(expectedId);
        assertTrue(shareById.isPresent(), "Share should be present");
        assertEquals(expectedId, shareById.get().getId(), "Id should match");
    }
    @Test
    void getByStatus() {
        boolean expectedStatus = true;
        when(shareRepository.getAllByStatus(expectedStatus)).thenReturn(loadShares());
        List<Share> shares = shareService.getAllByStatus(expectedStatus);
        assertNotNull(shares, "Collection shares should not null");
        assertEquals(loadShares().size(), shares.size(), "Size between collections should match");
        verify(shareRepository,times(1)).getAllByStatus(expectedStatus);
    }
    private List<Share> loadShares() {
        List<Share> shares = new ArrayList<>();
        Share share1 = new Share(1L, "image1.jpg", "banner1.jpg", LocalDate.of(2022, 5, 1), true, "url1",
                List.of(new PageTranslation(1L, LanguageCode.Ukr, PageType.share, "Нічна п'ятниця", "бла бла", null)),
                List.of(new CeoBlock(1L, LanguageCode.Ukr, PageType.share, "title1", "keywords1", "description1")),
                getElements(loadMarks()));
        Share share2 = new Share(2L, "image2.jpg", "banner2.jpg", LocalDate.of(2022, 6, 1), false, "url2",
                List.of(new PageTranslation(2L, LanguageCode.Ukr, PageType.share, "Дика неділя", "бла2 бла2", null)),
                List.of(new CeoBlock(2L, LanguageCode.Ukr, PageType.share, "title2", "keywords2", "description2")),
                getElements(loadMarks()));
        shares.add(share1);
        shares.add(share2);
        return shares;
    }

}